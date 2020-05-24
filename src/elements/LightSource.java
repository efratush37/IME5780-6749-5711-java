package elements;

import primitives.*;

/**
 * this interface represent the source of the light
 */
public interface LightSource {
    /**
     * this method calculate the intensity of the light in the intersected point
     * @param p the intersected point
     * @return the intensity of the light in this point
     */
    public Color getIntensity(Point3D p);

    /**
     * this method calculate the vector from the light source to the intersected point
     * @param p the intersected point
     * @return the vector from the light source to the point
     */
    public Vector getL(Point3D p);

}
