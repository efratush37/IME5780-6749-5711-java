package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
/**
 * Elements.DirectionalLight class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this class repredent the source of light as a directional light
 */
public class DirectionalLight extends Light implements LightSource {
    //field
    protected Vector direction; //the direction of the light

    /**
     * constructor to the directional light
     * @param c the color of the light source
     * @param d the vector from the source to the point
     */
    public DirectionalLight(Color c, Vector d) {
        super(c);
        this.direction = d.normalized();
    }

    /**
     * this method calculate the intensity of the light in the intersected point
     * @param p the intersected point
     * @return the intensity of the light in this point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.get_intensity();
    }

    /**
     * this method calculate the vector from the light source to the intersected point
     * @param p the intersected point
     * @return the vector from the source to the point
     */
    @Override
    public Vector getL(Point3D p) {
        return direction;
    }

    /**
     * this method calculate the distance from the light source to the intersected point
     * @param point the intersected point
     * @return the distance from the light source to the intersected point
     */
    public double getDistance(Point3D point){
        return Double.POSITIVE_INFINITY;
    }
}
