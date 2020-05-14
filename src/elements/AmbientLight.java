package elements;

import primitives.Color;

public class AmbientLight {
    Color _intensity ;
    public  AmbientLight(Color IA, double KA){
        _intensity=IA.scale(KA);
    }

    public Color get_intensity() {
        return _intensity;
    }

}
