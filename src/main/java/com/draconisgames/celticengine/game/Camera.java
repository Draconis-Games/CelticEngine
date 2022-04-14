package com.draconisgames.celticengine.game;

import com.draconisgames.celticengine.physics.math.Transform;
import com.draconisgames.celticengine.physics.math.matrices.Matrix4f;
import com.draconisgames.celticengine.physics.math.matrices.TranslationMatrix;

public class Camera extends GameObject {

    private float aspectRatio, fov;

    public Camera(Transform transform, float aspectRatio, float fov) {
        super(transform);
        this.aspectRatio = aspectRatio;
        this.fov = fov;
    }

    public Matrix4f getMatrix(float near, float far) {
        return Matrix4f.projectionMatrix(aspectRatio, fov, near, far);
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
