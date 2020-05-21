package primitives;

public class Material {
    double kD;
    double kS;
    int nShininess;
    public  Material(double kd,double ks, int nshininess){
        this.kD=kd;
        this.kS=ks;
        this.nShininess=nshininess;
    }

    public double getkD() {
        return kD;
    }

    public double getkS() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }

}
