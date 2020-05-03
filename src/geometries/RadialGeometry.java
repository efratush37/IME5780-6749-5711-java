package geometries;

import primitives.Point3D;
import primitives.Vector;
//an abstract class
public abstract class RadialGeometry implements Geometry {
    //field
    public double _radius;

    /**
     * copy constructor with one argument
     * @param radius= the radius of the object
     */
    public RadialGeometry(double radius) {
        _radius = radius;
    }

    /**
     * copy constructor
     * @param r= an existing radical geometry
     */
    public RadialGeometry(RadialGeometry r) {
        this._radius = r._radius;
    }

    /**
     * get method for the radius field
     * @return the value of the radius field
     */
    public double get_radius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "radius = " + _radius;
    }
}
