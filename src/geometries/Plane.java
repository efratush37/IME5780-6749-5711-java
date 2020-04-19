package geometries;


import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    //fields
    private Point3D _p;
    private Vector _normal;

    /**
     * constructor with three arguments
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
     * @param p= point
     * @param normal= normal of the object
     */
    public Plane(Point3D p, Vector normal) {
        _p = p;
        _normal = normal;
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
     * @return  the value of the normal field
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
     * @param p= point of the object
     * @return the normal to the plane
     */
    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }


}
