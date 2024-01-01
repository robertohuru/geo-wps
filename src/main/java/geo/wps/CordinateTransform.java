/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geo.wps;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;
import org.geotools.process.factory.StaticMethodsProcessFactory;
import org.geotools.text.Text;
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author OhuruRO
 */
public class CordinateTransform extends StaticMethodsProcessFactory<CordinateTransform> {
    public CordinateTransform() {
        super(Text.text("gs"),  "gs", CordinateTransform.class);
    }

    @DescribeProcess(
            title = "cordinatetransform",
            description = "Transform the coordinate of a geometry. Developer robertohuru@gmail.com"
    )
    @DescribeResult(name = "result",
            description = "Transformed Coordinates")
    public static Geometry cordinatetransform(@DescribeParameter(name = "Geometry", description = "Geometry to be transformed") SimpleFeatureCollection poly,
            @DescribeParameter(name = "SRID", description = "Target SRID") int srid)throws Exception {
        return VectorUtils.transformCoords(poly, srid);
    }
}
