package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    protected Vector direction;

    public DirectionalLight(Color c, Vector d) {
        this.direction = d.normalized();
        _intensity = c;
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.get_intensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return direction;
    }
}
