package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class represents a light source in a shape  of spotlight
 */
public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * constructor
     *
     * @param color     - the color of the light source
     * @param position  - the point which the light is being emitted from
     */
    public SpotLight(Color color, Point3D position, Vector direction) {
        super(color, position);
        this.direction = direction.normalized();
    }

    /**
     * function calculates the color of the light in a given point in the 3D space
     *
     * @param p - the point which we want to know what the color is in
     * @return the light color in p
     */
    @Override
    public Color getIntensity(Point3D p) {
        Color iL = super.getIntensity(p);
        double mul = Math.max(0, direction.dotProduct(getL(p)));
        return iL.scale(mul);
    }
}
