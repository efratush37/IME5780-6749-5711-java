package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * Geometries.Intersectable interface
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this interface represents the ability of geometries of being Intersectable
 */
public interface Intersectable {

    /**
     * an inner Geo Point class which represent an intersected point by both the value of the point
     * and the kind of geometry has been intersected
     * refactoring, instead of returning point3d as intersected point
     */
    public static class GeoPoint {
        //fields
        public Geometry geometry;
        public Point3D point;

        /**
         * a constructor for the geo point, which gets two arguments, the point and the geometry
         * @param geometry the geometry has been intersected
         * @param point the point where the geometry has been intersected
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        public Point3D getPoint() {
            return point;
        }

        /**
         * this function implements the equals method(comparing)
         * @param o an object who's going to be comparing
         * @return rether the two arguments has been compered are equal or not
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return this.geometry.equals(geoPoint.geometry) &&
                    this.point.equals(geoPoint.point);
        }
    }

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the geometry
     */
    List<GeoPoint> findIntsersections(Ray ray);
}
