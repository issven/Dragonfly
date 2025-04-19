package com.dragonfly.expressions;

import com.dragonfly.celestialbodies.CelestialBody;

public class NewtonGravitationalLaw implements com.dragonfly.expressions.Expression{

    public static final double GRAVITATIONAL_CONSTANT = 6.67430e-20; // km^3 kg^-1 s^-2

    @Override
    public double solveFunction() {
        return Double.NaN;
    }

    /**
     * Gravitational Force on objectB by objectA
     * @param byObjectA by Planet A (any class instance from celestial bodies package)
     * @param onObjectB on Planet B (any class instance from celestial bodies package)
     * @return Gravitation Force on B by A
     */
    public static double[] gravitationalForce(CelestialBody byObjectA, CelestialBody onObjectB) {

        double[] gravitationForce = new double[3];

        double[] r = new double[3];

        for (int i = 0; i < 3; i++){
            r[i] = onObjectB.getPosition()[i] - byObjectA.getPosition()[i];
        }

        double rMagnitude = Math.sqrt(Math.pow(r[0], 2) + Math.pow(r[1], 2) + Math.pow(r[2], 2));

        for (int i = 0; i < 3; i++){
            gravitationForce[i] = (-GRAVITATIONAL_CONSTANT * byObjectA.getMass() * onObjectB.getMass() * r[i])/Math.abs(Math.pow(rMagnitude, 3));
        }

        return gravitationForce;
    }
}
