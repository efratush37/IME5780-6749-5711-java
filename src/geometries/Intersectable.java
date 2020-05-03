package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

public interface Intersectable {
    /**
     * this function calculate the intersections points
     * @param ray= the ray thrown toward the geometry
     * @return list of point created by the intersection between the ray and the geometry
     */
    List<Point3D> findIntsersections(Ray ray);
}
