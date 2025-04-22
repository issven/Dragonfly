package com.dragonfly.celestialbodies;

@SpaceObject
public class Neptune extends CelestialBody{

    public Neptune() {
        super(1.02e+26, new double[] {4.47e+09, -5.31e+07, -1.02e+08}, new double[] {2.87e-02, 5.47e+00, -1.13e-01});
    }

    public Neptune(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Neptune getDeepCopy() {
        return new Neptune(this.mass, this.position, this.velocity);
    }
}