package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

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


    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        // Ray Points: P = P0 + t * v , t >= 0
        // Sphere points: abs(p^2 - O^2) - r^2 = 0
        //u = O - P0
        Point3D p0 = ray.get_p0();
        Vector v = ray.getDir();
        try {
            Vector u;
            u = _center.subtract(p0);
            //tm = v * u
            double tm = alignZero(v.dotProduct(u));
            // d= sqrt(u.squredLength - tm^2)
            double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

            //⇨ if (d>r) there are no intersections
            if (alignZero(d - this.get_radius()) >= 0)
                return null;
            else {
                //th = sqrt(r^2 - d^2)
                double th = alignZero(Math.sqrt(this.get_radius() * this.get_radius() - d * d));

                //t1,2 = tm +- th, pi = p0 + ti
                double t1 = tm + th;
                double t2 = tm - th;

                List<Point3D> result = new ArrayList<Point3D>();

                // take only t >= 0
                if (alignZero(t1) > 0)
                    result.add(new Point3D(p0.add(v.scale(t1))));
                if (alignZero(t2) > 0)
                    result.add(new Point3D(p0.add(v.scale(t2))));
                if(t1 <=0 && t2 <=0)
                    return null;
                return result;
            }
        } catch (IllegalArgumentException e) {
            // if ray start at the center
            // => return center + radius
            Point3D p2 = new Point3D(p0.add(v.scale(this.get_radius())));
            return List.of(p2);
        }
    }
}
