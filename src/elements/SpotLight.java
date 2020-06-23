package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * Elements.SpotLight class
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this class repredent the source of light as a spot light
 */
public class SpotLight extends PointLight {
    //field
    protected Vector direction; //the direction of the spot light

    /**
     * @param color the color of the light source
     * @param d     the vector from the source to the point
     * @param p     the position point of the source
     * @param kc    factor of  attenuation because of the distance
     * @param kl    factor of  attenuation because of the distance
     * @param kq    factor of  attenuation because of the distance
     */
    public SpotLight(Color color, Point3D p, Vector d, double kc, double kl, double kq) {
        super(color, p, kc, kl, kq);
        this.direction = d.normalized();
    }

    public SpotLight(Color color, Point3D p, Vector d, double kc, double kl, double kq, double radius) {
        this(color, p, d, kc, kl, kq);
        this.radius = radius;
    }

    /**
     * this method calculate the intensity of the light in the intersected point
     *
     * @param p the intersected point
     * @return the intensity of the light in this point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double x = direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(max(0, x));
    }
}
