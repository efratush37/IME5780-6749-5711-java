package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

public interface Intersectable {

    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;
        public GeoPoint(Geometry g, Point3D p){
            this.geometry=g;
            this.point=p;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }
    }

    /**
     * this function calculate the intersections points
     * @param ray= the ray thrown toward the geometry
     * @return list of point created by the intersection between the ray and the geometry
     */
    List<GeoPoint> findIntsersections(Ray ray);
}
