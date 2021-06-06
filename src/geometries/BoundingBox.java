package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;

// TODO:
//  read all code, understand it and also the documentation


/**
 * class represents axis-aligned bounding box, it used for checking if ray is in the area of a geometry
 * by checking if the ray direction come with intersection in the bounding box of the geometry it means for us that
 * the calculation of all the intersections of the same ray should be taken into account.
 */
public class BoundingBox {

    private double _xMin = Double.POSITIVE_INFINITY;
    private double _yMin = Double.POSITIVE_INFINITY;
    private double _zMin = Double.POSITIVE_INFINITY;

    private double _xMax = Double.NEGATIVE_INFINITY;
    private double _yMax = Double.NEGATIVE_INFINITY;
    private double _zMax = Double.NEGATIVE_INFINITY;

    /**
     * default constructor for class BoundingBox
     */
    public BoundingBox() {
    }


    /**
     * Constructor of class BoundingBox, with 6 inputs, minimum value and maximum value inputs per axis
     *
     * @param xMin minimum value in x axis
     * @param xMax maximum value in x axis
     * @param yMin minimum value in y axis
     * @param yMax maximum value in y axis
     * @param zMax maximum value in z axis
     * @param zMin minimum value in z axis
     */
    public BoundingBox(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax) {
        this._xMin = xMin;
        this._xMax = xMax;
        this._yMin = yMin;
        this._yMax = yMax;
        this._zMin = zMin;
        this._zMax = zMax;
    }

    /**
     * Setter for new values for class BoundingBox, with 6 inputs, minimum value and maximum value inputs per axis
     *
     * @param x1 minimum/maximum value in x axis
     * @param x2 minimum/maximum value in x axis
     * @param y1 minimum/maximum value in y axis
     * @param y2 minimum/maximum value in y axis
     * @param z1 minimum/maximum value in z axis
     * @param z2 minimum/maximum value in z axis
     */
    public void setBoundingBox(double x1, double x2, double y1, double y2, double z1, double z2) {
        this._xMin = Math.min(x1, x2);
        this._xMax = Math.max(x1, x2);
        this._yMin = Math.min(y1, y2);
        this._yMax = Math.max(y1, y2);
        this._zMin = Math.min(z1, z2);
        this._zMax = Math.max(z1, z2);
    }

    public double getMinX() {
        return _xMin;
    }

    public double getMaxX() {
        return _xMax;
    }

    public double getMinY() {
        return _yMin;
    }

    public double getMaxY() {
        return _yMax;
    }

    public double getMinZ() {
        return _zMin;
    }

    public double getMaxZ() {
        return _zMax;
    }

    /**
     * Function which check if a ray intersects with the bounding region
     *
     * @param ray the ray to check foe intersection
     * @return boolean result, if it does, or not
     */
    public boolean intersectBV(Ray ray) {
        Point3D p0 = ray.getP0();
        Point3D d = ray.getDir().getHead();
        double dX = d.getX();
        double dY = d.getY();
        double dZ = d.getZ();
        double oX = p0.getX();
        double oY = p0.getY();
        double oZ = p0.getZ();
        double t_Min, t_Max, t_yMin, t_yMax, t_zMin, t_zMax;

        // for all 3 axes:
        // calculate the intersection distance t0 and t1
        // (t_Min represent the min and t_Max represent the max)
        // 1. when the values for t are negative, the box is behind the ray.
        // 2. if the ray is parallel to an axis it won't intersect with the bounding volume plane for this axis.
        // 3. we first find where the ray intersects the planes defined by each face of the cube,
        // after that, we find the ray's first and second intersections with the planes.
        if (dX > 0) {
            t_Max = (_xMax - oX) / dX;
            if (t_Max <= 0) {
                return false; // if value for t_Max is negative, the box is behind the ray.
            }
            t_Min = (_xMin - oX) / dX; // oX + tDx = b0x => t0X = (b0x - oX) / dX
        } else if (dX < 0) {
            t_Max = (_xMin - oX) / dX;
            if (t_Max <= 0) {
                return false; // if value for t_Max is negative, the box is behind the ray.
            }
            t_Min = (_xMax - oX) / dX;
        } else { // preventing parallel to the x axis
            t_Max = Double.POSITIVE_INFINITY;
            t_Min = Double.NEGATIVE_INFINITY;
        }

        if (dY > 0) {
            t_yMax = (_yMax - oY) / dY;
            if (t_yMax <= 0) {
                return false; // if value for t_yMax is negative, the box is behind the ray.
            }
            t_yMin = (_yMin - oY) / dY;
        } else if (dY < 0) {
            t_yMax = (_yMin - oY) / dY;
            if (t_yMax <= 0) {
                return false; // if value for t_yMax is negative, the box is behind the ray.
            }
            t_yMin = (_yMax - oY) / dY;
        } else { // preventing parallel to the y axis
            t_yMax = Double.POSITIVE_INFINITY;
            t_yMin = Double.NEGATIVE_INFINITY;
        }
        // cases where the ray misses the cube
        // the ray misses the box when t0x is greater than t1y and when t0y is greater than  t1x
        if ((t_Min > t_yMax) || (t_yMin > t_Max)) {
            return false;
        }

        // we find which one of these two points lie on the cube by comparing their values:
        // we simply need to chose the point which value for t is the greatest.
        if (t_yMin > t_Min)
            t_Min = t_yMin;
        // we find the second point where the ray intersects the box
        // we simply need to chose the point which value for t is the smallest
        if (t_yMax < t_Max)
            t_Max = t_yMax;

        if (dZ > 0) {
            t_zMax = (_zMax - oZ) / dZ;
            if (t_zMax <= 0) {
                return false; // if value for t_zMax is negative, the box is behind the ray.
            }
            t_zMin = (_zMin - oZ) / dZ;
        } else if (dZ < 0) {
            t_zMax = (_zMin - oZ) / dZ;
            if (t_zMax <= 0) {
                return false; // if value for t_zMax is negative, the box is behind the ray.
            }
            t_zMin = (_zMax - oZ) / dZ;
        } else { // preventing parallel to the z axis
            t_zMax = Double.POSITIVE_INFINITY;
            t_zMin = Double.NEGATIVE_INFINITY;
        }

        // cases where the ray misses the cube
        // the ray misses the box when t0 is greater than t1z and when t0z is greater than  t1
        return (!(t_Min > t_zMax)) && (!(t_zMin > t_Max));

        // finding the real location of the intersection doesn't necessary for our boolean function
        //if (t_zMin > t_Min)
        //    t_Min = t_zMin;
        //if (t_zMax < t_Max)
        //    t_Max = t_zMax;
    }

    /**
     * calculate size of BoundingBox
     *
     * @return the size of bounding box
     */
    public double size() {
        return (_xMax - _xMin) * (_yMax - _yMin) * (_zMax - _zMin);
    }

    /**
     * Gets the distance between two bounding boxes in x axis
     *
     * @param other the second bounding box
     * @return value of distance between the closest points of two boxes in the current axis
     */
    public double getDistanceX(BoundingBox other) {
        return Util.alignZero(Math.max(other._xMin - this._xMax, this._xMin - other._xMax));
    }

    /**
     * Gets the distance between two bounding boxes in y axis
     *
     * @param other the second bounding box
     * @return value of distance between the closest points of two boxes in the current axis
     */
    public double getDistanceY(BoundingBox other) {
        return Util.alignZero(Math.max(other._yMin - this._yMax, this._yMin - other._yMax));
    }

    /**
     * Gets the distance between two bounding boxes in z axis
     *
     * @param other the second bounding box
     * @return value of distance between the closest points of two boxes in the current axis
     */
    public double getDistanceZ(BoundingBox other) {
        return Util.alignZero(Math.max(other._zMin - this._zMax, this._zMin - other._zMax));
    }

    /**
     * Gets the maximum value of distance between two bounding boxes form the three axes
     *
     * @param other the second bounding box
     * @return maximum value of distance from the three axes, between the closest points of two boxes for each axis
     */
    public double getMaxDistance(BoundingBox other) {
        // if the max distance is <= 0 then the bounding boxes intersects
        return Math.max(Math.max(getDistanceX(other), getDistanceY(other)), getDistanceZ(other));
    }

    /**
     * creates string which identify the values of the bounding region
     *
     * @return a string of minimum Vector and maximum Vector
     */
    @Override
    public String toString() {
        return "minimum : (" + _xMin + " " + _yMin + " " + _zMin + ") \n" +
                "maximum : (" + _xMax + " " + _yMax + " " + _zMax + ")";
    }


    /**
     * Bounding Box method equals implementation
     *
     * @param obj to compare with
     * @return boolean result (same values of the bounding boxes)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BoundingBox)) return false;
        BoundingBox other = (BoundingBox) obj;
        // Purpose: Make sure that all vertices of bounding boxes are equal:
        return Util.isZero(_xMin - other._xMin) &&
                Util.isZero(_yMin - other._yMin) &&
                Util.isZero(_zMin - other._zMin) &&
                Util.isZero(_xMax - other._xMax) &&
                Util.isZero(_yMax - other._yMax) &&
                Util.isZero(_zMax - other._zMax);
    }

}


