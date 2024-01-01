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
public class SplitPolygonProcess extends StaticMethodsProcessFactory<SplitPolygonProcess> {

    public SplitPolygonProcess() {
        super(Text.text("gs"), "gs", SplitPolygonProcess.class);
    }

    @DescribeProcess(
            title = "splitpolygon",
            description = "Splits a Polygon by a LineString. Developer :- robertohuru@gmail.com")
    @DescribeResult(name = "result",
            description = "The collection of result polygons")
    public static Geometry splitpolygon( @DescribeParameter(name = "polygon", description = "The polygon to be split") SimpleFeatureCollection poly,
            @DescribeParameter(name = "line", description = "The line to split by") SimpleFeatureCollection line) {
        return VectorUtils.splitPolygon(poly, line);
    }
}
