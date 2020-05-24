package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;
/**
 * Geometries.Triangle class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class Triangle extends Polygon {
    /**
     *  constructor with the values for all the arguments
     * @param emission the color of the triangle
     * @param material the value of the material of the triangle
     * @param p1 a point
     * @param p2 a point
     * @param p3 a point
     */
    public Triangle(Color emission, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emission, material, p1, p2, p3);
    }

    /**
     * constructor with thw color of the triangle
     * @param emission the color of the triangle
     * @param p1 a point
     * @param p2 a point
     * @param p3 a point
     */
    public Triangle(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        this(emission, new Material(0d, 0d, 0),  p1, p2, p3);
    }

    /**
     * constructor with the argument of the material of the triangle
     * @param material the value of the material of the triangle
     * @param p1 a point
     * @param p2 a point
     * @param p3 a point
     */
    public Triangle(Material material, Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, material, p1, p2, p3);
    }

    /**
     * constructor withe three arguments
     * puts a default values in the other fields
     * @param p1 a point
     * @param p2 a point
     * @param p3 a point
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, new Material(0, 0, 0), p1, p2, p3);
    }

    /**
     * to string method
     *
     * @return description of an object
     */
    @Override
    public String toString() {
        return "Triangle{\n" +
                "vertices = " + _vertices +
                "\nplane = " + _plane +
                '}';
    }

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo point created by the intersection between the ray and the triangle
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        List<GeoPoint> intersections = _plane.findIntsersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.getDir();
        //concerning that the geometry field in the geo point wil be triangle
        GeoPoint geoPoint = new GeoPoint(this, intersections.get(0).point);

        Vector v1 = _vertices.get(0).subtract(p0).normalize();
        Vector v2 = _vertices.get(1).subtract(p0).normalize();
        Vector v3 = _vertices.get(2).subtract(p0).normalize();

        double s1 = v.dotProduct(v1.crossProduct(v2));
        if (isZero(s1)) return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2)) return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3)) return null;

        //they all have the same sign
        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? List.of(geoPoint) : null;

    }
}
