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
    private List<Intersectable> listOfGeo = new ArrayList<Intersectable>(); //collection of geometries holds in a list
    private Box box;//the box wrapped the geometry

    /**
     * an empty constructor
     */
    //public Geometries() {
    //    listOfGeo = new ArrayList<>();
    //}

    /**
     * constructor
     * @param listOfGeo= list of geometries to be added to the collection
     */
    public Geometries(Intersectable... listOfGeo) {
        //this.listOfGeo = new ArrayList<Intersectable>();
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
        constructBox();
    }

    /**
     * this function construct the box to the geometry
     */
    public void constructBox(){
        //puts infinite values for the the fields
        double x1= Double.POSITIVE_INFINITY;
        double y1= Double.POSITIVE_INFINITY;
        double z1= Double.POSITIVE_INFINITY;
        double x2= Double.NEGATIVE_INFINITY;
        double y2= Double.NEGATIVE_INFINITY;
        double z2= Double.NEGATIVE_INFINITY;
        Box temp;
        for(Intersectable geo: listOfGeo){
            temp= geo.getBox();
            if(temp.getX1()<x1) x1= temp.getX1();
            if(temp.getX2()>x2) x2= temp.getX2();
            if(temp.getY1()<y1) y1= temp.getY1();
            if(temp.getY2()>y2) y2= temp.getY2();
            if(temp.getZ1()<z1) z1= temp.getZ1();
            if(temp.getZ2()>z2) z2= temp.getZ2();
        }
        this.box= new Box(x1,x2,y1,y2,z1,z2);
    }

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the geometry
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        if(!IntersectedBox(ray))
            return null;
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

    /**
     * get method for the box field
     * @return the box wrapped the geometry
     */
    @Override
    public Box getBox() {
        return box;
    }

    /**
     * this function returns rather the box is intersected with the ray or not
     * @param ray the constructed ray
     * @return rather the box is intersected with the ray or not
     */
    @Override
    public boolean IntersectedBox(Ray ray) {
      return this.box.IntersectedBox(ray);
    }
}


