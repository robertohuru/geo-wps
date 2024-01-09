/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geo.wps;

import org.geoserver.wps.gs.GeoServerProcess;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;

/**
 *
 * @author OhuruRO
 */
@DescribeProcess(
            title = "area",
        description = "Get Area of a Polygon. Developer:- robertohuru@gmail.com")
public class GetAreaProcess implements GeoServerProcess {
    
    @DescribeResult(name = "result",
            description = "The area of the polygon")
    public static double execute(@DescribeParameter(name = "Geometry", description = "Geometry which to determine its area") SimpleFeatureCollection poly) {
        return VectorUtils.getArea(poly);
    }
}
