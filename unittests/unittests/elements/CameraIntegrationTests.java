package unittests.elements;

import elements.Camera;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
/**
 * test class for the intergration between the camera and the find intersection method
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class CameraIntegrationTests {
    // **** Group: Construct Ray Throw Pixel With Sphere
    @Test
    public void constructRayThroughPixelSphere1() {
        Sphere s = new Sphere(1d, new Point3D(0, 0, 3));
        Camera c = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, s);
        assertEquals("Wrong number of points", 2, count);
    }

    @Test
    public void constructRayThroughPixelSphere2() {
        Sphere s = new Sphere(2.5d, new Point3D(0, 0, 2.5));
        Camera c = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, s);
        assertEquals("Wrong number of points", 18, count);
    }

    @Test
    public void constructRayThroughPixelSphere3() {
        Sphere s = new Sphere(2d, new Point3D(0, 0, 2));
        Camera c = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, s);
        assertEquals("Wrong number of points", 10, count);
    }

    @Test
    public void constructRayThroughPixelSphere4() {
        Sphere s = new Sphere(4d, new Point3D(0, 0, 3));
        Camera c = new Camera(new Point3D(-2, 0, 2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, s);
        assertEquals("Wrong number of points", 9, count);
    }

    @Test
    public void constructRayThroughPixelSphere5() {
        Sphere s = new Sphere(0.5d, new Point3D(0, 0, -1));
        Camera c = new Camera(new Point3D(0, 0, 2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, s);
        assertEquals("Wrong number of points", 0, count);
    }

    // **** Group: Construct Ray Throw Pixel With Plane
    @Test
    public void constructRayThroughPixelPlane1() {
        Plane p = new Plane(new Point3D(0, 0, 0), new Point3D(0, 1, 0), new Point3D(1, 0, 0));
        Camera c = new Camera(new Point3D(0, 0, -2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, p);
        assertEquals("Wrong number of points", 9, count);
    }

    @Test
    public void constructRayThroughPixelPlane2() {
        Plane p = new Plane(new Point3D(-8, 2, 0), new Point3D(1, 0, 1), new Point3D(1, 2, 0));
        Camera c = new Camera(new Point3D(0, 0, -2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, p);
        assertEquals("Wrong number of points", 9, count);
    }

    @Test
    public void constructRayThroughPixelPlane3() {
        Plane p = new Plane(new Point3D(1, 0, 0), new Point3D(0, 0, 1), new Point3D(0, 1, 0));
        Camera c = new Camera(new Point3D(0, 0, -2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, p);
        assertEquals("Wrong number of points", 6, count);
    }

    // **** Group: Construct Ray Throw Pixel With Triangle
    @Test
    public void constructRayThroughPixelTriangle1() {
        Triangle t = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        Camera c = new Camera(new Point3D(0, 0, -2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, t);
        assertEquals("Wrong number of points", 1, count);
    }

    @Test
    public void constructRayThroughPixelTriangle2() {
        Triangle t = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        Camera c = new Camera(new Point3D(0, 0, -2), new Vector(0, 0, 1), new Vector(0, 1, 0));
        int count = 0;
        count = helpFunction(c, t);
        assertEquals("Wrong number of points", 2, count);
    }

    /**
     * this function created for our conviniency and for preserving the DRY  demand
     *
     * @param c= the camera
     * @param o= an object who can be sphere, triangle or plane
     * @return the amount of the intersections with the geometry
     * (refactoring- now we use list of geo points to represent the intersections points)
     */
    public int helpFunction(Camera c, Object o) {
        Ray r;
        List<GeoPoint> list;
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                r = c.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                if (o instanceof Sphere) {
                    Sphere g = (Sphere) o;
                    list = g.findIntsersections(r);
                    if (list != null) {
                        counter = counter + list.size();
                    }
                }
                if (o instanceof Plane) {
                    Plane g = (Plane) o;
                    list = g.findIntsersections(r);
                    if (list != null) {
                        counter = counter + list.size();
                    }
                }
                if (o instanceof Triangle) {
                    Triangle g = (Triangle) o;
                    list = g.findIntsersections(r);
                    if (list != null) {
                        counter = counter + list.size();
                    }
                }

            }
        }
        return counter;
    }


}