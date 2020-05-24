package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Geometries.Cylinder class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class Cylinder extends Tube {
    //field
    private double _height;

    /**
     * @param m the value of the material of the cylinder
     * @param color  the color of the cylinder
     * @param _radius radius of the cylinder
     * @param _axisRay ray of the cylinder
     * @param _height height of the cylinder
     */
    public Cylinder(Color color, Material m, double _radius, Ray _axisRay, double _height) {
        super(color, m, _radius, _axisRay);
        this._height = _height;
    }

    /**
     * a constructor with the color argument in addition to the three
     * calls the  other constructor
     * @param color the color of the cylinder
     * @param _radius radius of the cylinder
     * @param _axisRay ray of the cylinder
     * @param _height height of the cylinder
     */
    public Cylinder(Color color, double _radius, Ray _axisRay, double _height) {
        this(color, new Material(0d, 0d, 0), _radius, _axisRay, _height);
    }

    /**
     * constructor with four arguments
     * puts default value to the color field who's do not get a value parameter
     * @param material the material value of the cylinder
     * @param _radius radius of the cylinder
     * @param _axisRay ray of the cylinder
     * @param _height height of the cylinder
     */
    public Cylinder(Material material, double _radius, Ray _axisRay, double _height) {
        this(Color.BLACK, material, _radius, _axisRay, _height);
    }

    /**
     * constructor with three arguments
     * puts default values to the fields that their values were not send as a parameter
     * @param _radius  is the radius of the cylinder
     * @param _axisRay is the ray
     * @param _height  the height of the Cylinder
     */
    public Cylinder(double _radius, Ray _axisRay, double _height) {
        this(Color.BLACK, new Material(0, 0, 0), _radius, _axisRay, _height);
    }


    /**
     * get method for the height field
     *
     * @return the value of the height field
     */
    public double get_height() {
        return _height;
    }


    /**
     * implement to string method
     *
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
     *
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

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the cylinder
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        return super.findIntsersections(ray);
    }
}
