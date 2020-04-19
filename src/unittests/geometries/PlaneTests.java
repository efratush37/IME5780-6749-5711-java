package unittests.geometries;

import geometries.Plane;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 * Unit tests for Geometries.Plane class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)} ()} (Geometries.Plane)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Plane pl1 = new Plane(new Point3D(0, 1, 0), new Point3D(1, 0, 0), new Point3D(0, 0, 1));
        Vector v1 = pl1.getNormal();

        Plane pl2 = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
        Vector v3 = pl2.getNormal();

        assertEquals("the normal of the plane is:",v1, v3);
    }
}