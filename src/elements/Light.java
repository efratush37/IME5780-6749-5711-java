package elements;

import primitives.Color;

abstract class Light {
    protected Color _intensity;

    public Light(Color intensity) {
        this._intensity = intensity;
    }

    protected Light() {
    }

    public Color get_intensity() {
        return _intensity;
    }
}
