package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere extends RadialGeometry {
    //field
    private Point3D _center;

    /**
     * constructor with two arguments
     * @param center= the center point of the sphere
     * @param radius= the radius of the sphere
     */
   Sphere(Point3D center,double radius){
       super(radius);
       _center=center;
   }

    /**
     * get method for the radius field
     * @return the value of the radius field
     */
    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * get method for the center point field
     * @return the value of the center point field
     */
    public Point3D get_center() {
        return _center;
    }

    /**
     * implement to string method
     * @return string describes the object
     */
    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                "} " + super.toString();
    }

    /**
     * returns the normal to the sphere
     * @param p= point of the object
     * @return the normal to the sphere
     */
    @Override
    public Vector getNormal(Point3D p) {
        return super.getNormal(p);
    }
}
