package com.blue.view.balllayout.coordinate;

public final class Vector {

    private double x;
    private double y;
    private double z;

    public static Vector createFromUI(double x, double y, double z) {
        return new Vector(x, z, y);
    }

    public Vector() {
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getUiX() {
        return x;
    }

    public double getUiY() {
        return z;
    }

    public double getUiZ() {
        return y;
    }

    public SphericalCoordinate toSphericalCoordinate() {
        return SphericalCoordinate.createByCartesianCoordinate(this);
    }

    public SphericalCoordinate toSphericalCoordinate(double radius) {
        return SphericalCoordinate.createByCartesianCoordinate(this, radius);
    }

    @Override
    public String toString() {
        return "Vector[" + x + ", " + y + ", " + z + ']';
    }
}
