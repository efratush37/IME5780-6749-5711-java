package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Geometries.geometries class
 * this class represents collection of geometries
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */

public class Geometries implements Intersectable {
    //field
    private List<Intersectable> listOfGeo = new ArrayList<>(); //collection of geometries holds in a list

    /**
     * an empty constructor
     */
    public Geometries() {
        listOfGeo = new ArrayList<>();
    }

    /**
     * constructor
     * @param listOfGeo= list of geometries to be added to the collection
     */
    public Geometries(Intersectable... listOfGeo) {
        this.listOfGeo = new ArrayList<Intersectable>();
        add(listOfGeo);
    }

    /**
     * this function represents adding a geometry to the list holdes the collection
     * @param geometries= geometries needed to be add to the collection
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries ) {
            listOfGeo.add(geometry);
        }
    }


    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the geometry
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        List<GeoPoint> intersections = new ArrayList<GeoPoint>();

        for (Intersectable geometry : listOfGeo) {
            List<GeoPoint> result = geometry.findIntsersections(ray);
            if (result != null) {
                intersections.addAll(result);
            }
        }
        if (intersections.size()> 0)
            return intersections;
        return null;
    }
}


