package com.dragonfly.solvers;

public class EulerSolver implements Solver{

    @Override
    public double step(double w0, double stepSize, double odeFunctionValue) {
        return w0 + stepSize * odeFunctionValue;
    }

}
