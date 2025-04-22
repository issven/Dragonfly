package com.dragonfly.celestialbodies;

@SpaceObject
public class Venus extends CelestialBody{

    public Venus() {
        super(4.87e+24, new double[] {-1.04e+08, -3.19e+07, 5.55e+06}, new double[] {9.89e+00, -3.37e+01, -1.03e+00});
    }

    public Venus(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Venus getDeepCopy() {
        return new Venus(this.mass, this.position, this.velocity);
    }
}
