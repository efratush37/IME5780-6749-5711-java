package MiniProject2;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import geometries.*;
import primitives.*;

/**
 * this class represent a cluster, a section in the space that objects will classify to
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * boundary volume
 */
public class Cluster {
    public List<Point> points;//list of points related to the cluster
    public Point centroid;//the center of the cluster, center of a geometric object of uniform density
    public int id;//the number of the cluster

    /**
     * constructor for the cluster
     *
     * @param id the number of the cluster
     */
    public Cluster(int id) {
        this.id = id;
        this.points = new ArrayList<Point>();
        this.centroid = null;
    }

    /**
     * get method for the points field
     * @return the list of point classify to this cluster
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * this function add points to the cluster
     * @param points list of point to add
     */
    public void addPoint(List<Point> points) {
        for (Point p : points) {
            points.add(p);
        }
    }

    /**
     * this function add a point to the cluster
     * @param p the point to add
     */
    public void addPoint(Point p) {
        points.add(p);
    }

    /**
     * set method for the points field
     * @param points the list to be set
     */
    public void setPoints(List<Point> points) {
        this.points = points;
    }

    /**
     * get method for the center point field
     * @return the center point of the cluster
     */
    public Point getCentroid() {
        return centroid;
    }

    /**
     * set method for the center point field
     * @param centroid the point to be set
     */
    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    /**
     * get method for the id of the cluster field
     * @return the id of the cluster
     */
    public int getId() {
        return id;
    }

    /**
     * this function clear the list of the point of the cluster
     */
    public void clear() {
        points.clear();
    }

    /**
     * this function print description about the cluster
     */
    public void plotCluster() {
        System.out.println("[Cluster: " + id + "]");
        System.out.println("[Centroid: " + centroid + "]");
        System.out.println("[Points: \n");
        for (Point p : points) {
            System.out.println(p);
        }
        System.out.println("]");
    }
}