package com.draconisgames.celticengine.physics.math;

import com.draconisgames.celticengine.physics.math.matrices.Matrix4f;

import static java.lang.Math.*;

public class Quaternion {

    private float w, x, y, z;

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Quaternion() {
        this(1, 0, 0, 0);
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

    public static Quaternion fromEuler(float x, float y, float z) {
        x /= 180/Math.PI;
        y /= 180/Math.PI;
        z /= 180/Math.PI;
        return fromRadEuler(x, y, z);
    }

    public static Quaternion fromEuler(Vector3 v) {
        return fromEuler(v.getX(), v.getY(), v.getZ());
    }

    public static Quaternion fromRadEuler(Vector3 v) {
        return fromRadEuler(v.getX(), v.getY(), v.getZ());
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

    public Matrix4f toMatrix() {
        float x2 = x * x;
        float xy = x * y;
        float xz = x * z;
        float xw = x * w;
        float y2 = y * y;
        float yz = y * z;
        float yw = y * w;
        float z2 = z * z;
        float zw = z * w;

        float[][] m = new float[4][4];

        m[0][0] = 1 - 2 * (y2 + z2);
        m[0][1] = 2 * (xy - zw);
        m[0][2] = 2 * (xz + yw);

        m[1][0] = 2 * (xy + zw);
        m[1][1] = 1 - 2 * (x2 + z2);
        m[1][2] = 2 * (yz - xw);

        m[2][0] = 2 * (xz - yw);
        m[2][1] = 2 * (yz + xw);
        m[2][2] = 1 - 2 * (x2 + y2);

        m[3][3] = 1;

        return new Matrix4f(m);
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
