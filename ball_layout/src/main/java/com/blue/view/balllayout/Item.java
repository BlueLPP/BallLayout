package com.blue.view.balllayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class Item<T> {

    private final double theta;
    private final double phi;
    private final T data;

    public Item(double theta, double phi, T data) {
        this.theta = theta;
        this.phi = phi;
        this.data = data;
    }

    public double getTheta() {
        return theta;
    }

    public double getPhi() {
        return phi;
    }

    public T getData() {
        return data;
    }

    public static <T> List<Item<T>> newRandomLocation(T... data) {
        return newRandomLocation(Arrays.asList(data));
    }

    public static <T> List<Item<T>> newRandomLocation(List<T> data) {
        List<Item<T>> items = new ArrayList<>(data.size());
        Random random = new Random();
        for (T t : data) {
            double theta = Math.asin(random.nextDouble() * 2 - 1) + Math.PI / 2;
            double phi = random.nextDouble() * 2 * Math.PI;
            items.add(new Item<>(theta, phi, t));
        }
        return items;
    }

    public static <T> List<Item<T>> newSphericalLocation(int countInOneCircuit, T... data) {
        return newSphericalLocation(countInOneCircuit, Arrays.asList(data));
    }

    public static <T> List<Item<T>> newSphericalLocation(int countInOneCircuit, List<T> data) {
        List<Item<T>> items = new ArrayList<>(data.size());
        double pi_2 = Math.PI / 2;
        int index = 0;

        double radianGap = Math.PI * 2 / countInOneCircuit;
        for (int i = 0, count = countInOneCircuit / 4; i < count; i++) {
            if (i == 0) {
                for (int j = 0; j < countInOneCircuit; j++) {
                    insert(data, items, pi_2, radianGap * j, index++);
                }
            } else {
                double move = radianGap * i;
                double theta1 = pi_2 - move;
                double theta2 = pi_2 + move;
                int countInOneCircuit2 = (int) (countInOneCircuit * Math.sin(theta1));
                double radianGap2 = Math.PI * 2 / countInOneCircuit2;
                for (int j = 0; j < countInOneCircuit2; j++) {
                    insert(data, items, theta1, radianGap2 * j, index++);
                    insert(data, items, theta2, radianGap2 * j, index++);
                }
            }
        }
        if (countInOneCircuit % 4 == 0) {
            insert(data, items, 0D, 0D, index++);
            insert(data, items, Math.PI, 0D, index++);
        }
        return items;
    }

    public static int calcSphericalLocationItemCount(int countInOneCircuit) {
        int result = countInOneCircuit;
        double pi_2 = Math.PI / 2;
        double radianGap = Math.PI * 2 / countInOneCircuit;
        for (int i = 1, count = countInOneCircuit / 4; i < count; i++) {
            double move = radianGap * i;
            double theta1 = pi_2 - move;
            int countInOneCircuit2 = (int) (countInOneCircuit * Math.sin(theta1));
            result += countInOneCircuit2 * 2;
        }
        if (countInOneCircuit % 4 == 0) {
            result += 2;
        }
        return result;
    }

    private static <T> void insert(List<T> data, List<Item<T>> items, double theta, double phi, int index) {
        if (data.size() > index) {
            items.add(new Item<>(theta, phi, data.get(index)));
        }
    }
}