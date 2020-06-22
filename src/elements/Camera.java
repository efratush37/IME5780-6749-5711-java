package elements;

import geometries.Intersectable;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * camera class
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */

public class Camera {
    //fields
    private static final Random rnd = new Random();//helper field for randoming in the rays creation function
    Point3D p0; //the position point of the camera
    Vector Vup, Vright, Vto; //the vectors represents the direction of observation of the camera

    /**
     * a constructor for the camera element
     *
     * @param p0=  the position point of the camera
     * @param Vto= vector of the camera axises
     * @param Vup= vector of the camera axises
     */
    public Camera(Point3D p0, Vector Vto, Vector Vup) {
        //if the the vectors are not orthogonal, throw exception.
        if (Vup.dotProduct(Vto) != 0)
            throw new IllegalArgumentException("the vectors must be orthogonal");

        this.p0 = new Point3D(p0);
        this.Vto = Vto.normalized();
        this.Vup = Vup.normalized();

        Vright = this.Vto.crossProduct(this.Vup).normalize();
    }

    /**
     * get method for the point of the position of the camera field
     *
     * @return the value of the position field
     */
    public Point3D getp0() {
        return p0;
    }

    /**
     * get method for the 'Right' vector field
     *
     * @return the value of the vector field
     */
    public Vector getVright() {
        return Vright.normalize();
    }

    /**
     * get method for the 'To' vector field
     *
     * @return the value of the vector field
     */
    public Vector getVto() {
        return Vto.normalize();
    }

    /**
     * get method for the 'Up' vector field
     *
     * @return the value of the vector field
     */
    public Vector getVup() {
        return Vup.normalize();
    }

    /**
     * this function create a ray thrown from the camera through a center of a pixel
     *
     * @param nX=             the number of the pixels on the X axis
     * @param nY=             the number of the pixels on the Y axis
     * @param j=              the position of the pixel on the Y anix
     * @param i=              the position of the pixel on the X anix
     * @param screenDistance= the distance of the camera from the view plane
     * @param screenWidth=    the width of the view plane
     * @param screenHeight=   the height of the view plane
     * @return ray thrown from the camera through a center of a pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) {
        if (isZero(screenDistance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc= P0+ d∙Vto
        Point3D pc = p0.add(Vto.scale(screenDistance));
        double ry = screenHeight / (double) nY;
        double rx = screenWidth / (double) nX;
        double xj = ((j - nX / 2d) * rx) + (rx / 2d);
        double yi = ((i - nY / 2d) * ry) + (ry / 2d);
        Point3D pij = pc;
        if (!isZero(xj)) {
            pij = pij.add(Vright.scale(xj));
        }
        if (!isZero(yi)) {
            pij = pij.add(Vup.scale(-yi));
        }
        Vector vij = pij.subtract(p0);
        Ray ray = new Ray(new Point3D(p0), new Vector(vij));
        return ray;
    }

    /**
     * this function calculate list of rays would construct from the intersected point to the light source position area
     * @param MainRay the opposite vector to the ray constructed from the light source to the point intersected
     * @param p the starting point of the ray constructed from the light source to the point intersected
     * @param radiusRange the wanted range for creating rays
     * @param numOfRays the wanted num of rays need to be construct
     * @param vup vector of the camera axises
     * @param vright vector of the camera axises
     * @return list of rays would construct from the intersected point to the light source position area
     */
    public LinkedList<Ray> constructRayBeamThroughPixel(Ray MainRay, Intersectable.GeoPoint p, double radiusRange, int numOfRays, Vector vup, Vector vright) {
        if (isZero(MainRay.get_p0().distance(p.getPoint()))) {
            throw new IllegalArgumentException("distance can't be 0");
        }
        double[] randomNumbers = rnd.doubles(numOfRays * 2, radiusRange * (-1), radiusRange).distinct().toArray();
        LinkedList<Ray> rays = new LinkedList<>();
        rays.add(MainRay);
        double minimum=Math.min(numOfRays, randomNumbers.length-1);
        for (int i=0; i<minimum; i++){
            Point3D pxy=p.getPoint();
            if(randomNumbers[i]!=0 && randomNumbers[i+1]!=0){
                pxy=pxy.add(vup.scale(randomNumbers[i])).add(vright.scale(randomNumbers[i+1]));
                rays.add(new Ray(MainRay.get_p0(), pxy.subtract(MainRay.get_p0()).normalize(),p.getGeometry().getNormal(p.getPoint())));
            }
        }
        return rays;
    }
}

