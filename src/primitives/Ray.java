package primitives;

import java.util.Objects;

import static primitives.Util.isZero;

public class Ray {
    //fields
    private Vector dir;
    private Point3D _p0;

    /**
     * constructor with two arguments
     * @param dir  the vector of the ray
     * @param _p0 point to the ray
     */
    public Ray(Point3D _p0, Vector dir) {
        this._p0 = new Point3D(_p0);
        this.dir = new Vector(dir.normalize());
    }

    /**
     * copy constructor
     * @param r= an existing ray
     */
    public Ray(Ray r) {
        this._p0 = new Point3D(r._p0);
        this.dir = new Vector(r.dir);
    }

    /**
     * get method for the point field
     * @return the value of the point field
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * get method for the vector field
     * @return the value of the vector field
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * implement equal method(comparing)
     * @param o Object
     * @return true or false value regarding their equality
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(dir, ray.dir) &&
                Objects.equals(_p0, ray._p0);
    }

    /**
     * implement to string method
     * @return string describes the object
     */
    @Override
    public String toString() {
        return "Ray{" +
                "dir=" + dir +
                ", _p0=" + _p0 +
                '}';
    }

    /**
     * refactoring for calculating the value of a point on a ray
     * @param t= parameter who means the value of scalar
     * @return new point after the addition of the scalar multiplication
     */
    public Point3D getPoint(double t) {
        return isZero(t) ? _p0 : _p0.add(dir.scale(t));
    }
}
