package unittests.miniProject2;


import MiniProject2.*;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for MiniProject1 shadows improvement
 *
 * @author Rivka Zizovi 207265711 and Efrat Anconina 322796749
 */
public class miniProject2 {
    @Test
    public void Test1() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(600);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));

        Plane p = new Plane(
                new Material(0.5, 0.8, 120, 0, 0),
                new Color(153, 31, 40),
                new Point3D(0, 800, 0),
                new Vector(0, 1, 0));
        Sphere yellow = new Sphere(//yellow
                new Color(100, 200, 0),
                new Material(0.5, 0.8, 120, 0, 1),
                1000,
                new Point3D(1400, -200, 5990));
        Sphere pink = new Sphere(//pink
                new Color(200, 0, 200),
                new Material(0.5, 0.8, 120, 0.4, 0.8),
                400,
                new Point3D(-700, -200, 2000));
        Sphere purple = new Sphere(//purple
                new Color(50, 0, 400),
                new Material(0.5, 0.8, 120),
                100,
                new Point3D(-350, 300, -300));
        Sphere green = new Sphere(//green
                new Color(50, 200, 50),
                new Material(0.5, 0.8, 120),
                70,
                new Point3D(500, 500, -300));
        Sphere white = new Sphere(//white
                new Color(200, 200, 200),
                new Material(0.5, 0.8, 120),
                300,
                new Point3D(200, -200, 4000));
        Sphere blue = new Sphere(//blue
                new Color(50, 200, 300),
                new Material(0.5, 0.8, 120),
                150,
                new Point3D(-500, 500, -300));

        scene.addGeometries(p, yellow, white, purple, blue, pink, green);
        /*Geometries A= new Geometries(yellow, white);
        Geometries B= new Geometries(purple, blue);
        Geometries C= new Geometries(p,A, B, pink, green);

        scene.addGeometries(C);*/

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

        ImageWriter imageWriter = new ImageWriter("mini2Test1", 500, 500, 2000, 2000);
        Render render = new Render(imageWriter, scene);
        //render.setNumOfRays(50);
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void Test2() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -2000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(600);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.3));

        Plane p = new Plane(
                new Material(0.5, 0.8, 120, 0, 0),
                new Color(123, 108, 89),
                new Point3D(0, 680, 0),
                new Vector(0, 1, 0));

        List<Point> points = new ArrayList<Point>();
        points.add(new Point(p));

        for(int i=0, distance=-650; i<14; i++) {//first line
            Sphere Ai = new Sphere(//blue
                    new Color(500, 0, 0),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (i * 100), 650, -300));
            points.add(new Point(Ai));
        }

        for(int j=0, distance= -650; j<14; j++){

                Sphere Aj = new Sphere(//blue
                        new Color(255, 128, 0),
                        new Material(0.5, 0.8, 120),
                        35,
                        new Point3D(distance + (j * 100), 550, -300));
                points.add(new Point(Aj));
        }
        for(int j=0, distance= -650; j<14; j++){

            Sphere Aj = new Sphere(//blue
                    new Color(0, 100, 0),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (j * 100), 450, -300));
            points.add(new Point(Aj));
        }
        for(int j=0, distance= -650; j<14; j++){

            Sphere Aj = new Sphere(//blue
                    new Color(0, 0, 500),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (j * 100), 350, -300));
            points.add(new Point(Aj));
        }
        for(int j=0, distance= -650; j<14; j++){

            Sphere Aj = new Sphere(//blue
                    new Color(105, 105, 105),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (j * 100), 250, -300));
            points.add(new Point(Aj));
        }
        for(int j=0, distance= -650; j<14; j++){

            Sphere Aj = new Sphere(//blue
                    new Color(30, 30, 30),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (j * 100), 150, -300));
            points.add(new Point(Aj));
        }
        for(int j=0, distance= -650; j<14; j++){

            Sphere Aj = new Sphere(//blue
                    new Color(0, 0, 0),
                    new Material(0.5, 0.8, 120),
                    35,
                    new Point3D(distance + (j * 100), 50, -300));
            points.add(new Point(Aj));
        }
        Triangle t=  new Triangle(new Color(java.awt.Color.blue), new Material(0.5, 0.5, 100,1,1),
                new Point3D(-300, -500, -900), new Point3D(0, 0, 100), new Point3D(300, -500, -900));
        points.add(new Point(t));






        KMeans kmeans = new KMeans();
        kmeans.init(points);
        kmeans.calculate();
        List<Cluster> clusters = kmeans.getClusters();
        for (Cluster cluster : clusters) {
            Geometries geos = new Geometries();
            for (Point point : cluster.getPoints()) {
                geos.add(point.getGeometry());
            }
            scene.addGeometries(geos);
        }

        scene.addLights(
                new PointLight(
                        new Color(150, 200, 0),
                        new Point3D(0, -700, 1700),
                        1d,
                        0.000001,
                        0.0000005, 3d),
                new PointLight(
                        new Color(150, 200, 0),
                        new Point3D(-200, -500, -1200),
                        1d,
                        0.000001,
                        0.0000005, 3d)


        );


        ImageWriter imageWriter = new ImageWriter("mini2Test2", 500, 500, 2000, 2000);
        Render render = new Render(imageWriter, scene);
        //render.setNumOfRays(81);
        render.renderImage();
        render.writeToImage();

    }
}
