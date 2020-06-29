package MiniProject2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import primitives.Point3D;

/**
 * this class represents the K-means algorithm for classifying
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * boundary volume
 */

public class KMeans {
    private int NUM_CLUSTERS = 3;//the number of clusters we want
    private int MAX_ITERATIONS = 50;//the boundary for iterations

    private List<Point> points;//list of Points need to be classify
    private List<Cluster> clusters;//list of the clusters

    /**
     * constructor for the Kmeans algorithm
     */
    public KMeans() {
        this.points = new ArrayList<Point>();
        this.clusters = new ArrayList<Cluster>();
    }

    /**
     * get method for the clusters list field
     * @return the list of the clusters
     */
    public List<Cluster> getClusters() {
        return clusters;
    }

    /**
     * this function start the process of classifying
     * @param points list of points need to be classify
     */
    public void init(List<Point> points) {
        //setting the field to point to the list
        this.points = points;

        for (int i = 0; i < NUM_CLUSTERS; i++) {
            //create clusters
            Cluster cluster = new Cluster(i);
            //creates random points to be the center point of the cluster
            Point centroid = Point.createRandomPoint(-100,100);
            cluster.setCentroid(centroid);
            //adding to the list of the clusters the cluster just created
            this.clusters.add(cluster);
        }
    }

    /**
     * this function print the state of the clusters
     */
    private void plotClusters() {
        for (Cluster c :clusters) {
            c.plotCluster();
        }
    }

    /**
     * this function calls an iterative method for calculate the Kmeans, for grouping into clusters
     */
    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(!finish && iteration< MAX_ITERATIONS) {
            //Clear cluster state
            clearClusters();

            List<Point> lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List<Point> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            plotClusters();

            if(distance == 0) {
                finish = true;
            }
        }
    }

    /**
     * this function clear the states of all clusters for recalculating
     */
    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    /**
     * this function gets a list of the center points of the cluster, by the geometries
     * the geometry holds the field of the geometry and from their we can know the center point
     * @return list of the center points of the clusters
     */
    private List<Point> getCentroids() {
        List<Point> centroids = new ArrayList<Point>(NUM_CLUSTERS);
        for(Cluster cluster : clusters) {
            Point center = cluster.getCentroid();
            Point point = new Point(center.getGeometry());
            centroids.add(point);
        }
        return centroids;
    }

    /**
     * this function create the classification, add points to the cluster whoes have the minimal distance
     * with the current point(from the center point of the cluster)
     */
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max;
        int cluster = 0;
        double distance = 0.0;

        for(Point point : points) {
            min = max;
            //checking with which cluster's center point we have yhe minimal distance' so were gonna add to this cluster
            for(int i = 0; i < NUM_CLUSTERS; i++) {
                Cluster c = clusters.get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster_number(cluster);
            clusters.get(cluster).addPoint(point);
        }
    }

    /**
     * this function calculating the center points of the clusters
     */
    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            double sumX = 0;
            double sumY = 0;
            double sumZ = 0;
            //getting the points classified to this cluster
            List<Point> listPoints = cluster.getPoints();
            int size = listPoints.size();

            //sum the values of the center position of the box for each Point
            for(Point point : listPoints) {
                sumX += point.getPositionPoint().getC1().get();
                sumY += point.getPositionPoint().getC2().get();
                sumZ += point.getPositionPoint().getC3().get();}

            //make thr center point of the cluster relative to the points instead of randoming it
            Point centroid = cluster.getCentroid();
            if(size > 0) {
                double newX = sumX / size;
                double newY = sumY / size;
                double newZ = sumZ / size;
                Point3D newCentroid=new Point3D(newX,newY,newZ);
                centroid.setPositionPoint(newCentroid);
            }
        }
    }
}