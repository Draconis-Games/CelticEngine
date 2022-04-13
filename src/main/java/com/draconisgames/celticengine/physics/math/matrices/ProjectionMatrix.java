package com.draconisgames.celticengine.physics.math.matrices;

public class ProjectionMatrix extends Matrix4f {

    public ProjectionMatrix() {
        matrix = new float[4][4];
    }

    public ProjectionMatrix(float[][] m) {
        super(m);
    }

    public ProjectionMatrix(float aspectRatio, float fov, float zNear, float zFar) {
        float t = (float) (1/Math.tan(fov/2));
        float zm = zFar - zNear;
        float zp = zFar + zNear;
        matrix[0][0] = t/aspectRatio;
        matrix[1][1] = t;
        matrix[2][2] = -zp/zm;
        matrix[2][3] = -(2*zFar*zNear)/zm;
        matrix[3][2] = -1;
    }

}
