package com.draconisgames.engine.physics.math;

public class Transform {

    private Vector3 pos;
    private Quaternion rot;
    private Vector3 scale;

    public Transform() {
        pos = new Vector3();
        rot = Quaternion.fromEuler(0, 0, 0);
        scale = new Vector3();
    }

    public Transform(Vector3 pos, Quaternion rot, Vector3 scale) {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public Transform(Vector3 pos, Vector3 scale, Vector3 r) {
        this.pos = pos;
        this.scale = scale;
        rot = Quaternion.fromEuler(r);
    }

    public Vector3 getPosition() {
        return pos;
    }

    public void setPosition(Vector3 pos) {
        this.pos = pos;
    }

    public Quaternion getRotation() {
        return rot;
    }

    public void setRotation(Quaternion rot) {
        this.rot = rot;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }
}
