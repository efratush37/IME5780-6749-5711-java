package primitives;
/**
 * Elements.material class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this class represents the material the geometry is made of
 */
public class Material {
    //fields
    double kD;
    double kS;
    int nShininess;

    /**
     * a constructor to the material
     * @param kd material attenuation coefficient
     * @param ks material attenuation coefficient
     * @param nshininess glitter coefficient of the material
     */
    public Material(double kd, double ks, int nshininess) {
        this.kD = kd;
        this.kS = ks;
        this.nShininess = nshininess;
    }

    /**
     * get method for the Kd field
     * @return the value of material attenuation coefficient Kd
     */
    public double getkD() {
        return kD;
    }

    /**
     * get method for the Ks field
     * @return the value of material attenuation coefficient Ks
     */
    public double getkS() {
        return kS;
    }

    /**
     * get method for the nshininess field
     * @return the value of the glitter coefficient of the material
     */
    public int getnShininess() {
        return nShininess;
    }

}
