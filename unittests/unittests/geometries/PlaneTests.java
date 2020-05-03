package unittests.geometries;

import geometries.Plane;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for Geometries.Plane class
 *
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

        assertEquals("the normal of the plane is:", v1, v3);
    }


    @Test
    public void findIntsersections() {
        Plane plane = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 1));
        //Vector n = plane.getNormal();
        //System.out.println(n);
        Point3D point;


        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane
        List<Point3D> result = plane.findIntsersections(new Ray(new Point3D(0, -2, 0), new Vector(1, 1, 1)));
        assertEquals("Wrong number of points", 1, result.size());
        point = new Point3D(2, 0, 2);
        assertEquals("Ray does not intersect the plane", List.of(point), result);

        // TC02: Ray does not intersect the plane
        result = plane.findIntsersections(new Ray(new Point3D(0, -2, 0), new Vector(-1, 0, 0)));
        assertNull("Ray intersects the plane", result);


        // =============== Boundary Values Tests ==================
        // **** Group: Ray is parallel to the plane
        // TC11: the ray included in the plane
        Ray r1 = new Ray(new Point3D(-2, 0, 0), new Vector(2, 0, 2));
        assertNull("the ray doesn't included in the plane", plane.findIntsersections(r1));

        // TC12: the ray does not included in the plane
        Ray r2 = new Ray(new Point3D(0, 1, 0), new Vector(2, 1, 2));
        assertNull("the ray included in the plane", plane.findIntsersections(r2));

        // **** Group: Ray is orthogonal to the plane
        Vector v = new Vector(2, 0, 2);

        // TC21: p0 before the plane
        Ray r21 = new Ray(new Point3D(0, 1, 0), new Vector(0, -2, 0));
        assertEquals("the ray doesn't orthogonal to the plane", 0, v.dotProduct(r21.getDir().normalize()), 0);
        assertEquals("the ray doesn't orthogonal to the plane", 1, (plane.findIntsersections(r21)).size());
        List<Point3D> result22 = plane.findIntsersections(r21);
        Point3D point21 = new Point3D(0, 0, 0);
        assertEquals("Orthogonal-before", List.of(point21), result22);

        // TC22: p0 in the plane
        Ray r22 = new Ray(new Point3D(0, 0, 0), new Vector(0, -2, 0));
        assertEquals("the ray doesn't orthogonal to the plane", 0, v.dotProduct(r22.getDir().normalize()), 0);
        assertNull("Orthogonal-in", plane.findIntsersections(r22));

        // TC23: p0 after the plane
        Ray r23 = new Ray(new Point3D(0, -1, 0), new Vector(0, -2, 0));
        assertEquals("the ray doesn't orthogonal to the plane", 0, v.dotProduct(r23.getDir().normalize()), 0);
        assertNull("Orthogonal-after", plane.findIntsersections(r23));

        // **** Group: Ray is neither orthogonal nor parallel to the plane

        // TC31: Ray begins at the plane
        Ray r31 = new Ray(new Point3D(0, 0, 0), new Vector(4, -2, 3));
        assertNull("in, and not parallel or orthogonal, start on p0", plane.findIntsersections(r31));

        // TC32: Ray begins in the same point which appears as reference point in the plane
        Ray r32 = new Ray(new Point3D(1, 0, 0), new Vector(4, -2, 3));
        assertNull("in,  and not parallel or orthogonal, start on reference point", plane.findIntsersections(r32));


    }
}