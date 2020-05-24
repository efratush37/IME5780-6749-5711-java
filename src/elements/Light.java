package elements;

import primitives.Color;
/**
 * Elements.light abstract class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this is an abstract class represents the light on a geometry
 */
abstract class Light {
    //field
    protected Color _intensity;

    /**
     * constructor for the light
     * @param intensity the intensity of the light
     */
    public Light(Color intensity) {
        this._intensity = intensity;
    }

    /**
     * get method for the intensity field
     * @return the intensity of the light
     */
    public Color get_intensity() {
        return _intensity;
    }
}
