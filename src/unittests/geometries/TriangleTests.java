package unittests.geometries;

import geometries.Triangle;
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
public class TriangleTests {

    /**
     * Test method for {@link Triangle#getNormal(Point3D)} ()} (Geometries.Triangle)}.
     */
    @Test
    public void getNormal() {
        //    // ============ Equivalence Partitions Tests ==============
        //    // TC01: There is a simple single test here
        //    Triangle t = new Triangle(new Point3D(1,0,0), new Point3D(0, 1, 0), new Point3D(0, 0, 1));
        //    assertEquals("the normal of the triangle is:", (new Vector(-1,-1,-1)).normalize(), t.getNormal(new Point3D(1,0,0)));

        Triangle triangle = new Triangle(new Point3D(-1, 0, 0), new Point3D(1, 0, 0), new Point3D(0, 0, 1));
        Vector vector = new Vector(0, -1, 0).normalize();

        assertEquals(vector, triangle.getNormal(new Point3D(-1, 0, 0)));

        assertEquals(vector, triangle.getNormal(new Point3D(0, 0, 0)));

        assertEquals(vector, triangle.getNormal(new Point3D(0.5, 0, 0)));
    }
}