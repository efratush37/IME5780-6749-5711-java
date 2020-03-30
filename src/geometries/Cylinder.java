package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

public class Cylinder extends Tube {
    //field
    private double _height;

    /**
     * constructor with three arguments
     * @param h= high of the cylinder
     * @param r= ray of the cylinder
     * @param radius= radius of the cylinder
     */
    public Cylinder(double h, Ray r, double radius) {
        super(r, radius);
        _height = h;
    }

    /**
     * get method for the height field
     * @return the value of the height field
     */
    public double get_height() {
        return _height;
    }

    /**
     * get method for the ray field
     * @return the value of the ray field
     */
    @Override
    public Ray get_axisRay() {
        return super.get_axisRay();
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
        return "Cylinder{" +
                "_height=" + _height +
                "} " + super.toString();
    }

    /**
     * returns the normal to the cylinder
     * @param p= the point
     * @return= the normal to the cylinder
     */
    @Override
    public Vector getNormal(Point3D p) {
        return super.getNormal(p);
    }
}
