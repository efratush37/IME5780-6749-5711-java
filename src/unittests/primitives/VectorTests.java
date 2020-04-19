package unittests.primitives;

import org.junit.Test;
import primitives.Vector;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Rivka Zizovi 207265711 & Efrat Ankonina 322796749
 */
public class VectorTests {
    /**
     * Test method for {@link primitives.Vector#subtract(Vector)} (primitives.Vector)}.
     */
    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals("the subtract of the vectors is:", new Vector(3, 6, 9), v1.subtract(v2));
        // =============== Boundary Values Tests ==================
        try {
            Vector v3=new Vector(1,1,1);
            Vector v4=new Vector(1,1,1);
            v3.subtract(v4);
            fail("Vector (0,0,0) should'nt be valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
    }

    /**
     * Test method for {@link primitives.Vector#add(Vector)} (primitives.Vector)}.
     */
    @Test
    public void add() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals("the add of the vectors is:", new Vector(-1, -2, -3), v1.add(v2));
        // =============== Boundary Values Tests ==================
        try {
            Vector v3=new Vector(-1,-1,-1);
            Vector v4=new Vector(1,1,1);
            v3.add(v4);
            fail("Vector (0,0,0) shouldnt be valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null);
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void scale() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(1, 2, 3);
        double x = 5;
        assertEquals("the scale is:", new Vector(5, 10, 15), v1.scale(x));
        // =============== Boundary Values Tests ==================
        try {

            v1.scale(0);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null); }

    }

    /**
     * Test method for {@link Vector#lengthSquared()} (primitives.Vector)}.
     */
    @Test
    public void lengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(6, 8, 0);
        assertEquals("The length squared of the vector", v1.lengthSquared(), 100.0, 0);
    }

    /**
     * Test method for {@link Vector#length()} (primitives.Vector)}.
     */
    @Test
    public void length() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(6, 8, 0);
        assertEquals("The length of the vector", v1.length(), 10.0, 0);
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void dotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(6, 8, 0);
        Vector v2 = new Vector(-2, -4, -6);
        double x = 6 * (-2) + 8 * (-4);
        assertEquals("the dot product is:", x, v1.dotProduct(v2), 0);
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands

        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }

    /**
     * Test method for {@link Vector#normalize()} (primitives.Vector)}.
     */
    @Test
    public void normalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(6, 8, 1);
        double length = Math.sqrt(101);
        assertEquals("the normal vector is:", new Vector(6 / length, 8 / length, 1 / length), v1.normalize());
    }

    /**
     * Test method for {@link Vector#normalized()} (primitives.Vector)}.
     */
    @Test
    public void normalized() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Vector v1 = new Vector(6, 8, 1);
        double length = Math.sqrt(101);
        assertEquals("the normalized vector is:", new Vector(6 / length, 8 / length, 1 / length), v1.normalize());
    }
}