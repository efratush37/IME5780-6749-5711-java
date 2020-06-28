package MiniProject2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import geometries.*;
import primitives.*;

/**
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 *
 */

/**
 * this class represent the model we want to classify
 */
public class Point {
    //fields
    private Geometry geometry;//the geometry need to be classify to a larger containing box
    private Point3D centerPosition;//the center point of the box wrapped the geometry
    private int cluster_number = 0;//the number of the cluster some points were tagged in

    /**
     * constructor for the model
     * @param g the geometry
     */
    public Point(Geometry g)
    {
        this.geometry=g;
        this.centerPosition=g.getCenterPosition();
    }

    /**
     * get method for the geometry field
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }

    /**
     * set method for the geometry field
     * @param geometry the geometry to set
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    /**
     * get method for the center position field
     * @return the center point of the box wrapped the geometry
     */
    public Point3D getPositionPoint() {
        return centerPosition;
    }

    /**
     * set method for the center position field
     * @param p the positionPoint to set
     */
    public void setPositionPoint(Point3D p) {
        centerPosition = p;
    }

    /**
     * get method for the cluster number field
     * @return the cluster_number
     */
    public int getCluster_number() {
        return cluster_number;
    }

    /**
     * set method for the cluster number field
     * @param cluster_number the number of the cluster
     */
    public void setCluster_number(int cluster_number) {
        this.cluster_number = cluster_number;
    }

    /**
     * this function Calculates the euclidean distance between two points
     * @param p first point
     * @param centroid second point
     * @return the distance between two points
     */
    protected static double distance(Point p, Point centroid) {
        return p.getPositionPoint().distanceSquared(centroid.getPositionPoint());
    }

    /**
     * this function is a wrap function for function Creates random point
     * it sends the minimal and the maximal values for double, that will be the range of the random
     * @return the random point created
     */
    protected static Point createRandomPoint() {
        return Point.createRandomPoint(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
    }

    /**
     * this function create random point
     * @param min the minimal value of the range
     * @param max the maximum value of the range
     * @return the random point created
     */
    protected static Point createRandomPoint(double min, double max) {
        Random r = new Random();
        double x = min + (max - min) * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        double z = min + (max - min) * r.nextDouble();
        return new Point(new Sphere(1,new Point3D(x,y,z)));
    }

    /**
     * this function implement to string method
     * @return string describes the object
     */
    public String toString() {
        return "("+this.centerPosition.getC1()+","+this.centerPosition.getC2()+","+this.centerPosition.getC3()+")";
    }
}