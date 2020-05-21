package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements  LightSource {
    protected Point3D position;
    protected double kC;
    protected double kL;
    protected double kQ;
public PointLight(Color color, Point3D p, double kc, double kl, double kq){
    _intensity=color;
    this.position=p;
    this.kC=kc;
    this.kL=kl;
    this.kQ=kq;
}


    @Override
    public Color getIntensity(Point3D p) {
        double d_squared = p.distanceSquared(position);
        double d = p.distance(position);
        return (_intensity.reduce(kC + kL * d + kQ * d_squared));
    }

    @Override
    public Vector getL(Point3D p) {
        if (p.equals(position)) {
            return null;
        }
        return (p.subtract(position).normalize());
    }
}
