package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Tube extends RadialGeometry {
    //field
    private Ray _axisRay;

    /**
     * constructor with two arguments
     * @param _axisRay= ray of the tube
     * @param _radius= radius of the tube
     */
    public Tube(double _radius, Ray _axisRay) {
        super(_radius);
        this._axisRay = new Ray(_axisRay);
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
        Point3D o = _axisRay.get_p0();
        Vector v = _axisRay.getDir();
        Vector vector1 = p.subtract(o);

        //We need the projection to multiply the _direction unit vector
        double projection = vector1.dotProduct(v);
        if(!isZero(projection))
        {
            // projection of P-O on the ray:
            o = o.add(v.scale(projection));
        }
        //This vector is orthogonal to the _direction vector.
        Vector check = p.subtract(o);
        return check.normalize();
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }
}
