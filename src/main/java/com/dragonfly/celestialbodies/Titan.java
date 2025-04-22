package com.dragonfly.celestialbodies;

@SpaceObject
public class Titan extends CelestialBody{

    public Titan() {
        super(1.35e+23, new double[] {1.42e+09, -1.92e+08, -5.28e+07}, new double[] {5.95e+00, 7.68e+00, 2.54e-01});
    }

    public Titan(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Titan getDeepCopy() {
        return new Titan(this.mass, this.position, this.velocity);
    }
}
