package com.draconisgames.celticengine.rendering;

import com.draconisgames.celticengine.world.World;
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
    int positionBuffer;
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

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Position VBO
        positionBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, positionBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Colour VBO
        normalVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, normalVboId);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        uvVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, uvVboId);
        glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 0, 0);

        // Index VBO
        indicesId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    @Override
    public void render() {
        shader.use();

        shader.setMat4("transform", World.activeCam.getMatrix());

        glBindVertexArray(vaoID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);


        glDrawElements(GL_TRIANGLES, numbOfIndices, GL_UNSIGNED_INT, 0);

    }

    @Override
    public void delete() {

    }
}
