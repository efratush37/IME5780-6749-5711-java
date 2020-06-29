package renderer;

import elements.DirectionalLight;
import elements.LightSource;
import elements.PointLight;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable.GeoPoint;
import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static elements.Camera.constructRayBeamThroughPixel;
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
    //private static final double DELTA = 0.1; //minimal size to add to the shadow rays in order to avoid from intersected with the geometry
    private static final int MAX_CALC_COLOR_LEVEL = 10; //the maximum level can be afford for stopping the recursion
    private static final double MIN_CALC_COLOR_K = 0.001; //the minimum level can be afford for stopping the recursion
    private int NUM_OF_RAYS = 1; //number of rays for soft shadow feature


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
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        renderImage(1);
    }

    /**
     * this function create the image pixel after pixel
     * (refactoring- now when we create the image, were going on a list of geo points)
     * (refactoring- now we use the findCLosestIntersection function instead of closest point)
     *
     * @param numRays numRays
     */
    public void renderImage(int numRays) {
        Camera camera = scene.get_camera();
        java.awt.Color background = scene.get_background().getColor();
        Double distance = scene.get_distance();
        Intersectable listGeo = scene.get_geometries();

        int nX = IMwr.getNx();
        int nY = IMwr.getNy();
        Double width = IMwr.getWidth();
        Double height = IMwr.getHeight();

        final Pixel thePixel = new Pixel(nY, nX);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height);
                    List<GeoPoint> intersections= listGeo.findIntsersections(ray);
                    if(intersections== null)
                        IMwr.writePixel(pixel.col, pixel.row, background);
                    else {
                        GeoPoint closestPoint = getClosestPoint(intersections);
                        IMwr.writePixel(pixel.col, pixel.row, calcColor(closestPoint,ray).getColor());
                    }
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n");
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
        if (level == 1) {
            return Color.BLACK;
        }

        Color color = p.getGeometry().getEmission();
        Geometry geometry = p.getGeometry();

        Point3D point = p.getPoint();
        Vector v = point.subtract(scene.get_camera().getp0()).normalize();
        Vector n = geometry.getNormal(point).normalize();
        double kd = geometry.getMaterial().getkD();
        double ks = geometry.getMaterial().getkS();
        int shine = geometry.getMaterial().getnShininess();

        List<Ray> beam;
        List<LightSource> allLights = scene.get_lights();
        //regarding the Phong model, calculating the color by the influence of all the light sources on the point
        if (allLights != null) {
            for (LightSource lightSource : allLights) {
                Vector l = lightSource.getL(point).normalize();

                double ln = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));
                beam = constructRayBeamThroughPixel(lightSource, l, n, p);
                double ktr = transparency(lightSource, p, beam);

                if (ln * nv > 0 && ktr * k > MIN_CALC_COLOR_K) {
                    Color ip = lightSource.getIntensity(p.getPoint()).scale(ktr);
                    color = color.add(
                            calcDiffusive(kd, ln, ip),
                            calcSpecular(ks, l, n, ln, v, shine, ip));
                }
            }
        }

        //calculating the reflection influence
        double kr = p.getGeometry().getMaterial().get_kR();
        double kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(point, ray, n);
            GeoPoint reflectedpoint = findCLosestIntersection(reflectedRay);
            if (reflectedpoint != null) {
                color = color.add(calcColor(reflectedpoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }

        //calculating the refraction influence
        double kt = p.getGeometry().getMaterial().get_kT();
        double kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(point, ray, n);
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
     * this function change the hard coded number of rays to construct to a given value
     * @param numOfRays the wanted number of rays to construct
     */
    public void setNumOfRays(int numOfRays) {
        this.NUM_OF_RAYS = numOfRays;
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
        Vector v, r;
        v = inRay.getDir();
        double vn = v.dotProduct(n);
        if (vn == 0) {
            return null;
        }
        r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
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

    /**
     * this function calculate the influence of shadows
     * refactoring so that the function will work with a beam of rays or one way as wish
     *
     * @param light    the light source that illuminates the geometry
     * @param geopoint the intersected point
     * @param beam List Ray beam
     * @return the level of the transparent influence on the objects
     */
    private double transparency(LightSource light, GeoPoint geopoint, List<Ray> beam) {
        double result = 0.0, ktr;
        double lightDistance = light.getDistance(geopoint.getPoint());
        for (Ray lightRay : beam) {
            List<GeoPoint> intersections = scene.get_geometries().findIntsersections(lightRay);
            if (intersections == null) {
                result += 1.0;
            } else {
                ktr = 1.0;
                for (GeoPoint gp : intersections) {
                    if (alignZero(gp.getPoint().distance(geopoint.getPoint()) - lightDistance) <= 0) {
                        ktr *= gp.getGeometry().getMaterial().get_kT();
                        if (ktr < MIN_CALC_COLOR_K) {
                            ktr = 0.0;
                            break;
                        }
                    }
                }
                result += ktr;
            }
        }
        double sum = result / beam.size(); //
        return sum;
    }

    /**
     * this function create a beam of rays
     * @param light the light source
     * @param l the vector from the light source to the intersected point
     * @param n the normal to the intersected point
     * @param geopoint the intersected point
     * @return beam of shadow rays
     */
    public List<Ray> constructRayBeamThroughPixel(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector centralLightDirection = l.scale(-1).normalize();
        Point3D point = geopoint.getPoint();
        Ray centralRay = new Ray(point, centralLightDirection, n);
        List<Ray> beam = new LinkedList<Ray>();
        beam.add(centralRay);

        if(!(light instanceof PointLight)) {
            return beam;
        }

        PointLight pointLight = (PointLight) light;
        if (this.NUM_OF_RAYS > 1) {   // if soft shadows is enabled
            Vector normalToCentralRay = new Vector(-centralLightDirection.getHead().getC2().get(), centralLightDirection.getHead().getC1().get(), 0).normalize();
            Vector cross = normalToCentralRay.crossProduct(centralLightDirection).normalize();

            double radius = pointLight.getRadius();
            if (radius == 0d) {
                return beam;
            }

            Point3D randomPoint, position = pointLight.getPosition();
            Ray randomRay;
            Vector randomDirection;
            double rnd1, rnd2, upper = radius, lower = -radius; // upper and lower range to rand numbers in order to get random points in the circle

            for (int i = 0; i < this.NUM_OF_RAYS; i++) {
                do {
                    rnd1 = Math.random() *(upper -lower + 1) + lower;
                    rnd2 = Math.random() *(upper -lower + 1) + lower;
                } while (Math.abs(rnd1 + rnd2) > radius); // check that we don't exceed from the circle
                randomPoint = position.add(normalToCentralRay.scale(rnd1).add(cross.scale(rnd2))); // get the new point by adding the 2 vectors scaled by random numbers
                randomDirection = randomPoint.subtract(point).normalize();  // vector from the point to the new random point
                randomRay = new Ray(point, randomDirection, n); // the new ray shifted by delta from the shape
                beam.add(randomRay);    // add the ray to the list
            }
        }
        return beam;
    }

    //code for multyThreading
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.
     * There is a main follow up object and several secondary objects - one in each thread.
     * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
     *
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         *  Default constructor for secondary Pixel objects
         */
        public Pixel() {}

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }
    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }
}

