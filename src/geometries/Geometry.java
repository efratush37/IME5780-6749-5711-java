package geometries;

import primitives.Point3D;
import primitives.Vector;
// an interface represents geometries
public interface Geometry {
    /**
     * this function finds the normal of the geometry
     * @param p= point of the object
     * @return the normal vector to the object
     */
    public Vector getNormal(Point3D p);
}
