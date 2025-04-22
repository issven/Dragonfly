package com.dragonfly.main;

import com.dragonfly.celestialbodies.*;
import com.dragonfly.expressions.NewtonGravitationalLaw;
import com.dragonfly.solvers.EulerSolver;
import com.dragonfly.solvers.Solver;

import java.util.HashMap;

public class SolarSystemSimulation {

    public static void main(String[] args){

        // Let's first set up our Initial model of SolarSystem at time t0
        // We will use Hash map to track the Planets and their instance states
        HashMap<String, CelestialBody> SolarSystemMap = new HashMap<>();

        SolarSystemMap.put("Sun", new Sun());
        SolarSystemMap.put("Mercury", new Mercury());
        SolarSystemMap.put("Venus", new Venus());
        SolarSystemMap.put("Earth", new Earth());
        SolarSystemMap.put("Moon", new Moon());
        SolarSystemMap.put("Mars", new Mars());
        SolarSystemMap.put("Jupiter", new Jupiter());
        SolarSystemMap.put("Saturn", new Saturn());
        SolarSystemMap.put("Titan", new Titan());
        SolarSystemMap.put("Uranus", new Uranus());
        SolarSystemMap.put("Neptune", new Neptune());

        System.out.println(displayCurrentState(SolarSystemMap));
        updateSystemByStepSize(0.5, new EulerSolver(), SolarSystemMap);
        System.out.println(displayCurrentState(SolarSystemMap));
    }

    public static void updateSystemByStepSize(double stepSize, Solver solver, HashMap<String, CelestialBody> solarSystemMap) {

        // Snapshot current states
        HashMap<String, CelestialBody> snapshotMap = createDeepCopy(solarSystemMap);

        // For each planet:
        for(CelestialBody object : solarSystemMap.values()){

            // 1. Compute net Gravity
            double[] netGravity = computeNetGravitation(snapshotMap, object);
            // 2. Compute net Acceleration
            object.setAcceleration(computeNetAcceleration(netGravity, object));
            // 3. Update Position
            updatePosition(solarSystemMap, object, solver, stepSize);
            // 4. Update Velocity
            updateVelocity(solarSystemMap, object, solver, stepSize);

        }

    }

    public static double[] computeNetGravitation(HashMap<String, CelestialBody> snapshotMap, CelestialBody planet){

        double[] netGravity = new double[3];

        for(CelestialBody body : snapshotMap.values()){

            if (body.getClass().getSimpleName().equals(planet.getClass().getSimpleName()))
                continue;

            double[] gravity = NewtonGravitationalLaw.gravitationalForce(body, planet);

            for (int i = 0; i < 3; i++) {
                netGravity[i] += gravity[i];
            }

        }

        return  netGravity;
    }

    public static double[] computeNetAcceleration(double[] netGravity, CelestialBody onObj){

        double[] netAcceleration = new double[3];

        for (int i = 0; i < 3; i++){
            netAcceleration[i] = netGravity[i]/ onObj.getMass();
        }

        return netAcceleration;

    }

    public static void updatePosition(HashMap<String, CelestialBody> solarSystemMap, CelestialBody planet, Solver ode_method, double stepSize){

        double[] updatedPosition = new double[3];
        double[] w0Values = planet.getPosition();
        double[] odeValues = planet.getVelocity();

        for (int i = 0; i < 3; i++){
            updatedPosition[i] = ode_method.step(w0Values[i], stepSize, odeValues[i]);
        }

        solarSystemMap.get(planet.getClass().getSimpleName()).setPosition(updatedPosition);
    }

    public static void updateVelocity(HashMap<String, CelestialBody> solarSystemMap, CelestialBody planet, Solver ode_method, double stepSize){

        double[] updatedVelocity = new double[3];
        double[] w0Values = planet.getVelocity();
        double[] odeValues = planet.getAcceleration();

        for (int i = 0; i < 3; i++){
            updatedVelocity[i] = ode_method.step(w0Values[i], stepSize, odeValues[i]);
        }

        solarSystemMap.get(planet.getClass().getSimpleName()).setVelocity(updatedVelocity);
    }

    public static String displayCurrentState(HashMap<String, CelestialBody> solarSystemMap){

        StringBuilder displayString = new StringBuilder();

        for (CelestialBody planet : solarSystemMap.values()) {
            displayString.append(planet.toString()).append("\n");
        }
        return displayString.toString();

    }

    public static HashMap<String, CelestialBody> createDeepCopy(HashMap<String, CelestialBody> originalMap){

        HashMap<String, CelestialBody> deepCopyMap = new HashMap<>();
        for (CelestialBody planet : originalMap.values()){
            deepCopyMap.put(planet.getClass().getSimpleName(), planet.getDeepCopy());
        }

        return deepCopyMap;
    }

}
