package com.dragonfly.celestialbodies;

@SpaceObject
public class Sun extends CelestialBody{

    public Sun() {
        super(1.99e+30, new double[] {0.00e+00, 0.00e+00, 0.00e+00}, new double[] {0.00e+00, 0.00e+00, 0.00e+00});
    }

    public Sun(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Sun getDeepCopy() {
        return new Sun(this.mass, this.position, this.velocity);
    }
}
