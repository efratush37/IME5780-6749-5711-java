package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

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

    /**
     * a constructor to the geometry
     * puts default values for the fields
     */
    public Geometry() {
        this(Color.BLACK);
        this.material = new Material(0d, 0d, 0);
    }

    /**
     * a constructor with a paramater for the emission color of the geometry
     * @param emission the color of the geometry
     * this constructor calls the constructor with 2 arguments and set the material with default values
     */
    public Geometry(Color emission) {
        this(emission, new Material(0d, 0d, 0));
    }

    /**
     * a constructor with two arguments
     * @param emission the color of the geometry
     * @param material the material the geometry made of
     */
    public Geometry(Color emission, Material material) {
        this.emission = emission;
        this.material = material;
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
}
