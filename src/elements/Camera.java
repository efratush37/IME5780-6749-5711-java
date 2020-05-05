package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Camera {
    Point3D position;

    Vector Vup, Vright, Vto;

    public Point3D getPosition() {
        return position;
    }

    public Vector getVright() {
        return Vright;
    }

    public Vector getVto() {
        return Vto;
    }

    public Vector getVup() {
        return Vup;
    }
    public Camera(Point3D p, Vector up, Vector to) {
        position=p;
        up.normalize();
        to.normalize();
        if (up.dotProduct(to)==0)
        {
            Vector right=new Vector(up.crossProduct(to));
            right.normalize();
            Vup=up;
            Vto=to;
            Vright=right;
        }
        else
            throw new IllegalArgumentException();
    }

    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight){
        return null;
    }

}