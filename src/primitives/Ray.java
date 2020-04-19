package primitives;

import java.util.Objects;

public class Ray {
    //fields
    private Vector dir;
    private Point3D _p0;

    /**
     * constructor with two arguments
     * @param direction= the vector of the ray
     * @param p= point to the ray
     */
    public Ray(Vector direction, Point3D p) {
/*        dir.normalize();*/
        dir = direction;
        _p0 = p;
    }

    /**
     * copy constructor
     * @param r= an existing ray
     */
    public Ray(Ray r) {
        this.dir = r.dir;
        this._p0 = r._p0;
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
     * @param o
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
}
