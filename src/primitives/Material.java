package primitives;
/**
 * Elements.material class
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 * this class represents the material the geometry is made of
 */
public class Material {
    //fields
    double kD; //material attenuation coefficient
    double kS; //material attenuation coefficient
    int nShininess; //glitter coefficient of the material
    double _kT;//transparency coefficient
    double _kR;//reflection coefficient

    //kt=1 means perfect transparency
    //kr=1 means perfect

    /**
     * a constructor to the material
     * @param kd material attenuation coefficient
     * @param ks material attenuation coefficient
     * @param nshininess glitter coefficient of the material
     *  set the kT and the kR fields with default values using the other constructor
     */
    public Material(double kd, double ks, int nshininess) {
        this(kd, ks,nshininess, 0,0);
    }

    /**
     * constructor with five arguments for all the coefficients of the material
     * @param kt transparency coefficient
     * @param kr reflection coefficient
     * @param kd material attenuation coefficient
     * @param ks material attenuation coefficient
     * @param nshininess glitter coefficient of the material
     */
    public Material(double kd, double ks, int nshininess, double kt, double kr){
        this.kD = kd;
        this.kS = ks;
        this.nShininess = nshininess;
        this._kT=kt;
        this._kR=kr;
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

    /**
     * get method for the reflaction coefficient field
     * @return the value of the reflaction coefficient of the material
     */

    public double get_kR() { return _kR; }

    /**
     * get method for the transparency coefficient field
     * @return the value of the transparency coefficient of the material
     */
    public double get_kT() {
        return _kT;
    }
}


