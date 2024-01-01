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

/**
 *
 * @author OhuruRO
 */
public class GetAreaProcess extends StaticMethodsProcessFactory<GetAreaProcess> {
    public GetAreaProcess() {
        super(Text.text("gs"),  "gs", GetAreaProcess.class);
    }

    @DescribeProcess(
            title = "area",
        description = "Get Area of a Polygon. Developer:- robertohuru@gmail.com")
    @DescribeResult(name = "result",
            description = "The area of the polygon")
    public static double area(@DescribeParameter(name = "Geometry", description = "Geometry which to determine its area") SimpleFeatureCollection poly) {
        return VectorUtils.getArea(poly);
    }
}
