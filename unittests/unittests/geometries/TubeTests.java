package unittests.geometries;

import geometries.Intersectable.GeoPoint;
import geometries.Tube;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
        Ray r1 = new Ray(Point3D.ZERO, new Vector(0,1,0));
        Tube t1 = new Tube(1, r1);
        Vector v1 = new Vector(1,2,1);
        Ray r2 = new Ray(new Point3D(1,2,3), new Vector(1,0,2));
        Tube t2 = new Tube(2, r2);

        assertEquals(v1.subtract(new Vector(0,2,0)).scale(1d/Math.sqrt(2)), t1.getNormal(v1.getHead()));

        assertEquals(new Vector(-4, 0, 2).scale(1d/Math.sqrt(20)), t2.getNormal(new Point3D(0,2,11)));
    }

    @Test
    public void findIntsersections() {
        Ray ray = new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0));
        Point3D p1 = new Point3D(0, 2, 0);
        Point3D p2 = new Point3D(2, 2, 0);
        Tube tube = new Tube(1d, ray);

        // TC01: Ray's line is outside the tube (0 points)
        List<GeoPoint> result = tube.findIntsersections(new Ray(new Point3D(0, 4, 3),
                new Vector(1, 0, 0)));

        assertEquals("Ray's line out of tube", null, result);

        // TC02: Ray starts before and crosses the tube (2 points)
        result = tube.findIntsersections(new Ray(new Point3D(-1, 2, 0),
                new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", 2, result.size());

        if (result.get(0).point.getC1().get() > result.get(1).point.getC1().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses tube", List.of(p1, p2), result);

        // TC03: Ray starts inside the tube (1 point)
        result = tube.findIntsersections(new Ray(new Point3D(0.5, 2, 0),
                new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray's crosses the tube", List.of(p2), result);

        // TC04: Ray starts after the tube (0 points)
        assertEquals("Ray's start point out of tube", null,
                tube.findIntsersections(new Ray(new Point3D(3, 2, 0),
                        new Vector(1, 0, 0))));

        // =============== Boundary Values Tests ==================

        //TC11: Ray starts at tube and go inside (1 point)
        result = tube.findIntsersections(new Ray(p1, new Vector(1, 0, 0)));

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray's crosses the tube", List.of(p2), result);

        // TC12: Ray starts at tube and goes outside (0 points)
        assertEquals("Ray's start point the tube and go outside", null,
                tube.findIntsersections(new Ray(p2, new Vector(1, 0, 0))));
    }
}