package com.blue.view.balllayout.coordinate;

import java.util.List;

public final class Rotation {

    public static void rotate(SphericalCoordinate from, SphericalCoordinate to, List<Vector> list) {
        SinCosBase phi1Base = new SinCosBase(-from.getPhi());
        SinCosBase thetaBase = new SinCosBase(to.getTheta() - from.getTheta());
        SinCosBase phi2Base = new SinCosBase(to.getPhi());

        for (Vector vector : list) {
            rotateByZ(phi1Base, vector);
            rotateByY(thetaBase, vector);
            rotateByZ(phi2Base, vector);
        }
    }

    public static void rotateByY(double radian, Vector vector) {
        rotateByY(new SinCosBase(radian), vector);
    }

    private static void rotateByY(SinCosBase base, Vector vector) {
        double x = vector.getX();
        double z = vector.getZ();
        vector.setX(z * base.sin + x * base.cos);
        vector.setZ(z * base.cos - x * base.sin);
    }

    public static void rotateByZ(double radian, Vector vector) {
        rotateByZ(new SinCosBase(radian), vector);
    }

    private static void rotateByZ(SinCosBase base, Vector vector) {
        double x = vector.getX();
        double y = vector.getY();
        vector.setX(x * base.cos - y * base.sin);
        vector.setY(x * base.sin + y * base.cos);
    }

    private static class SinCosBase {

        final double radian;
        final double sin;
        final double cos;

        SinCosBase(double radian) {
            this.radian = radian;
            this.sin = Math.sin(radian);
            this.cos = Math.cos(radian);
        }
    }
}
