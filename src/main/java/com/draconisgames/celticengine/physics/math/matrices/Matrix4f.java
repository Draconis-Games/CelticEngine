package com.draconisgames.celticengine.physics.math.matrices;

import com.draconisgames.celticengine.physics.math.*;

import static java.lang.Math.sqrt;

public class Matrix4f {

    protected float[][] matrix = new float[4][4];

    public Matrix4f() {
        for (int i = 0; i < 4; i++) {
            matrix[i][i] = 1;
        }
    }

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

    public float[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }
}
