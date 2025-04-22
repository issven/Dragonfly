package com.dragonfly.celestialbodies;

@SpaceObject
public class Jupiter extends CelestialBody{

    public Jupiter() {
        super(1.90e+27, new double[] {5.54e+07, 7.62e+08, -4.40e+06}, new double[] {-1.32e+01, 1.29e+01, 5.22e-02});
    }

    public Jupiter(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Jupiter getDeepCopy() {
        return new Jupiter(this.mass, this.position, this.velocity);
    }
}
