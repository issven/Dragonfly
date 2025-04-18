package com.dragonfly.celestialbodies;

public abstract class CelestialBody {

    protected double mass; // kg
    protected double[] position; // km
    protected double[] velocity; // km/s

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

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double getMass() {
        return mass;
    }

}

