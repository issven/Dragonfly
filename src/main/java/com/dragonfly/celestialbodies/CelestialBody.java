package com.dragonfly.celestialbodies;

import java.util.Arrays;

public abstract class CelestialBody {

    protected double mass; // kg
    protected double[] position; // km
    protected double[] velocity; // km/s
    protected double[] acceleration;

    public CelestialBody(){
        mass = Double.NaN;
        position = null;
        velocity = null;
    }

    public CelestialBody(double mass, double[] position, double[] velocity) {
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public double[] getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double[] acceleration) {
        this.acceleration = acceleration;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

    public CelestialBody getDeepCopy() {
        return null;
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + ": position" + Arrays.toString(position) + ", velocity" + Arrays.toString(velocity);
    }

}
