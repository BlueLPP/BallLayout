package com.blue.view.balllayout.coordinate;

public final class SphericalCoordinate {

    private double radius;
    private double theta;
    private double phi;

    public static SphericalCoordinate createByCartesianCoordinate(Vector vector) {
        return createByCartesianCoordinate(vector.getX(), vector.getY(), vector.getZ());
    }

    public static SphericalCoordinate createByCartesianCoordinate(Vector vector, double radius) {
        return createByCartesianCoordinate(vector.getX(), vector.getY(), vector.getZ(), radius);
    }

    public static SphericalCoordinate createByCartesianCoordinate(double x, double y, double z) {
        double radius = Math.sqrt(x * x + y * y + z * z);
        return new SphericalCoordinate(radius, Math.acos(z / radius), atan(x, y));
    }

    public static SphericalCoordinate createByCartesianCoordinate(double x, double y, double z, double radius) {
        return new SphericalCoordinate(radius, Math.acos(z / radius), atan(x, y));
    }

    private static double atan(double x, double y) {
        if (x == 0D) {
            if (y == 0D) {
                return 0D;
            } else {
                return y > 0D ? Math.PI / 2 : Math.PI * 3 / 2;
            }
        } else {
            if (x > 0D) {
                return Math.atan(y / x);
            } else {
                return Math.PI + Math.atan(y / x);
            }
        }
    }

    public SphericalCoordinate(double theta, double phi) {
        this.radius = 1D;
        this.theta = theta;
        this.phi = phi;
    }

    public SphericalCoordinate(double radius, double theta, double phi) {
        this.radius = radius;
        this.theta = theta;
        this.phi = phi;
    }

    public void set(double radius, double theta, double phi) {
        this.radius = radius;
        this.theta = theta;
        this.phi = phi;
    }

    public void set(SphericalCoordinate sc) {
        this.radius = sc.radius;
        this.theta = sc.theta;
        this.phi = sc.phi;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getTheta() {
        return theta;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getPhi() {
        return phi;
    }

    public Vector toVector() {
        return copyToVector(new Vector());
    }

    public Vector copyToVector(Vector vector) {
        double sinTheta = Math.sin(theta);
        return vector.set(
                radius * sinTheta * Math.cos(phi),
                radius * sinTheta * Math.sin(phi),
                radius * Math.cos(theta));
    }

    public String toDegreesString() {
        return "SphericalCoordinate{" +
                "radius=" + radius +
                ", theta=" + Math.toDegrees(theta) +
                ", phi=" + Math.toDegrees(phi) +
                '}';
    }

    @Override
    public String toString() {
        return "SphericalCoordinate{" +
                "radius=" + radius +
                ", theta=" + theta +
                ", phi=" + phi +
                '}';
    }
}
