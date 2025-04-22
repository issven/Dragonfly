package com.dragonfly.celestialbodies;

@SpaceObject
public class Mars extends CelestialBody{

    public Mars() {
        super(6.42e+23, new double[] {-2.15e+08, 1.27e+08, 7.94e+06}, new double[] {-1.15e+01, -1.87e+01, -1.11e-01});
    }

    public Mars(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Mars getDeepCopy() {
        return new Mars(this.mass, this.position, this.velocity);
    }
}
