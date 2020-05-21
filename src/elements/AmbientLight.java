package elements;

import primitives.Color;

public class AmbientLight extends Light {

    public AmbientLight(Color IA, double KA){
       super(IA.scale(KA));
    }


}
