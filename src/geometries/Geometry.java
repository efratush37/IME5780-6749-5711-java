package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

// an abstract class represents geometries
public abstract class Geometry implements Intersectable {
    protected Color emission;
    protected Material material;


    public Geometry(Color c, Material m){
        this.emission=c;
        this.material=m;
    }
    public Geometry(Color color) {
       this(color, new Material(0, 0, 0));
    }

    public Geometry() {
        this(Color.BLACK);
    }

    public Color getEmission() {
        return emission;
    }

    public Material getMaterial() {
        return material;
    }
    /**
     * this function finds the normal of the geometry
     *
     * @param p= point of the object
     * @return the normal vector to the object
     */
    public abstract Vector getNormal(Point3D p);
}
