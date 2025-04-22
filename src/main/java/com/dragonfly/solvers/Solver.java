package com.dragonfly.solvers;

public interface Solver {

    // w0[] -> step() -> y_nPlusOne
    double step(double w0, double stepSize, double odeFunctionValue);

}
