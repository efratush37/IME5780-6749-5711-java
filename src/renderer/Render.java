package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 * Render class which responsible of creating the color matrix of the scene
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class Render {
    //fields
    private ImageWriter IMwr; //an image writer object for creating scene
    private Scene scene; //the scene includes all the objects
    private static final double DELTA = 0.1; //minimal size to add to the shadow rays in order to avoid from intersected with the geometry
    private static final int MAX_CALC_COLOR_LEVEL = 10; //the maximum level can be afford for stopping the recursion
    private static final double MIN_CALC_COLOR_K = 0.001; //the minimum level can be afford for stopping the recursion

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
     * this function create the image pixel after pixel
     * (refactoring- now when we create the image, were going on a list of geo points)
     * (refactoring- now we use the findCLosestIntersection function instead of closest point)
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

                List<GeoPoint> intersectionPoints = geometries.findIntsersections(ray);
                GeoPoint closestpoint = findCLosestIntersection(ray);
                if (intersectionPoints == null) {
                    IMwr.writePixel(j, i, scene.get_background().getColor());
                } else {
                    IMwr.writePixel(j, i, (calcColor(closestpoint, ray)).getColor());
                }
            }
        }
    }

    /**
     * this function is a wrapping function for the calcColor
     * this function gets two arguments and send to the calcColor with 4 arguments
     *
     * @param geop  the geo point that sets the color of the pixel to the calculation
     * @param inray the ray that constructed from the camera and intersected the geometry
     * @return the color of the point for calculating the color for the pixel
     */
    private Color calcColor(GeoPoint geop, Ray inray) {
        return calcColor(geop, inray, MAX_CALC_COLOR_LEVEL, 1.0).add(scene.get_ambientLight().get_intensity());
    }

    /**
     * this function calculate the color of a point from calculating the color of the pixel
     * (refactoring- now we get geo point instead of regular point)
     * (refactoring- we add the shadow, reflection and the refraction influences)
     *
     * @param p     the geo point that sets the color of the pixel to the calculation
     * @param ray   the ray that constructed from the camera and intersected the geometry
     * @param level the level of the recursion
     * @param k     comparison measure for the recursion
     * @return the color of the point for calculating the color for the pixel
     */
    private Color calcColor(GeoPoint p, Ray ray, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K)
            return Color.BLACK;
        if (level == 1) {
            return Color.BLACK;
        }

        Color color = p.getGeometry().getEmission();
        Vector v = p.getPoint().subtract(scene.get_camera().getp0()).normalize();
        Vector n = p.getGeometry().getNormal(p.getPoint());
        Material m = p.getGeometry().getMaterial();
        int shine = m.getnShininess();
        double kd = m.getkD();
        double ks = m.getkS();
        //regarding the Phong model, calculating the color by the influence of all the light sources on the point
        for (LightSource lightSource : scene.get_lights()) {
            Vector l = lightSource.getL(p.getPoint());
            double nl = alignZero(n.dotProduct(l));
            double nv = alignZero(n.dotProduct(v));

            //calculating the diffiuse and rhe specular influences
            if (sign(nl) == sign(nv)) {
                Ray main=new Ray(p.getPoint(), l);
                LinkedList<Ray> listRay = scene.get_camera().constructRayBeamThroughPixel(ray,
                       p, 300, 50, scene.get_camera().getVup(), scene.get_camera().getVright());

                double ktr = transparency(lightSource, p, listRay);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color ip = lightSource.getIntensity(p.getPoint()).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, nl, ip),
                            calcSpecular(ks, l, n, nl, v, shine, ip));
                }
            }
        }

        //calculating the reflection influence nnnn
        double kr = p.getGeometry().getMaterial().get_kR();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(p.getPoint(), ray, n);
            GeoPoint reflectedpoint = findCLosestIntersection(reflectedRay);
            if (reflectedpoint != null) {
                color = color.add(calcColor(reflectedpoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        //calculating the refraction influence
        double kt = p.getGeometry().getMaterial().get_kT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(p.getPoint(), ray, n);
            GeoPoint refrectedPoint = findCLosestIntersection(refractedRay);
            if (refrectedPoint != null) {
                color = color.add(calcColor(refrectedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }

    /**
     * this function returns rather the given parameter is positive
     *
     * @param val double parameter
     * @return rather the given parameter is positive
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * a helper method for calculating the diffusive color
     *
     * @param kd material attenuation coefficient
     * @param nl vector represents scalar product between the vector from the light source and the normal
     * @param ip the color calculated without the diffusive
     * @return the color caused by the diffusive influence
     */
    private Color calcDiffusive(double kd, double nl, Color ip) {
        if (nl < 0) {
            nl = -nl;
        }
        return ip.scale(nl * kd);
    }

    /**
     * a helper method for calculating the specular color
     *
     * @param ks         material attenuation coefficient
     * @param l          the vector from the light source
     * @param n          the normal vector to the intersected point
     * @param nl         vector represents scalar product between the vector from the light source and the normal
     * @param v          vector construct from the camera to the intersected point
     * @param nShininess glitter coefficient of the material
     * @param ip         the color calculated without the specular
     * @return the color caused by the specular influence
     */
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
     * this function construct a refraction ray from the ray intersected withe the geometry
     *
     * @param pointGeo the intersected point
     * @param inRay    the vector from the light source
     * @param n        the normal vector to the intersected point
     * @return refraction vector
     */
    private Ray constructRefractedRay(Point3D pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getDir(), n);
    }

    /**
     * this function construct a reflection ray from the ray intersected withe the geometry
     *
     * @param pointGeo the intersected point
     * @param inRay    the vector from the light source
     * @param n        the normal vector to the intersected point
     * @return reflection vector
     */
    private Ray constructReflectedRay(Point3D pointGeo, Ray inRay, Vector n) {
        //𝒓=𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * this function calculate the influence of shadows
     * refactoring so that the function will work with a beam of rays or one way as wish
     *
     * @param ls      the light source that illuminates the geometry
     * @param gp      the intersected point
     * @param listray a list of rays represents a beam
     * @return the level of the transparent influence on the objects
     */
    private double transparency(LightSource ls, GeoPoint gp, List<Ray> listray) {
        double result = 0.0;
        double ktr = 0.0;
        //going over the list for calculating the transparency as an average of the influence of each ray
        for (Ray r : listray) {
            List<GeoPoint> intersections = scene.get_geometries().findIntsersections(r);
            if (intersections == null) {
                result = result + 1.0;
            } else {
                double lightDistance = ls.getDistance(gp.getPoint());
                ktr = 1.0;
                for (GeoPoint geoP : intersections) {
                    if (alignZero(geoP.getPoint().distance(gp.getPoint()) - lightDistance) <= 0)
                        ktr = ktr * geoP.getGeometry().getMaterial().get_kT();
                    if (ktr < MIN_CALC_COLOR_K) {
                        ktr = 0.0;
                        break;
                    }
                }
            }
            result = result + ktr;
        }

        double average = result / listray.size();
        return average;
    }


    /**
     * this function cheak which point value of the geo point is the closest to the camera
     * (refactoring- now we get geo point and return geo point instead of regular point)
     *
     * @param points= list of geo point representing intersections points of the geometry
     * @return the geo point that it's point value closest to the camera
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
     * this function calculates the closest intersection
     *
     * @param ray the ray constructed
     * @return the closest intersection point
     */
    private GeoPoint findCLosestIntersection(Ray ray) {
        if (ray == null)
            return null;
        GeoPoint cloesestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D rayp0 = ray.get_p0();

        List<GeoPoint> intersections = scene.get_geometries().findIntsersections(ray);
        if (intersections == null)
            return null;
        for (GeoPoint gp : intersections) {
            double distance = rayp0.distance(gp.getPoint());
            if (distance < closestDistance) {
                closestDistance = distance;
                cloesestPoint = gp;
            }
        }
        return cloesestPoint;
    }

    /**
     * this function print the grid of the image
     *
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
