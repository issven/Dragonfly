package com.dragonfly.celestialbodies;

@SpaceObject
public class Mercury extends CelestialBody{

    public Mercury() {
        super(3.30e+23, new double[] {-5.67e+07, -3.23e+07, 2.58e+06}, new double[] {1.39e+01, -4.03e+01, -4.57e+00});
    }

    public Mercury(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Mercury getDeepCopy() {
        return new Mercury(this.mass, this.position, this.velocity);
    }
}