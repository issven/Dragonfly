package com.dragonfly.solvers;

import java.util.HashMap;

public interface Solver {

    // y_n[] -> step() -> y_nPlusOne
    static double[] step(double[] y_n, double stepSize) {
        return new double[3];
    }

    // solve System for time t
    static HashMap<String, Double[]> solve() { return new HashMap<>();}
}
