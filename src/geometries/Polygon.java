package geometries;

import java.util.List;

import primitives.*;

import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     * @param emission the color of the polygon
     * @param material the value of the material argument of the polygon
     * @param vertices list of vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Color emission, Material material, Point3D... vertices) {
        super(emission, material, null);
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        //for creating a box to the polygon
        Box b= constructBox();
        this.setBox(b);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();

        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with
        // the normal. If all the rest consequent edges will generate the same sign -
        // the
        // polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (int i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    /**
     * constructing a box for a polygon
     * @return box
     */
    private Box constructBox(){
        //puts infinite values dor the fields
        double x1= Double.POSITIVE_INFINITY;
        double x2= Double.NEGATIVE_INFINITY;
        double y1= Double.POSITIVE_INFINITY;
        double y2= Double.NEGATIVE_INFINITY;
        double z1= Double.POSITIVE_INFINITY;
        double z2= Double.NEGATIVE_INFINITY;

        for(Point3D p: _vertices){
                if(p.getC1().get()<x1) x1= p.getC1().get();
                if(p.getC1().get()>x2) x2= p.getC1().get();
                if(p.getC2().get()<y1) y1= p.getC2().get();
                if(p.getC2().get()>y2) y2= p.getC2().get();
                if(p.getC3().get()<z1) z1= p.getC3().get();
                if(p.getC3().get()>z2) z2= p.getC3().get();
        }
        return new Box(x1,x2,y1,y2,z1,z2);
    }


    /**
     * constructor withe the argument of color of the polygon
     * puts default value for the material argument
     * @param color the color of the polygon
     * @param vertices the vertices of the polygon
     */
    public Polygon(Color color, Point3D... vertices) {
        this(color, new Material(0d, 0d, 0), vertices);
    }

    /**
     * constructor of the polygon
     * puts default value for the material argument
     * @param vertices the vertices of the polygon
     */
    public Polygon(Point3D... vertices) {
        this(Color.BLACK, new Material(0d, 0d, 0), vertices);
    }

    /**
     * this function returns the normal of the geometry
     * @param point point
     * @return the normal to the polygon
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    /**
     * this function returns the center point of the wrap box
     * @return the center point of the wrap box
     */
    @Override
    public Point3D getCenterPosition() {
        Point3D c= _vertices.get(0);
        return c;
    }

    /**
     * this function calculate the intersections points
     * (refactoring, returns list of geo points instead of regular points)
     * @param ray the ray thrown toward the geometry
     * @return list of geo points created by the intersection between the ray and the polygon
     */
    @Override
    public List<GeoPoint> findIntsersections(Ray ray) {
        if(!IntersectedBox(ray))
            return null;

        List<GeoPoint> intersections = _plane.findIntsersections(ray);
        if (intersections == null) return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.getDir();

        Vector v1 = _vertices.get(1).subtract(p0);
        Vector v2 = _vertices.get(0).subtract(p0);
        double sign = v.dotProduct(v1.crossProduct(v2));
        if (isZero(sign))
            return null;

        boolean positive = sign > 0;

        for (int i = _vertices.size() - 1; i > 0; --i) {
            v1 = v2;
            v2 = _vertices.get(i).subtract(p0);
            sign = alignZero(v.dotProduct(v1.crossProduct(v2)));
            if (isZero(sign)) return null;
            if (positive != (sign > 0)) return null;
        }

        //the for loop going over all the geo point list and set their geometry field to be poygon
        for (GeoPoint g : intersections) {
            g.geometry = this;
        }
        return intersections;
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