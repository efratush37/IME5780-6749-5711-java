package geometries;


import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane implements Geometry {
    //fields
    private Point3D _p;
    private Vector _normal;

    /**
     * constructor with three arguments
     *
     * @param _p1= point
     * @param _p2= point
     * @param _p3= point
     */
    public Plane(Point3D _p1, Point3D _p2, Point3D _p3) {
        Vector v1 = _p1.subtract(_p2);
        Vector v2 = _p1.subtract(_p3);
        Vector normal = v1.crossProduct(v2).normalize();
        this._p = new Point3D(_p1);
        this._normal = normal.scale(1);
    }

    /**
     * constructor with two arguments
     *
     * @param _p      point
     * @param _normal normal of the object
     */
    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal).normalize();
    }

    /**
     * get method for the point field
     *
     * @return the value of the point field
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * get method for the normal field
     *
     * @return the value of the normal field
     */
    public Vector get_normal() {
        return _normal;
    }

    /**
     * implement to string method
     *
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
     *
     * @return the normal of the plane
     */
    public Vector getNormal() {
        return _normal.normalize();
    }

    /**
     * returns the normal to the cylinder
     *
     * @param p= point of the object
     * @return the normal to the plane
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal.normalize();
    }


    /**
     * this function calculate the intersections points
     *
     * @param ray= the ray thrown toward the geometry
     * @return list of point created by the intersection between the ray and the geometry
     */
    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        //P = P0 + T*V, T >= 0
        // N * Q0 - P = 0


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

        return t <= 0 ? null : List.of(ray.getPoint(t));
    }
}
