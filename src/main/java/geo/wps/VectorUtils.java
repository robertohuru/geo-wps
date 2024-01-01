/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geo.wps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.LineStringExtracter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.operation.polygonize.Polygonizer;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.cs.CoordinateSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author OhuruRO
 */
public class VectorUtils {

    public static Geometry polygonize(Geometry geometry) {
        List lines = LineStringExtracter.getLines(geometry);
        Polygonizer polygonizer = new Polygonizer();
        polygonizer.add(lines);
        Collection polys = polygonizer.getPolygons();
        Polygon[] polyArr = GeometryFactory.toPolygonArray(polys);
        return geometry.getFactory().createGeometryCollection(polyArr);
    }

    public static double length(SimpleFeatureCollection linefeature) {
        Geometry line = simpleFeature2Geom(linefeature).union();
        return line.getLength();
    }

    public static Geometry transformCoords(SimpleFeatureCollection feature, int srid) throws Exception {
        Geometry sourceGeometry = simpleFeature2Geom(feature).union();
        CoordinateReferenceSystem sourceCRS;
        CoordinateReferenceSystem targetCRS;
        targetCRS = CRS.decode("EPSG:" + srid);
        if (sourceGeometry.getSRID() == 0) {
            sourceGeometry.setSRID(4326);
            sourceCRS = CRS.decode("EPSG:4326");
        } else {
            sourceCRS = CRS.decode("EPSG:" + sourceGeometry.getSRID());
        }
        MathTransform transform = CRS.findMathTransform(sourceCRS, targetCRS);
        return JTS.transform(sourceGeometry, transform);
    }

    public static Geometry simpleFeature2Geom(SimpleFeatureCollection geom) {
        SimpleFeatureIterator iterator = geom.features();
        List geoms = new ArrayList();
        int srid = 4326;
        while (iterator.hasNext()) {
            SimpleFeature feature = iterator.next();
            try {
                Geometry geometry = (Geometry) feature.getDefaultGeometry();
                srid = geometry.getSRID();
                if (!geometry.isValid()) {
                    continue;
                }
                geoms.add(geometry);
            } catch (Exception e) {
                Logger.getLogger(VectorUtils.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        PrecisionModel pm = new PrecisionModel(1);
        GeometryFactory g = new GeometryFactory(pm, srid);
        Geometry poly = g.createGeometryCollection(GeometryFactory.toGeometryArray(geoms));
        return poly;
    }

    public static double getArea(SimpleFeatureCollection geom) {
        return simpleFeature2Geom(geom).getArea();
    }

    public static Geometry splitPolygon(SimpleFeatureCollection polyFeature, SimpleFeatureCollection lineFeature) {
        Geometry poly = simpleFeature2Geom(polyFeature).union();
        Geometry line = simpleFeature2Geom(lineFeature).union();
        Geometry nodedLinework = poly.getBoundary().union(line);
        Geometry polys = polygonize(nodedLinework);
        // Only keep polygons which are inside the input
        List output = new ArrayList();
        for (int i = 0; i < polys.getNumGeometries(); i++) {
            Polygon candpoly = (Polygon) polys.getGeometryN(i);
            if (poly.contains(candpoly.getInteriorPoint())) {
                output.add(candpoly);
            }
        }
        return poly.getFactory().createGeometryCollection(GeometryFactory.toGeometryArray(output));
    }

    public static SimpleFeatureCollection geometry2SimpleFeature(Geometry geom) {
        SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
        tb.setName("featureType");
        tb.add("geometry", Geometry.class);
        tb.add("integer", Integer.class);

        SimpleFeatureBuilder b = new SimpleFeatureBuilder(tb.buildFeatureType());
        DefaultFeatureCollection features = new DefaultFeatureCollection(null, b.getFeatureType());
        
        b.add(geom);
        b.add(1);
        features.add(b.buildFeature(0 + ""));
        return features;
    }
}
