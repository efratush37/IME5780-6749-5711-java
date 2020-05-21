package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

import static java.lang.Math.max;

public class SpotLight extends PointLight {
    protected Vector direction;

    public SpotLight(Color color,Vector d, Point3D p, double kc, double kl, double kq) {
        super(color, p, kc, kl, kq);
        this.direction=d.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double x=direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(max(0,x));
    }

    @Override
    public Vector getL(Point3D p) {
        return super.getL(p);
    }
}
