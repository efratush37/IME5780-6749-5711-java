package unittests.geometries;

import geometries.Sphere;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for Geometries.Sphere class
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class SphereTests {

    /**
     * Test method for {@link Sphere#getNormal(Point3D)} ()} (Geometries.Sphere)}.
     */
    @Test
    public void getNormal() {
        //   // ============ Equivalence Partitions Tests ==============
        //   // TC01: There is a simple single test here
        //   Sphere s = new Sphere(Point3D.ZERO, 2);
        //   Point3D p=new Point3D(2,0,0);
        //   assertEquals("the normal of sphere is:",4, s.getNormal(p));

        Sphere s1 = new Sphere(4, new Point3D(0, 0, 0));
        Sphere s2 = new Sphere(1, new Point3D(1, 1, 1));

        assertTrue(s1.getNormal(new Point3D(0, 0, 4)).equals(new Vector(new Point3D(0, 0, 1))));
        assertTrue(s1.getNormal(new Point3D(0, 0, -4)).equals(new Vector(new Point3D(0, 0, -1))));

        assertTrue(s1.getNormal(new Point3D(0, 4, 0)).equals(new Vector(new Point3D(0, 1, 0))));
        assertTrue(s1.getNormal(new Point3D(0, -4, 0)).equals(new Vector(new Point3D(0, -1, 0))));

        assertTrue(s1.getNormal(new Point3D(4, 0, 0)).equals(new Vector(new Point3D(1, 0, 0))));
        assertTrue(s1.getNormal(new Point3D(-4, 0, 0)).equals(new Vector(new Point3D(-1, 0, 0))));

        assertTrue(s2.getNormal(new Point3D(1, 1, 0)).equals(new Vector(new Point3D(0, 0, -1))));

        assertTrue(s2.getNormal(new Point3D(0, 1, 1)).equals(new Vector(new Point3D(-1, 0, 0))));

        assertTrue(s2.getNormal(new Point3D(1, 0, 1)).equals(new Vector(new Point3D(0, -1, 0))));
    }
}