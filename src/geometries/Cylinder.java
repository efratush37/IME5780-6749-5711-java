package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Cylinder extends Tube {
    //field
    private double _height;

    /**
     * constructor with three arguments
     * @param _height= high of the cylinder
     * @param _axisRay= ray of the cylinder
     * @param _radius= radius of the cylinder
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }
    public Cylinder(Color color,double _radius, Ray _axisRay, double _height ){
        this(_radius,_axisRay,_height);
        emission=color;
    }
    public Cylinder(Material m, Color color, double _radius, Ray _axisRay, double _height ){
        this(color,_radius,_axisRay,_height);
        material=m;
    }
    /**
     * get method for the height field
     * @return the value of the height field
     */
    public double get_height() {
        return _height;
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
     * @param point= the point
     * @return= the normal to the cylinder
     */
    @Override
    public Vector getNormal(Point3D point) {
        Point3D o = get_axisRay().get_p0();
        Vector v = get_axisRay().getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return point.subtract(o).normalize();
    }

    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        return super.findIntsersections(ray);
    }
}
