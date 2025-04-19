package com.dragonfly.expressions;

import com.dragonfly.celestialbodies.Earth;
import com.dragonfly.celestialbodies.Sun;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewtonGravitationalLawTest {

    @Test // Earth @ -ve pos is attracted towards Sun @ +ve 000 -> GForce on Earth by Sun should be mostly +ve
    void testGravityOnEarthBySun() {
        assertArrayEquals(new double[] {3.456e+19, 6.980e+18, -6.646e+15}, NewtonGravitationalLaw.gravitationalForce(new Sun(), new Earth()), 1e16);
    }
}