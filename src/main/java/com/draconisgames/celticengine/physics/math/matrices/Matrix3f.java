package com.draconisgames.celticengine.physics.math.matrices;

public class Matrix3f {

    public float[][] matrix = new float[3][3];

    public Matrix3f() {}

    public Matrix3f(float[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix3f(float a, float b, float c, 
                    float d, float e, float f, 
                    float g, float h, float i) {

        matrix[0][0] = a; matrix[0][1] = b; matrix[0][2] = c;
        matrix[1][0] = d; matrix[1][1] = e; matrix[1][2] = f;
        matrix[2][0] = g; matrix[2][1] = h; matrix[2][2] = i;

    }

    public void multiply(Matrix3f m) {
        float[][] nm = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nm[i][j] = (matrix[i][0] * m.matrix[0][j]) + (matrix[i][1] * m.matrix[1][j]) + (matrix[i][2] * m.matrix[2][j]);
            }
        }
        matrix = nm;
    }

    public static Matrix3f multiply(Matrix3f m1, Matrix3f m2) {
        float[][] nm = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                nm[i][j] = (m1.matrix[i][0] * m2.matrix[0][j]) + (m1.matrix[i][1] * m2.matrix[1][j]) + (m1.matrix[i][2] * m2.matrix[2][j]);
            }
        }
        return new Matrix3f(nm);
    }

    public void add(Matrix3f m) {
        for (int i = 0; i < 9; i++) {
            matrix[(int) i / 3][i % 3] += m.matrix[(int) i / 3][i % 3];
        }
    }

    public static Matrix3f add(Matrix3f m1, Matrix3f m2) {
        float[][] nm = new float[3][3];
        for (int i = 0; i < 9; i++) {
            nm[(int) i / 3][i % 3] = m1.matrix[(int) i / 3][i % 3] + m2.matrix[(int) i / 3][i % 3];
        }
        return new Matrix3f(nm);
    }

    public void subtract(Matrix3f m) {
        for (int i = 0; i < 9; i++) {
            matrix[(int) i / 3][i % 3] -= m.matrix[(int) i / 3][i % 3];
        }
    }

    public static Matrix3f subtract(Matrix3f m1, Matrix3f m2) {
        float[][] nm = new float[3][3];
        for (int i = 0; i < 9; i++) {
            nm[(int) i / 3][i % 3] = m1.matrix[(int) i / 3][i % 3] - m2.matrix[(int) i / 3][i % 3];
        }
        return new Matrix3f(nm);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j++) {
                s += matrix[i][j] + ", ";
            }
            s += "\n";
        }
        return s.substring(0, s.length() - 3);
    }

}
