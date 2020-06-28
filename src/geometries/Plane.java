package geometries;


import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Geometries.plane class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class Plane extends Geometry {
    //fields
    private Point3D _p; //point on the plane
    private Vector _normal; //the normal of the plane

    /**
     * constructor with three arguments
     * @param _p1= point
     * @param _p2= point
     * @param _p3= point
     */
    public Plane(Point3D _p1, Point3D _p2, Point3D _p3) {
        super(
                new Box(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        Vector v1 = _p1.subtract(_p2);
        Vector v2 = _p1.subtract(_p3);
        Vector normal = v1.crossProduct(v2).normalize();
        this._p = new Point3D(_p1);
        this._normal = normal.scale(1);
    }

    /**
     * constructor with two arguments
     * @param _p  the point of the plane
     * @param _normal normal of the object
     */
    public Plane(Point3D _p, Vector _normal) {
        super(
                new Box(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal).normalize();
    }

    /**
     * constructor eithe the argument of color of the plane
     * @param color the color of the plane
     * @param _p the point of the plane
     * @param _normal the normal of the plane
     */
    public Plane(Color color, Point3D _p, Vector _normal){
        this(_p,_normal);
        emission=color;
    }

    /**
     * constructor with all the arguments create the plane
     * @param m the value of the material of the plane
     * @param color the color of the plane
     * @param _p the point of the plane
     * @param _normal the normal of the plane
     */
    public Plane(Material m, Color color, Point3D _p, Vector _normal){
        this(color,_p,_normal);
        material=m;
    }

    /**
     * get method for the point field
     * @return the value of the point field
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * get method for the normal field
     * @return the value of the normal field
     */
    public Vector get_normal() {
        return _normal;
    }

    /**
     * implement to string method
     * @return string describes the object
     */
    @Override
    public String toString() {
        return "Plane{" +
                "_p=" + _p +
                ", _normal=" + _normal +
                '}';
    }

    /**
     * this function returns the normal
     * @return the normal of the plane
     */
    public Vector getNormal() {
        return _normal.normalize();
    }

    /**
     * returns the normal to the cylinder
     * @param p point of the object
     * @return the normal to the plane
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal.normalize();
    }

    /**
     * this function return the center point of the box
     * @return the center point of the box
     */
    @Override
    public Point3D getCenterPosition() {
        return this.get_p();
    }

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the plane
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        //P = P0 + T*V, T >= 0
        // N * Q0 - P = 0
        if(!IntersectedBox(ray))
            return null;

        // vector from p0 of ray to point on the plane
        Vector p0Q;
        try {
            p0Q = _p.subtract(ray.get_p0());
        } catch (IllegalArgumentException e) {
            return null; // ray starts from point Q - no intersections
        }
        // t =
        //N * (Q0 - P0) / (n * v)
        double nv = _normal.dotProduct(ray.getDir());
        // if ray is parallel to the plane - no intersections
        if (isZero(nv))
            return null;

        double t = alignZero(_normal.dotProduct(p0Q) / nv);
        return t <= 0 ? null : List.of(new GeoPoint(this,ray.getPoint(t)));
    }

    /**
     * this function return rather the box is intersected with the ray or not
     * @param ray the constructed ray
     * @return rather the box is intersected with the ray or not
     */
    @Override
    public boolean IntersectedBox(Ray ray) {
        return this.box.IntersectedBox(ray);
    }
}
