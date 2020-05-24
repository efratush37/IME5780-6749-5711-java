package unittests.geometries;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

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

    /***
     * test method for the find intersection function between a ray and the sphere
     */
    @Test
    public void findIntsersections() {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        // p1 and p2 are the Intsersections points
        //refactoring to a list of geo points
        GeoPoint geoPoint = new GeoPoint(sphere,new Point3D(0.0651530771650466, 0.355051025721682, 0));
        GeoPoint geoPoint2 = new GeoPoint(sphere,  new Point3D(1.53484692283495, 0.844948974278318, 0));
        List<GeoPoint> result = sphere.findIntsersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));

        assertEquals("Wrong number of points", 2, result.size());
        // check for the order
        if (result.get(0).getPoint().getC1().get() > result.get(1).getPoint().getC1().get())
            result = List.of(result.get(1), result.get(0));
        // check that we find the right points
        assertEquals("Ray crosses sphere", List.of(geoPoint, geoPoint2), result);



        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntsersections(new Ray(new Point3D(1, 0.5, 0), new Vector(0,1,0)));
        assertEquals("Wrong number of points", 1, result.size());
        geoPoint = new GeoPoint(sphere, new Point3D(1, 1,0));
        assertEquals("Ray crosses sphere", List.of(geoPoint), result);


        // TC04: Ray starts after the sphere (0 points)
        result= sphere.findIntsersections(new Ray(new Point3D(2,2,0),new Vector(1,0,0)));
        assertNull("Wrong number of points", result);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(new Ray(new Point3D(2,0,0), new Vector(-1,0,1)));
        assertEquals("Wrong number of points", 1, result.size());
        geoPoint = new GeoPoint(sphere, new Point3D(1,0,1));
        assertEquals("Ray crosses sphere", List.of(geoPoint), result);


        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntsersections(new Ray(new Point3D(2,0,0), new Vector(1,0,0)));
        assertNull("Wrong number of points", result);


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntsersections(new Ray(new Point3D(-1,0,0), new Vector(1,0,0)));
        assertEquals("Wrong number of points", 2, result.size());
        geoPoint = new GeoPoint(sphere, new Point3D(0,0,0));
        geoPoint2 =new GeoPoint(sphere, new Point3D(2,0,0));

        if (result.get(0).getPoint().getC1().get() > result.get(1).getPoint().getC1().get())
            result = List.of(result.get(1), result.get(0));

        assertEquals("Ray crosses sphere", List.of(geoPoint, geoPoint2), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntsersections(new Ray(new Point3D(2,0,0), new Vector(-1,0,0)));
        assertEquals("Wrong number of points", 1, result.size());
        geoPoint = new GeoPoint(sphere, new Point3D(0,0,0));
        assertEquals("through the center - Ray starts at sphere and goes inside", List.of(geoPoint), result);

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntsersections(new Ray(new Point3D(1.5,0,0), new Vector(-1,0,0)));
        assertEquals("Wrong number of points", 1, result.size());
        geoPoint= new GeoPoint(sphere, new Point3D(0,0,0));
        assertEquals("through the center - Ray starts inside", List.of(geoPoint), result);

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntsersections(new Ray(new Point3D(1,0,0), new Vector(-1,0,0)));
        assertEquals("through the center - Wrong number of points", 1, result.size());
        geoPoint = new GeoPoint(sphere, new Point3D(0,0,0));
        assertEquals("through the center - Ray starts at the center", List.of(geoPoint), result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntsersections(new Ray(new Point3D(2,0,0), new Vector(1,0,0)));
        assertNull("through the center - Ray starts at sphere and goes outside", result);

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntsersections(new Ray(new Point3D(3,0,0), new Vector(1,0,0)));
        assertNull("through the center - Ray starts after sphere", result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point

        result = sphere.findIntsersections(new Ray(new Point3D(0,1,0), new Vector(1,0,0)));
        assertNull("Tangent - Ray starts before the tangent point", result);

        // TC20: Ray starts at the tangent point
        result = sphere.findIntsersections(new Ray(new Point3D(1,1,0), new Vector(1,0,0)));
        assertNull("Tangent - Ray starts at the tangent point", result);

        // TC21: Ray starts after the tangent point
        result = sphere.findIntsersections(new Ray(new Point3D(2,1,0), new Vector(1,0,0)));
        assertNull("Tangent - Ray starts after the tangent point", result);

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntsersections(new Ray(new Point3D(1,2,0), new Vector(1,0,0)));
        assertNull("Special cases - ray is orthogonal to ray start", result);
    }
}