package com.dragonfly.celestialbodies;

@SpaceObject
public class Moon extends CelestialBody{

    public Moon() {
        super(7.35e+22, new double[] {-1.47e+08, -2.95e+07, 5.29e+04}, new double[] {4.53e+00, -2.86e+01, 6.73e-02});
    }

    public Moon(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Moon getDeepCopy() {
        return new Moon(this.mass, this.position, this.velocity);
    }
}