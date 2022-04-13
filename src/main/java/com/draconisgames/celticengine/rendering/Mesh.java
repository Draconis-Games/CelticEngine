package com.draconisgames.celticengine.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh implements Renderable {
    IntBuffer indices;
    FloatBuffer vertices;
    FloatBuffer texCoords;
    FloatBuffer normals;
    Shader shader;

    public Mesh(IntBuffer indices, FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals) {
        this.indices = indices;
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.normals = normals;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    @Override
    public void bindVbos() {

    }

    @Override
    public void bind() {

    }

    @Override
    public void render() {

    }

    @Override
    public void delete() {

    }
}
