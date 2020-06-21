package elements;

import primitives.Color;

/**
 * Elements.AmbientLight class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this class is a kind of light' the ambient light that only have the intensity
 */
public class AmbientLight extends Light {
    /**
     * constructor for the ambient light
     * @param IA the color of the ambient light
     * @param KA the coefficient of the light
     */
    public AmbientLight(Color IA, double KA){
       super(IA.scale(KA));
    }
}
