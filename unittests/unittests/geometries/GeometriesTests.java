package unittests.geometries;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GeometriesTests {

    @Test
    public void findIntsersections() {
        // ============ Equivalence Partitions Tests ==============
        Geometries geometries = new Geometries();
        List<GeoPoint> result;

        // TC11: An empty collection of geometries
        result = geometries.findIntsersections(new Ray(new Point3D(6, 6, 6), new Vector(1, 0, 0)));

        assertNull("Wrong number of points", result);

        geometries.add(
                new Sphere(1d, new Point3D(1, 0, 0)),
                new Plane(new Point3D(0, 0, 2), new Point3D(2, 1, 2), new Point3D(2, 2, 2)),
                new Plane(new Point3D(0, 0, -2), new Point3D(2, 1, -2), new Point3D(2, 2, -2)));

        // =============== Boundary Values Tests ==================
        // **** Group: do no intersects the geometries
        // TC01: several geometries have been intersectable
        result = geometries.findIntsersections(new Ray(new Point3D(0, 6, 4), new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 2, result.size());

        // TC12: non of the geometries are intersectable
        result = geometries.findIntsersections(new Ray(new Point3D(6, 6, 6), new Vector(1, 0, 0)));
        assertNull("Wrong number of points", result);


        // **** Group: intersects the geometries
        // TC21: only one geometry have been intersectable
        result = geometries.findIntsersections(new Ray(new Point3D(0, 6, 1), new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 1, result.size());


        // TC22: all the geometries have been intersectable
        result = geometries.findIntsersections(new Ray(new Point3D(0.5, 0.5, 4), new Vector(0, 0, -1)));
        assertEquals("Wrong number of points", 4, result.size());

    }
}