package com.draconisgames.celticengine.rendering;

import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20C.glBindAttribLocation;
import static org.lwjgl.opengl.GL20C.glDisableVertexAttribArray;

public class Mesh implements Renderable {
    IntBuffer indices;
    FloatBuffer vertices;
    FloatBuffer texCoords;
    FloatBuffer normals;
    Shader shader;

    int vaoID;
    int vertexVboId;
    int uvVboId;
    int normalVboId;

    int indicesId;
    int numbOfIndices;


    public Mesh(IntBuffer indices, FloatBuffer vertices, FloatBuffer texCoords, FloatBuffer normals, int numbOfIndices) {
        this.indices = indices;
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.normals = normals;
        this.numbOfIndices = numbOfIndices;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }


    @Override
    public void bind() {
        vertexVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexVboId);
        glBufferData(vertexVboId, vertices, GL_STATIC_DRAW);

        uvVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, uvVboId);
        glBufferData(uvVboId, texCoords, GL_STATIC_DRAW);

        normalVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalVboId);
        glBufferData(normalVboId, normals, GL_STATIC_DRAW);

        indicesId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);
        glBufferData(indicesId, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);


        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vertexVboId);
        glVertexAttribPointer(0,3,GL_FLOAT,false,0,0);
        glBindAttribLocation(shader.getProgramId(),0,"in_position");

        glEnableVertexAttribArray(1);
        glBufferData(uvVboId, texCoords, GL_STATIC_DRAW);
        glVertexAttribPointer(1,2,GL_FLOAT,false,0,0);
        glBindAttribLocation(shader.getProgramId(),1,"in_texture");

        glEnableVertexAttribArray(2);
        glBufferData(normalVboId, normals, GL_STATIC_DRAW);
        glVertexAttribPointer(2,3,GL_FLOAT,false,0,0);
        glBindAttribLocation(shader.getProgramId(),2,"in_normal");

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);

        glBindVertexArray(0);
    }

    @Override
    public void render() {
        shader.use();
        glBindVertexArray(vaoID);

        glDrawElements(GL_TRIANGLES, numbOfIndices, GL_UNSIGNED_INT, 0);

    }

    @Override
    public void delete() {

    }
}
