package com.dragonfly.solvers;

import java.util.HashMap;

public interface Solver {

    // y_n[] -> step() -> y_nPlusOne
    double step(double y_n, double stepSize);

}
