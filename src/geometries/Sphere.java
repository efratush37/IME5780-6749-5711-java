package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry {
    //field
    private Point3D _center;

    /**
     * constructor with two arguments
     *
     * @param _center= the center point of the sphere
     * @param _radius= the radius of the sphere
     */
    public Sphere(double _radius, Point3D _center) {
        super(_radius);
        this._center = _center;
    }

    public Sphere(Color color,double _radius, Point3D _center) {
        this(_radius,_center);
        emission=color;
    }
    public Sphere(Material m,Color color, double _radius, Point3D _center) {
        this(color,_radius,_center);
       material=m;
    }
    /**
     * get method for the radius field
     *
     * @return the value of the radius field
     */
    @Override
    public double get_radius() {
        return super.get_radius();
    }

    /**
     * get method for the center point field
     *
     * @return the value of the center point field
     */
    public Point3D get_center() {
        return _center;
    }

    /**
     * implement to string method
     *
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
     *
     * @param p= point of the object
     * @return the normal to the sphere
     */
    @Override
    public Vector getNormal(Point3D p) {
        return (p.subtract(_center)).normalize();
    }

    /**
     * this function calculate the intersections points
     * @param ray= the ray thrown toward the geometry
     * @return list of point created by the intersection between the ray and the geometry
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        Point3D p0 = ray.get_p0();
        Vector v = ray.getDir();
        Vector u;
        try {
            u = _center.subtract(p0);   // p0 == _center
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this,ray.getPoint(_radius)));
        }
        double tm = alignZero(v.dotProduct(u));
        double dSquared = (tm == 0) ? u.lengthSquared() : u.lengthSquared() - tm * tm;
        double thSquared = alignZero(_radius * _radius - dSquared);

        if (thSquared <= 0) return null;

        double th = alignZero(Math.sqrt(thSquared));
        if (th == 0) return null;

        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0) return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));//P1 , P2
        if (t1 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        else
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
    }


}