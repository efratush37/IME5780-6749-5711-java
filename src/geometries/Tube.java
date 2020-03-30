package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    //field
    private Ray _axisRay;

    /**
     * constructor with two arguments
     * @param r= ray of the tube
     * @param radius= radius of the tube
     */
    public Tube(Ray r, double radius) {
        super(radius);
        _axisRay = r;
    }

    /**
     * get method for the ray field
     * @return the value of the ray field
     */
    public Ray get_axisRay() {
        return _axisRay;
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
     * implement to string method
     * @return string describes the object
     */
    @Override
    public String toString() {
        return "Tube{" +
                "_axisRay=" + _axisRay +
                "} " + super.toString();
    }

    /**
     * returns the normal to the tube
     * @param p= point of the object
     * @return the normal to the tube
     */
    @Override
    public Vector getNormal(Point3D p) {
        return super.getNormal(p);
    }
}
