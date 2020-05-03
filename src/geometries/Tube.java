package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Tube extends RadialGeometry {
    //field
    private Ray _axisRay;

    /**
     * constructor with two arguments
     *
     * @param _axisRay= ray of the tube
     * @param _radius=  radius of the tube
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
    }

    /**
     * get method for the ray field
     *
     * @return the value of the ray field
     */
    public Ray get_axisRay() {
        return _axisRay;
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
     * implement to string method
     *
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
     *
     * @param p= point of the object
     * @return the normal to the tube
     */
    @Override
    public Vector getNormal(Point3D p) {
        Point3D o = _axisRay.get_p0();
        Vector v = _axisRay.getDir();
        Vector vector1 = p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if (!isZero(projection)) {
            // projection of P-O on the ray:
            o = o.add(v.scale(projection));
        }
        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        Vector vTube = _axisRay.getDir();
        Vector vectorV0;
        Vector vXvTube;
        Vector rayDirXvTube;
        try {
            vectorV0 = ray.get_p0().subtract(_axisRay.get_p0());
        } catch (IllegalArgumentException e) {
            vectorV0 = new Vector(0,0,0);
        }
        try {
            rayDirXvTube = vectorV0.crossProduct(vTube);
        } catch (IllegalArgumentException e) {
            rayDirXvTube = new Vector(0,0,0);
        }
        try {
            vXvTube = ray.getDir().crossProduct(vTube);
        } catch (IllegalArgumentException e) {
            vXvTube = new Vector(0,0,0);
        }

        // Cylinder [Ray(Point A,Vector V), r].
        // Point P on infinite cylinder if ((P - A) x (V))^2 = r^2 * V^2
        // P = O + t * V1
        // expand : ((O - A) x (V) + t * (V1 x V))^2 = r^2 * V^2

        double vTube2 = Util.alignZero(vTube.lengthSquared());
        double a = Util.alignZero(vXvTube.lengthSquared());
        double b = Util.alignZero(2 * vXvTube.dotProduct(rayDirXvTube));
        double c = Util.alignZero(rayDirXvTube.lengthSquared() - (_radius * _radius * vTube2));
        double d = Util.alignZero(b * b - 4 * a * c);
        if (d < 0) return null;
        if (a == 0)
            return null;
        double t1 = Util.alignZero((-b - Math.sqrt(d)) / (2 * a));
        double t2 = Util.alignZero((-b + Math.sqrt(d)) / (2 * a));
        if (t1 <= 0 && t2 <= 0) return null;
        if (t1 > 0 && t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        if (t1 > 0)
            return List.of(ray.getPoint(t1));
        else
            return List.of(ray.getPoint(t2));
    }
}
