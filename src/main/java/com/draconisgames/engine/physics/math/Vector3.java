package com.draconisgames.engine.physics.math;

public class Vector3 {

    public float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return x + ", " + y + ", " + z;
    }

    public Vector3 toDegrees() {
        x *= 180/Math.PI;
        y *= 180/Math.PI;
        z *= 180/Math.PI;
        return this;
    }

    public Vector3 toRad() {
        x /= 180/Math.PI;
        y /= 180/Math.PI;
        z /= 180/Math.PI;
        return this;
    }
}