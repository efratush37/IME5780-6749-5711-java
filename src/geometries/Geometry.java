package geometries;

import primitives.*;

/**
 * Geometries.Geometry class
 * this is an abstract class represents geometry
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
// refactoring to an abstract class represents geometries
public abstract class Geometry implements Intersectable {
    //fields
    protected Color emission; //the emission color of a geometry
    protected Material material; //the material that the geometry is made of
    Box box;//the box of the geometry

    /**
     * a constructor to the geometry
     * puts default values for the fields
     * @param b the box wrapped the geometry
     */
    public Geometry(Box b) {
        this(Color.BLACK,b);
        this.material = new Material(0d, 0d, 0);
    }

    /**
     * a constructor with a paramater for the emission color of the geometry
     * @param emission the color of the geometry
     * @param b the box wrapped the geometry
     * this constructor calls the constructor with 2 arguments and set the material with default values
     */
    public Geometry(Color emission, Box b) {
        this(emission, new Material(0d, 0d, 0),b);
    }

    /**
     * a constructor with two arguments
     * @param emission the color of the geometry
     * @param material the material the geometry made of
     * @param b the box wrapped the geometry
     */
    public Geometry(Color emission, Material material, Box b) {
        this.emission = emission;
        this.material = material;
        this.box= b;
    }

    /**
     * get method for the emission field
     * @return the value of the color of the emission field
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * get method for the material field
     * @return the value of the material field the geometry made of
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * this function finds the normal of the geometry
     *(refactoring to abstract function in abstract class)
     * @param p point of the object
     * @return the normal vector to the object
     */
    public abstract Vector getNormal(Point3D p);

    /**
     * this function finds the center of the box
     * @return center of the box
     */
    public abstract Point3D getCenterPosition();

    /**
     * get method for the box field
     * @return the box wrapped the geometry
     */
    @Override
    public Box getBox() {
        return box;
    }

    /**
     * set method for the box field
     * @param box the box wrapped the geometry
     */
    public void setBox(Box box) {
        this.box = box;
    }
}
