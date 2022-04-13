package com.draconisgames.engine.physics.math.matrices;

import com.draconisgames.engine.physics.math.Position;
import com.draconisgames.engine.physics.math.Quaternion;
import com.draconisgames.engine.physics.math.Scale;
import com.draconisgames.engine.physics.math.Transform;

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

        float x2 = q.x * q.x;
        float xy = q.x * q.y;
        float xz = q.x * q.z;
        float xw = q.x * q.w;
        float y2 = q.y * q.y;
        float yz = q.y * q.z;
        float yw = q.y * q.w;
        float z2 = q.z * q.z;
        float zw = q.z * q.w;

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
        q.w = (float) (sqrt(1.0 + matrix[0][0] + matrix[1][1] + matrix[2][2]) / 2.0);
	    float w4 = (float) (4.0 * q.w);
	    q.x = (matrix[2][1] - matrix[1][2]) / w4 ;
	    q.y = (matrix[0][2] - matrix[2][0]) / w4 ;
	    q.z = (matrix[1][0] - matrix[0][1]) / w4 ;
        return q;
    }

    public static Matrix4f fromScale(Scale scale) {
        Matrix4f m = new Matrix4f();
        m.matrix[0][0] = scale.x;
        m.matrix[1][1] = scale.y;
        m.matrix[2][2] = scale.z;
        m.matrix[3][3] = 1;
        return m;
    }

    public Scale toScale() {
        Scale scale = new Scale();
        scale.x = matrix[0][0];
        scale.y = matrix[1][1];
        scale.z = matrix[2][2];
        return scale;
    }

    public static Matrix4f fromPosition(Position pos) {
        Matrix4f m = new Matrix4f();
        m.matrix[0][0] = m.matrix[1][1] = m.matrix[2][2] = m.matrix[3][3] = 1;
        m.matrix[0][3] = pos.x;
        m.matrix[1][3] = pos.y;
        m.matrix[2][3] = pos.z;
        return m;
    }

    public Position toPosition() {
        return new Position(matrix[0][3], matrix[1][3], matrix[2][3]);
    }

    public Matrix4f fromTransform(Transform t) {
        float[][] m = new float[4][4];

        float x2 = t.rot.x * t.rot.x;
        float xy = t.rot.x * t.rot.y;
        float xz = t.rot.x * t.rot.z;
        float xw = t.rot.x * t.rot.w;
        float y2 = t.rot.y * t.rot.y;
        float yz = t.rot.y * t.rot.z;
        float yw = t.rot.y * t.rot.w;
        float z2 = t.rot.z * t.rot.z;
        float zw = t.rot.z * t.rot.w;

        m[0][0] = (1 - 2 * (y2 + z2)) * t.scale.x;
        m[0][1] = 2 * (xy - zw) * t.scale.y;
        m[0][2] = 2 * (xz + yw) * t.scale.z;

        m[1][0] = 2 * (xy + zw) * t.scale.x;
        m[1][1] = (1 - 2 * (x2 + z2)) * t.scale.y;
        m[1][2] = 2 * (yz - xw) * t.scale.z;

        m[2][0] = 2 * (xz - yw)  * t.scale.x;
        m[2][1] = 2 * (yz + xw) * t.scale.y;
        m[2][2] = (1 - 2 * (x2 + y2)) * t.scale.z;

        m[0][3] = t.pos.x;
        m[1][3] = t.pos.y;
        m[2][3] = t.pos.z;

        m[3][3] = 1;

        return new Matrix4f(m);
    }

}
