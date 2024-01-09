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
            title = "cordinatetransform",
            description = "Transform the coordinate of a geometry. Developer robertohuru@gmail.com"
    )
public class CordinateTransform implements GeoServerProcess {
    
    /**
     *
     * @param poly
     * @param srid
     * @return
     * @throws Exception
     */
    @DescribeResult(name = "result",
            description = "Transformed Coordinates")
    public static Geometry execute(@DescribeParameter(name = "Geometry", description = "Geometry to be transformed") SimpleFeatureCollection poly,
            @DescribeParameter(name = "SRID", description = "Target SRID") int srid)throws Exception {
        return VectorUtils.transformCoords(poly, srid);
    }
}
