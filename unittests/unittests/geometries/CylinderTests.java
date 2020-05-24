package unittests.geometries;

import geometries.Cylinder;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 * Unit tests for Geometries.Cylinder class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class CylinderTests {

    /**
     * Test method for {@link Cylinder#getNormal(Point3D)} ()} (Geometries.Cylinder)}.
     */
    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Ray ray = new Ray(new Point3D(1, 2, 3), new Vector(1, 0, 2));
        Cylinder cylinder = new Cylinder(2, ray, 4);

        assertEquals(new Vector(-4, 0, 2).scale(1d / Math.sqrt(20)), cylinder.getNormal(new Point3D(0, 2, 11)));

        assertEquals(ray.getDir(), cylinder.getNormal(ray.get_p0().add(new Vector(-2, 0, 1).normalized().scale(1.5d))));
    }

    /**
     * test method for the find intersection function between a ray and a geometry
     */
    @Test
    public void findIntsersections() {

    }
}