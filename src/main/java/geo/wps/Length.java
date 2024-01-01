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

public class Length extends StaticMethodsProcessFactory<Length> {
    public Length() {
        super(Text.text("gs"),  "gs", Length.class);
    }

    @DescribeProcess(
            title = "length",
            description = "Get the total length of all line segments in a feature collection. Measurement is given in the source units. Developer robertohuru@gmail.com"
    )
    @DescribeResult(name = "result",
            description = "Total length of linestring")
    public static double length(@DescribeParameter(name = "feature", description = "Line feature") SimpleFeatureCollection linestring) {
        return VectorUtils.length(linestring);
    }
}