package renderer;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;
import org.junit.jupiter.api.Test;


/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

    private Scene scene2 = new Scene("Test scene")
            .setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

    private Camera camera1 = new Camera(
            new Point3D(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)
    ) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);

    private Camera camera2 = new Camera(
            new Point3D(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0)
    ) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150),
            new Point3D(150, -150, -150),
            new Point3D(75, 75, -150)
    );

    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150),
            new Point3D(-70, 70, -50),
            new Point3D(75, 75, -150)
    );

    private static Geometry sphere = new Sphere(
            new Point3D(0, 0, -50),
            50) //
            .setEmission(Color.BLUE) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);

        scene1.lights.add(
                new DirectionalLight(
                        new Color(500, 300, 0),
                        new Vector(1, 1, -1)
                )
        );

        ImageWriter imageWriter = new ImageWriter("sphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene1) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);

        scene1.lights.add(new PointLight(
                new Color(500, 300, 0),
                new Point3D(-50, -50, 50),
                1, 0.00001, 0.000001)
        );

        ImageWriter imageWriter = new ImageWriter("spherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene1) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(
                new Color(500, 300, 0),
                new Point3D(-50, -50, 50),
                1, 0.00001, 0.00000001,
                new Vector(1, 1, -2))
        );

        ImageWriter imageWriter = new ImageWriter("sphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene1) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("trianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene2) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new PointLight(
                new Color(500, 250, 250),
                new Point3D(10, -10, -130),
                1, 0.0005, 0.0005));

        ImageWriter imageWriter = new ImageWriter("trianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene2) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(
                new Color(500, 250, 250),
                new Point3D(10, -10, -130),
                1, 0.0001, 0.000005,
                new Vector(-2, -2, -1)));

        ImageWriter imageWriter = new ImageWriter("trianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene2) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a narrow spot light
     */
    @Test
    public void sphereSpotSharp() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(
                new Color(500, 300, 0),
                new Point3D(-50, -50, 50),
                1, 0.000005, 0.00000025,
                new Vector(1, 1, -2)));

        ImageWriter imageWriter = new ImageWriter("sphereSpotSharp", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene1) //
                .setCamera(camera1) //
                .setRayTracer(new RayTracerBasic(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a narrow spot light
     */
    @Test
    public void trianglesSpotSharp() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(
                new Color(800, 400, 400),
                new Point3D(10, -10, -130),
                1, 0.00005, 0.0000025,
                new Vector(-2, -2, -1)));

        ImageWriter imageWriter = new ImageWriter("trianglesSpotSharp", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene2) //
                .setCamera(camera2) //
                .setRayTracer(new RayTracerBasic(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    /**
     * Testing creation of lighted images
     */
    public void bigImageTest() {

        Camera BigTestCamera = new Camera(
                new Point3D(-5000, 0, 0),
                new Vector(1, 0, 0),
                new Vector(0, 1, 0)
        ) //
                .setViewPlaneSize(150, 150) //
                .setDistance(5300);

        Scene scene = new Scene("Test scene")
                .setAmbientLight(new AmbientLight(Color.WHITE, 0.15));

        scene.lights.add(
                new PointLight(
                        new Color(200, 200, 200),
                        new Point3D(-30, 50, 60),
                        1, 0.00005, 0.00003));

        scene.lights.add(new PointLight(
                new Color(00, 00, 200),
                new Point3D(80, 80, 120),
                1, 0.00005, 0.00003));

        scene.lights.add(new SpotLight(
                new Color(200, 00, 000),
                new Point3D(30, 0, -60),
                1, 0.00005, 0.00003,
                new Vector(1, 0, 2)));

        scene.lights.add(new DirectionalLight(
                new Color(10, 100, 10),
                new Vector(new Point3D(0, 0, -0.5))));

        // Remove this loop and all it's contents if you wish the rendering to take less than 30 minutes!!
        for (double x = -50; x <= 50; x += 5) {

            for (double y = -(50 - Math.abs(x)); y <= 50 - Math.abs(x); y += 5) {

                double z = Math.sqrt(2500 - x * x - y * y);

                Sphere s = new Sphere(new Point3D(x + 60, y, z), 5);

                s.setEmission(new Color(
                        (int) Math.abs(x + y + z) % 25,
                        (int) Math.abs(x + y + z + 10) % 25,
                        (int) Math.abs(x + y + z + 20) % 25
                ));

                s.setMaterial(new Material().setKd(1d).setKs(0).setShininess(99));

                scene.geometries.add(s);

                if (z != 0) {

                    s = new Sphere(new Point3D(x + 60, y, -z), 5);

                    s.setEmission(new Color(
                            (int) Math.abs(x + y + z) % 25,
                            (int) Math.abs(x + y + z + 10) % 25,
                            (int) Math.abs(x + y + z + 20) % 25)
                    );

                    s.setMaterial(new Material().setKd(1d).setKs(0.1d).setShininess(99));

                    scene.geometries.add(s);
                }
            }
        }


        scene.geometries.add(
                new Sphere(new Point3D(80, 0, 120), 80)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setKd(0.05d).setKs(1d).setShininess(15)));

        scene.geometries.add(
                new Sphere(new Point3D(60, 0, 0), 30)
                        .setEmission(new Color(75, 0, 25))
                        .setMaterial(new Material().setKd(0.2d).setKs(1d).setShininess(15)));

        scene.geometries.add(
                new Sphere(new Point3D(60, -900, 0), 800)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setKd(0.1d).setKs(1d).setShininess(15)));

        scene.geometries.add(
                new Sphere(new Point3D(60, 900, 0), 800)
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material().setKd(0.1d).setKs(1d).setShininess(15)));

        scene.geometries.add(new Plane(
                new Point3D(250, -200, -150),
                new Point3D(250, 200, -150),
                new Point3D(250, -200, 200)).
                setEmission(new Color(15, 15, 15)).
                setMaterial(new Material().setKd(0.7d).setKs(1d).setShininess(99)));

        scene.geometries.add(new Triangle(
                new Point3D(-5000, -200, -70),
                new Point3D(150, 200, -70),
                new Point3D(150, -200, -70))
                .setEmission(new Color(7, 7, 7))
                .setMaterial(new Material().setKd(0.1d).setKs(1d).setShininess(99)));

        scene.geometries.add(new Triangle(
                new Point3D(-5000, 200, -70),
                new Point3D(150, 200, -70),
                new Point3D(-500, -200, -70))
                .setEmission(new Color(7, 7, 7))
                .setMaterial(new Material().setKd(0.1d).setKs(1d).setShininess(99)));

        ImageWriter imageWriter = new ImageWriter("The great image", 1000, 1000);

        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setScene(scene) //
                .setCamera(BigTestCamera) //
                .setRayTracer(new RayTracerBasic(scene));
        render.renderImage();
        render.writeToImage();

    }

}
