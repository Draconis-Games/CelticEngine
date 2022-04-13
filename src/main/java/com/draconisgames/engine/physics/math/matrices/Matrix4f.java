package com.draconisgames.engine.physics.math.matrices;

import com.draconisgames.engine.physics.math.*;

import static java.lang.Math.sqrt;

public class Matrix4f {

    public float[][] matrix = new float[4][4];

    public Matrix4f() {}

    public Matrix4f(float[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix4f(float a, float b, float c, float d,
                    float e, float f, float g, float h,
                    float i, float j, float k, float l,
                    float m, float n, float o, float p) {

        matrix[0][0] = a; matrix[0][1] = b; matrix[0][2] = c; matrix[0][3] = d;
        matrix[1][0] = e; matrix[1][1] = f; matrix[1][2] = g; matrix[1][3] = h;
        matrix[2][0] = i; matrix[2][1] = j; matrix[2][2] = k; matrix[2][3] = l;
        matrix[3][0] = m; matrix[3][1] = n; matrix[3][2] = o; matrix[3][3] = p;

    }

    public void multiply(Matrix4f m) {
        float[][] nm = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nm[i][j] = (matrix[i][0] * m.matrix[0][j]) + (matrix[i][1] * m.matrix[1][j]) + (matrix[i][2] * m.matrix[2][j]) + (matrix[i][3] * m.matrix[3][j]);
            }
        }
        matrix = nm;
    }

    public static Matrix4f multiply(Matrix4f m1, Matrix4f m2) {
        float[][] nm = new float[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                nm[i][j] = (m1.matrix[i][0] * m2.matrix[0][j]) + (m1.matrix[i][1] * m2.matrix[1][j]) + (m1.matrix[i][2] * m2.matrix[2][j]) + (m1.matrix[i][3] * m2.matrix[3][j]);
            }
        }
        return new Matrix4f(nm);
    }

    public void add(Matrix4f m) {
        for (int i = 0; i < 16; i++) {
            matrix[(int) i / 4][i % 4] += m.matrix[(int) i / 4][i % 4];
        }
    }

    public static Matrix4f add(Matrix4f m1, Matrix4f m2) {
        float[][] nm = new float[4][4];
        for (int i = 0; i < 16; i++) {
            nm[(int) i / 4][i % 4] = m1.matrix[(int) i / 4][i % 4] + m2.matrix[(int) i / 4][i % 4];
        }
        return new Matrix4f(nm);
    }

    public void subtract(Matrix4f m) {
        for (int i = 0; i < 16; i++) {
            matrix[(int) i / 4][i % 4] -= m.matrix[(int) i / 4][i % 4];
        }
    }

    public static Matrix4f subtract(Matrix4f m1, Matrix4f m2) {
        float[][] nm = new float[4][4];
        for (int i = 0; i < 16; i++) {
            nm[(int) i / 4][i % 4] = m1.matrix[(int) i / 4][i % 4] - m2.matrix[(int) i / 4][i % 4];
        }
        return new Matrix4f(nm);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j++) {
                s += matrix[i][j] + ", ";
            }
            s += "\n";
        }
        return s.substring(0, s.length() - 3);
    }

    public static Matrix4f fromQuaternion(Quaternion q) {

        float x2 = q.getX() * q.getX();
        float xy = q.getX() * q.getY();
        float xz = q.getX() * q.getZ();
        float xw = q.getX() * q.getW();
        float y2 = q.getY() * q.getY();
        float yz = q.getY() * q.getZ();
        float yw = q.getY() * q.getW();
        float z2 = q.getZ() * q.getZ();
        float zw = q.getZ() * q.getW();

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

    public Quaternion toQuaternion() {
        Quaternion q = new Quaternion();
        q.setW((float) (sqrt(1.0 + matrix[0][0] + matrix[1][1] + matrix[2][2]) / 2.0));
	    float w4 = (float) (4.0 * q.getW());
	    q.setX((matrix[2][1] - matrix[1][2]) / w4);
	    q.setY((matrix[0][2] - matrix[2][0]) / w4);
	    q.setZ((matrix[1][0] - matrix[0][1]) / w4);
        return q;
    }

    public static Matrix4f fromScale(Vector3 scale) {
        Matrix4f m = new Matrix4f();
        m.matrix[0][0] = scale.getX();
        m.matrix[1][1] = scale.getY();
        m.matrix[2][2] = scale.getZ();
        m.matrix[3][3] = 1;
        return m;
    }

    public Vector3 toScale() {
        Vector3 scale = new Vector3();
        scale.setX(matrix[0][0]);
        scale.setY(matrix[1][1]);
        scale.setZ(matrix[2][2]);
        return scale;
    }

    public static Matrix4f fromPosition(Vector3 pos) {
        Matrix4f m = new Matrix4f();
        m.matrix[0][0] = m.matrix[1][1] = m.matrix[2][2] = m.matrix[3][3] = 1;
        m.matrix[0][3] = pos.getX();
        m.matrix[1][3] = pos.getY();
        m.matrix[2][3] = pos.getZ();
        return m;
    }

    public Vector3 toPosition() {
        return new Vector3(matrix[0][3], matrix[1][3], matrix[2][3]);
    }

    public static Matrix4f fromTransform(Transform t) {
        float[][] m = new float[4][4];

        float x2 = t.getRotation().getX() * t.getRotation().getX();
        float xy = t.getRotation().getX() * t.getRotation().getY();
        float xz = t.getRotation().getX() * t.getRotation().getZ();
        float xw = t.getRotation().getX() * t.getRotation().getW();
        float y2 = t.getRotation().getY() * t.getRotation().getY();
        float yz = t.getRotation().getY() * t.getRotation().getZ();
        float yw = t.getRotation().getY() * t.getRotation().getW();
        float z2 = t.getRotation().getZ() * t.getRotation().getZ();
        float zw = t.getRotation().getZ() * t.getRotation().getW();

        m[0][0] = (1 - 2 * (y2 + z2)) * t.getScale().getX();
        m[0][1] = 2 * (xy - zw) * t.getScale().getY();
        m[0][2] = 2 * (xz + yw) * t.getScale().getZ();

        m[1][0] = 2 * (xy + zw) * t.getScale().getX();
        m[1][1] = (1 - 2 * (x2 + z2)) * t.getScale().getY();
        m[1][2] = 2 * (yz - xw) * t.getScale().getZ();

        m[2][0] = 2 * (xz - yw)  * t.getScale().getX();
        m[2][1] = 2 * (yz + xw) * t.getScale().getY();
        m[2][2] = (1 - 2 * (x2 + y2)) * t.getScale().getZ();

        m[0][3] = t.getPosition().getX();
        m[1][3] = t.getPosition().getY();
        m[2][3] = t.getPosition().getZ();

        m[3][3] = 1;

        return new Matrix4f(m);
    }

}
