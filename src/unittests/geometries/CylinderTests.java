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
        Vector v=new Vector(4, 4, 4);
        Point3D p=  new Point3D(1, 1, 1);
        Ray r = new Ray(v,p);
        Cylinder c=new Cylinder(1, r, 2);
        Point3D p1 = new Point3D(2, 3, 4);
        Vector V = new Vector(-95, -94, -93).normalize();
        assertEquals("the normal of the cylinder is:",V, c.getNormal(p1));
    }
}