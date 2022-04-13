package com.draconisgames.engine.physics.math;

public class Transform {

    public Position pos;
    public Quaternion rot;
    public Scale scale;

    public Transform() {
        pos = new Position();
        rot = Quaternion.fromDegEuler(0, 0, 0);
        scale = new Scale();
    }

    public Transform(Position pos, Quaternion rot, Scale scale) {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public Transform(Position pos, Scale scale, Vector3 r) {
        this.pos = pos;
        this.scale = scale;
        rot = Quaternion.fromDegVector(r);
    }
}
