package com.dragonfly.celestialbodies;

@SpaceObject
public class Uranus extends CelestialBody{

    public Uranus() {
        super(8.68e+25, new double[] {1.62e+09, 2.43e+09, -1.19e+07}, new double[] {-5.72e+00, 3.45e+00, 8.70e-02});
    }

    public Uranus(double mass, double[] position, double[] velocity) {
        super(mass, position.clone(), velocity.clone()); // clone to avoid sharing references
    }

    @Override
    public Uranus getDeepCopy() {
        return new Uranus(this.mass, this.position, this.velocity);
    }
}
