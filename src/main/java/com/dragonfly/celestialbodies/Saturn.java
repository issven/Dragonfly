package com.dragonfly.celestialbodies;

@SpaceObject
public class Saturn extends CelestialBody{

    public Saturn() {
        super(5.68e+26, new double[] {1.42e+09, -1.91e+08, -5.33e+07}, new double[] {7.48e-01, 9.55e+00, -1.96e-01});
    }

    public Saturn(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Saturn getDeepCopy() {
        return new Saturn(this.mass, this.position, this.velocity);
    }
}