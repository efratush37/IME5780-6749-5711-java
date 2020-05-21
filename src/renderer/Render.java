package renderer;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

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
     *
     * @param img an image writer object
     * @param s   a scene object
     */
    public Render(ImageWriter img, Scene s) {
        IMwr = img;
        scene = s;
    }

    /**
     * this function create the background image by the intersection points
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
                List<GeoPoint> intersectionPoints = scene.get_geometries().findIntsersections(ray);
                if (intersectionPoints == null) {
                    IMwr.writePixel(j, i, scene.get_background().getColor());
                } else {
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    IMwr.writePixel(j, i, (calcColor(closestPoint)).getColor());
                }
            }
        }
    }

    /**
     * this function calculate the color of a point
     *
     * @param p=a point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint p) {
        Color color = scene.get_ambientLight().get_intensity();
        color = color.add(p.geometry.getEmission());
        Vector v = p.point.subtract(scene.get_camera().getp0()).normalize();
        Vector n = p.geometry.getNormal(p.point);
        Material m = p.geometry.getMaterial();
        int shine = m.getnShininess();
        double kd = m.getkD();
        double ks = m.getkS();
        for (LightSource lightSource : scene.get_lights()) {
            Vector l = lightSource.getL(p.point);
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));

            if (sign(nl) == sign(nv)) {
                Color ip = lightSource.getIntensity(p.point);
                color = color.add(
                        calcDiffusive(kd, nl, ip),
                        calcSpecular(ks, l, n, nl, v, shine, ip));
            }
        }
        return color;
    }

    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) {
            nl = -nl;
        }

        return ip.scale(nl * kd);
    }

    private boolean sign(double val) {
        return (val > 0d);
    }

    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {
        double p = nShininess;

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        // [rs,gs,bs](-V.R)^p
        return ip.scale(ks * Math.pow(minusVR, p));
    }



    /**
     * this function chek which point is the closest to the camera
     * @param points= list of intersections points
     * @return the closest point to the camera
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points) {

        GeoPoint result = null;
        double minD = Double.MAX_VALUE;

        Point3D p0 = this.scene.get_camera().getp0();

        for (GeoPoint p : points) {
            double distance = p0.distance(p.point);
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
