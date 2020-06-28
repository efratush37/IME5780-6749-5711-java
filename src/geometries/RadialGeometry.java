package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometries.Radial Geometry class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * an abstract class represents radial geometry which extend the geometry and add a radius field field
 */
//an abstract class
public abstract class RadialGeometry extends Geometry {
    //field
    public double _radius; //radius of the radial geometries

    /**
     * copy constructor with one argument
     * @param radius the radius of the object
     * @param b the box wrap the geometry
     */
    public RadialGeometry(double radius, Box b) {
        super(b);
        _radius = radius;
    }

    /**
     * constructor with three arguments
     * @param c the color of the geometry
     * @param r the radius
     * @param b the box wrap the geometry
     */
    public RadialGeometry(Color c, double r, Box b) {
        super(c, b);
        this._radius = r;
    }

    /**
     * a constructor with three arguments
     * @param c the color of the geometry
     * @param m the value of the material argument of the geometry
     * @param r the radius of the radial geometry
     * @param b the box wrap the geometry
     */
    public RadialGeometry(Color c, Material m, double r, Box b) {
        super(c, m, b);
        this._radius = r;
    }

    /**
     * copy constructor
     * @param r an existing radical geometry
     */
    public RadialGeometry(RadialGeometry r) {
        super(r.getBox());
        this._radius = r._radius;
    }

    /**
     * get method for the radius field
     * @return the value of the radius field
     */
    public double get_radius() {
        return _radius;
    }

    /**
     * implements to string method
     * @return a string describes the object
     */
    @Override
    public String toString() {
        return "radius = " + _radius;
    }
}
