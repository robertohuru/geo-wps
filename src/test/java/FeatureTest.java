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
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CRSAuthorityFactory;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.referencing.CRS;

public class FeatureTest {

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testLength() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry geom = wktReader.read("LINESTRING(0 0, 0 1,0 2)");
        assertEquals(2, Length.execute(VectorUtils.geometry2SimpleFeature(geom)), 0.1);
    }

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testArea() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry  geom = wktReader.read("POLYGON((0 0, 0 2, 2 2, 2 0,0 0))");
        assertEquals(4, GetAreaProcess.execute(VectorUtils.geometry2SimpleFeature(geom)), 0.1);
    }

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    public void testTransform() throws Exception {
        WKTReader wktReader = new WKTReader(new GeometryFactory());
        Geometry geom = wktReader.read("POLYGON((0 0, 0 2, 2 2, 2 0,0 0))");
        try {
            CRSAuthorityFactory factory = CRS.getAuthorityFactory(true);
            CoordinateReferenceSystem sourceCRS = factory.createCoordinateReferenceSystem("EPSG:3857");
            sourceCRS.getCoordinateSystem();
        } catch (FactoryException ex) {
            Logger.getLogger(VectorUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        assertEquals(4, GetAreaProcess.execute(VectorUtils.geometry2SimpleFeature(geom)), 0.1);
    }
}
