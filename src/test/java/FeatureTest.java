/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author OhuruRO
 */
import geo.wps.GetAreaProcess;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;

import geo.wps.Length;
import geo.wps.VectorUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class FeatureTest {

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testLength() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry geom = wktReader.read("LINESTRING(0 0, 0 1,0 2)");
        assertEquals(2, Length.length(VectorUtils.geometry2SimpleFeature(geom)), 0.1);
    }

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testArea() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry geom = wktReader.read("POLYGON((0 0, 0 2, 2 2, 2 0,0 0))");
        assertEquals(4, GetAreaProcess.area(VectorUtils.geometry2SimpleFeature(geom)), 0.1);
    }

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testTransform() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry geom = wktReader.read("POLYGON((0 0, 0 2, 2 2, 2 0,0 0))");
        CoordinateReferenceSystem crs = null;
        try {
            crs = CRS.decode("EPSG:3857");
        } catch (FactoryException ex) {
            Logger.getLogger(VectorUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        SimpleFeatureCollection col = VectorUtils.geometry2SimpleFeature(geom);
        
        assertEquals(4, GetAreaProcess.area(col), 0.1);
    }
}
