package renderer;

import elements.Camera;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;
/**
 * Render class which responsible of creating the color matrix of the scene
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class Render {
    //fields
    private ImageWriter IMwr;
    private Scene scene;

    /**
     * a constructor for the Render object
     * @param img an image writer object
     * @param s a scene object
     */
    public Render(ImageWriter img, Scene s) {
        IMwr = img;
        scene = s;
    }

   /**
    this function create the background image by the intersection points
    */
    public void renderImage() {
        Camera camera = scene.get_camera();
        Intersectable geometries = scene.get_geometries();
        java.awt.Color background = scene.get_background().getColor();
        Double distance = scene.get_distance();

        int nX = IMwr.getNx();
        int nY = IMwr.getNy();
        Double width = IMwr.getWidth();
        Double height = IMwr.getHeight();

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, distance, width, height);
                List<Point3D> intersectionPoints = geometries.findIntsersections(ray);
                if (intersectionPoints == null) {
                    IMwr.writePixel(j, i, background);
                } else {
                    Point3D closestPoint = getClosestPoint(intersectionPoints);
                    IMwr.writePixel(j, i, (calcColor(closestPoint)).getColor());
                }
            }
        }
    }

    /**
     * this function calculate the color of a point
     * @param p=a point
     * @return the color of the point
     */
    private Color calcColor(Point3D p) {
        return scene.get_ambientLight().get_intensity();
    }

    /**
     * this function chek which point is the closest to the camera
     * @param points= list of intersections points
     * @return the closest point to the camera
     */
    private Point3D getClosestPoint(List<Point3D> points) {

        Point3D result = null;
        double minD = Double.MAX_VALUE;

        Point3D p0 = this.scene.get_camera().getp0();

        for (Point3D p : points) {
            double distance = p0.distance(p);
            if (distance < minD) {
                minD = distance;
                result = p;
            }
        }
        return result;
    }

    /**
     * this function print the grid of the image
     * @param interval= a parameter which symbolize the grid location
     * @param color=the color of the grid
     */
    public void printGrid(int interval, java.awt.Color color) {
        int Nx = IMwr.getNx();
        int Ny = IMwr.getNy();
        for (int i = 0; i < Ny; i++) {
            for (int j = 0; j < Nx; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    IMwr.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * write the image
     */
    public void writeToImage() {
        IMwr.writeToImage();
    }
}
