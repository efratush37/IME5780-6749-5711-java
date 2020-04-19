package unittests.geometries;

import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for Geometries.Tube class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class TubeTests {

    /**
     * Test method for {@link Tube#getNormal(Point3D)} ()} (Geometries.Tube)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v=new Vector(4, 4, 4);
        Point3D p=  new Point3D(1, 1, 1);
        Ray r = new Ray(v,p);
        Point3D p1 = new Point3D(2, 3, 4);
        Vector V = new Vector(-95, -94, -93).normalize();
        Tube t = new Tube(2, r);
        assertEquals("the normal of the tube is:",new Vector(V), t.getNormal(p1));
    }
}