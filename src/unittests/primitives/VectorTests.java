package unittests.primitives;

import org.junit.Test;
import primitives.Vector;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class VectorTests {

    @Test
    public void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals("the subtract of the vectors is:", new Vector(3, 6, 9), v1.subtract(v2));
    }

    @Test
    public void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals("the add of the vectors is:", new Vector(-1, -2, -3), v1.add(v2));
    }

    @Test
    public void scale() {
        Vector v1 = new Vector(1, 2, 3);
        double x = 5;
        assertEquals("the scale is:", new Vector(5, 10, 15), v1.scale(x));
    }

    @Test
    public void lengthSquared() {
        Vector v1 = new Vector(6, 8, 0);
        assertEquals("The length squared of the vector", v1.lengthSquared(), 100.0, 0);
    }

    @Test
    public void length() {
        Vector v1 = new Vector(6, 8, 0);
        assertEquals("The length of the vector", v1.length(), 10.0, 0);
    }

    @Test
    public void dotProduct() {
        Vector v1 = new Vector(6, 8, 0);
        Vector v2 = new Vector(-2, -4, -6);
        double x = 6 * (-2) + 8 * (-4);
        assertEquals("the dot product is:", x, v1.dotProduct(v2), 0);
    }

    @Test
    public void crossProduct() {
        Vector v1 = new Vector(6, 8, 0);
        Vector v2 = new Vector(2, 4, 6);
        assertEquals("the cross product is:", new Vector(48, -36, 8), v1.crossProduct(v2));
    }

    @Test
    public void normalize() {
        Vector v1 = new Vector(6, 8, 1);
        double length = Math.sqrt(101);
        assertEquals("the normal vector is:", new Vector(6 / length, 8 / length, 1 / length), v1.normalize());
    }

    @Test
    public void normalized() {
        Vector v1 = new Vector(6, 8, 1);
        double length = Math.sqrt(101);
        assertEquals("the normalized vector is:", new Vector(6 / length, 8 / length, 1 / length), v1.normalize());
    }


}