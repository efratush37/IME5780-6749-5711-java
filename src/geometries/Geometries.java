package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Geometries implements Intersectable {
    //field
    private List<Intersectable> listOfGeo = new ArrayList<>();

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
     *
     * @param geometries= geometries needed to be add to the collection
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geometry : geometries ) {
            listOfGeo.add(geometry);
        }
    }


    /**
     * this function calculate the intersections points
     * @param ray= the ray thrown toward the geometry
     * @return list of point created by the intersection between the ray and the geometry
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        List<GeoPoint> intersections = null;

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


