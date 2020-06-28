package unittests.miniProject;

import elements.AmbientLight;
import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.*;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

import static org.junit.Assert.*;

/**
 * Unit tests for MiniProject1 shadows improvement
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class miniProject1 {
    @Test
    public void softShadowes() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(600);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));

        scene.addGeometries(
                new Plane(
                        new Material(0.5, 0.8, 120, 0.05,0.1),
                        new Color(153, 31, 40),
                        new Point3D(0, 800, 0),
                        new Vector(0, 1, 0)),

                new Sphere(
                        new Color(100, 200, 0),
                        new Material(0.5, 0.8, 120, 0,1),
                        1000,
                        new Point3D(1100, -200, 5990)),
                new Sphere(
                        new Color(200, 0, 200),
                        new Material(0.5, 0.8, 120,0.4,0.8),
                        400,
                        new Point3D(-700, -200, 2000)),
                new Sphere(
                        new Color(50, 0, 400),
                        new Material(0.5, 0.8, 120),
                        100,
                        new Point3D(0, 300, -300)),
                new Sphere(
                        new Color(50, 200, 50),
                        new Material(0.5, 0.8, 120),
                        70,
                        new Point3D(500, 500, -300)),
                new Sphere(
                        new Color(200, 200, 200),
                        new Material(0.5, 0.8, 120),
                        300,
                        new Point3D(50, -200, 4000)),
                new Sphere(
                        new Color(50, 200, 300),
                        new Material(0.5, 0.8, 120),
                        150,
                        new Point3D(-500, 500, -300))
        );

        scene.addLights(
                new PointLight(
                        new Color(150, 200, 0),
                        new Point3D(0, -1300, 4200),
                        1d,
                        0.000001,
                        0.0000005, 200d),
                new PointLight(
                        new Color(150, 200, 0),
                        new Point3D(-1300, -1000, -2400),
                        1d,
                        0.000001,
                        0.0000005, 200d)
        );

        ImageWriter imageWriter = new ImageWriter("miniProject1", 500, 500, 2000, 2000);
        Render render = new Render(imageWriter, scene);
        //render.setNumOfRays(50);
        render.renderImage();
        render.writeToImage();
    }
}