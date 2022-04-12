package com.draconis.physics.math;

import static java.lang.Math.*;

public class Quaternion {

    public float w, x, y, z;

    public static Quaternion fromEuler(float roll, float pitch, float yaw) {
        float cy = (float) cos(roll * 0.5f);
        float sy = (float) sin(roll * 0.5f);
        float cp = (float) cos(pitch * 0.5f);
        float sp = (float) sin(pitch * 0.5f);
        float cr = (float) cos(yaw * 0.5f);
        float sr = (float) sin(yaw * 0.5f);

        float a = cr * cp * cy + sr * sp * sy;
        float b = sr * cp * cy - cr * sp * sy;
        float c = cr * sp * cy + sr * cp * sy;
        float d = cr * cp * sy - sr * sp * cy;
        return new Quaternion(a, b, c, d);
    }

    public static Quaternion fromVector(Vector3 v) {
        return fromEuler(v.x, v.y, v.z);
    }

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float magnitude() {
        return (float) sqrt(w * w + x * x + y * y + z * z);
    }

    public Quaternion normalize() {
        float magnitude = magnitude();
        w /= magnitude;
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;

        return this;
    }

    public Vector3 toVector() {
        float a, b, c;
        a = (float) atan2(2 * (w * x + y * z), w * w - x * x - y * y + z * z);
        b = (float) asin(2 * (w * y - x * z));
        c = (float) atan2(2 * (w * z + x * y), w * w + x * x - y * y - z * z);
        return new Vector3(a, b, c);
    }

    public String toString() {
        return w + ", " + x + ", " + y + ", " + z;
    }

}
