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
import org.locationtech.jts.geom.Geometry;

/**
 *
 * @author OhuruRO
 */
@DescribeProcess(
            title = "splitpolygon",
            description = "Splits a Polygon by a LineString. Developer :- robertohuru@gmail.com")
public class SplitPolygonProcess implements GeoServerProcess {

    /**
     *
     * @param poly
     * @param line
     * @return
     */
    @DescribeResult(name = "result",
            description = "The collection of result polygons")
    public static Geometry execute( @DescribeParameter(name = "polygon", description = "The polygon to be split") SimpleFeatureCollection poly,
            @DescribeParameter(name = "line", description = "The line to split by") SimpleFeatureCollection line) {
        return VectorUtils.splitPolygon(poly, line);
    }
}
