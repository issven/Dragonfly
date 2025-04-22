package com.dragonfly.celestialbodies;

@SpaceObject
public class Earth extends CelestialBody{

    public Earth() {
        super(5.97e+24, new double[] {-1.47e+08, -2.97e+07, 2.75e+04}, new double[] {5.31e+00, -2.93e+01, 6.69e-04});
    }

    public Earth(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Earth getDeepCopy() {
        return new Earth(this.mass, this.position, this.velocity);
    }
}