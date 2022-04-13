package com.draconisgames.celticengine.physics.math.matrices;

import com.draconisgames.celticengine.physics.math.Quaternion;
import com.draconisgames.celticengine.physics.math.Transform;
import com.draconisgames.celticengine.physics.math.Vector3;

import static java.lang.Math.sqrt;

public class TranslationMatrix extends Matrix4f {

    public TranslationMatrix(Quaternion q) {

        float x2 = q.getX() * q.getX();
        float xy = q.getX() * q.getY();
        float xz = q.getX() * q.getZ();
        float xw = q.getX() * q.getW();
        float y2 = q.getY() * q.getY();
        float yz = q.getY() * q.getZ();
        float yw = q.getY() * q.getW();
        float z2 = q.getZ() * q.getZ();
        float zw = q.getZ() * q.getW();

        matrix[0][0] = 1 - 2 * (y2 + z2);
        matrix[0][1] = 2 * (xy - zw);
        matrix[0][2] = 2 * (xz + yw);

        matrix[1][0] = 2 * (xy + zw);
        matrix[1][1] = 1 - 2 * (x2 + z2);
        matrix[1][2] = 2 * (yz - xw);

        matrix[2][0] = 2 * (xz - yw);
        matrix[2][1] = 2 * (yz + xw);
        matrix[2][2] = 1 - 2 * (x2 + y2);

        matrix[3][3] = 1;
    }

    public TranslationMatrix(float[][] m) {
        matrix = m;
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

    public TranslationMatrix fromScale(Vector3 scale) {
        float[][] m = new float[4][4];
        m[0][0] = scale.getX();
        m[1][1] = scale.getY();
        m[2][2] = scale.getZ();
        return new TranslationMatrix(m);
    }

    public Vector3 toScale() {
        Vector3 scale = new Vector3();
        scale.setX(matrix[0][0]);
        scale.setY(matrix[1][1]);
        scale.setZ(matrix[2][2]);
        return scale;
    }

    public static TranslationMatrix fromPosition(Vector3 pos) {
        float[][] m = new float[4][4];
        m[0][3] = pos.getX();
        m[1][3] = pos.getY();
        m[2][3] = pos.getZ();
        return new TranslationMatrix(m);
    }

    public Vector3 toPosition() {
        return new Vector3(matrix[0][3], matrix[1][3], matrix[2][3]);
    }

    public TranslationMatrix(Transform t) {

        float x2 = t.getRotation().getX() * t.getRotation().getX();
        float xy = t.getRotation().getX() * t.getRotation().getY();
        float xz = t.getRotation().getX() * t.getRotation().getZ();
        float xw = t.getRotation().getX() * t.getRotation().getW();
        float y2 = t.getRotation().getY() * t.getRotation().getY();
        float yz = t.getRotation().getY() * t.getRotation().getZ();
        float yw = t.getRotation().getY() * t.getRotation().getW();
        float z2 = t.getRotation().getZ() * t.getRotation().getZ();
        float zw = t.getRotation().getZ() * t.getRotation().getW();

        matrix[0][0] = (1 - 2 * (y2 + z2)) * t.getScale().getX();
        matrix[0][1] = 2 * (xy - zw) * t.getScale().getY();
        matrix[0][2] = 2 * (xz + yw) * t.getScale().getZ();

        matrix[1][0] = 2 * (xy + zw) * t.getScale().getX();
        matrix[1][1] = (1 - 2 * (x2 + z2)) * t.getScale().getY();
        matrix[1][2] = 2 * (yz - xw) * t.getScale().getZ();

        matrix[2][0] = 2 * (xz - yw)  * t.getScale().getX();
        matrix[2][1] = 2 * (yz + xw) * t.getScale().getY();
        matrix[2][2] = (1 - 2 * (x2 + y2)) * t.getScale().getZ();

        matrix[0][3] = t.getPosition().getX();
        matrix[1][3] = t.getPosition().getY();
        matrix[2][3] = t.getPosition().getZ();
    }

}
