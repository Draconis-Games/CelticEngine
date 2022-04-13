package com.draconisgames.engine.physics.math;

import static java.lang.Math.*;

public class Quaternion {

    public float w, x, y, z;

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Quaternion() {
        this(0, 0, 0, 0);
    }
    
    public static Quaternion fromRadEuler(float roll, float pitch, float yaw) {
        float cy = (float) cos(yaw * 0.5);
        float sy = (float) sin(yaw * 0.5);
        float cp = (float) cos(pitch * 0.5);
        float sp = (float) sin(pitch * 0.5);
        float cr = (float) cos(roll * 0.5);
        float sr = (float) sin(roll * 0.5);

        Quaternion q = new Quaternion();

        q.w = cr * cp * cy + sr * sp * sy;
        q.x = sr * cp * cy - cr * sp * sy;
        q.y = cr * sp * cy + sr * cp * sy;
        q.z = cr * cp * sy - sr * sp * cy;
        return q;
    }

    public static Quaternion fromDegEuler(float x, float y, float z) {
        Vector3 v = new Vector3(x, y, z);
        v.toRad();
        return fromRadEuler(v.x, v.y, v.z);
    }

    public static Quaternion fromDegVector(Vector3 vi) {
        Vector3 v = new Vector3(vi.x, vi.y, vi.z);
        v.toRad();
        return fromRadEuler(v.x, v.y, v.z);
    }

    public static Quaternion fromRadVector(Vector3 v) {
        return fromRadEuler(v.x, v.y, v.z);
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
