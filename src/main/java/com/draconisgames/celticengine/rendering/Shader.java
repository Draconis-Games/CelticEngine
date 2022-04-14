package com.draconisgames.celticengine.rendering;

import com.draconisgames.celticengine.file.TextLoader;
import com.draconisgames.celticengine.physics.math.matrices.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
	Logger logger = LogManager.getLogger(Shader.class);

	private final int programId;

	private int vertexShader;
	private int fragmentShader;

	private Map<String, Integer> uniforms = new HashMap<>();


	public Shader() {
		programId = glCreateProgram();
		if(programId == 0)
			logger.error("Could not create shader.");
	}
	public Shader(String vertPath, String fragPath){
		this();
		createVertexShader(TextLoader.load(vertPath));
		createFragmentShader(TextLoader.load(fragPath));
		link();
	}

	protected int createShader(String shaderCode, int shaderType){
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			logger.error("Failed to Load Shader");
			return 0;
		}

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			logger.error("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
			return 0;
		}

		glAttachShader(programId, shaderId);

		return shaderId;
	}

	public void link(){
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
			logger.error("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
			return;
		}

		if (vertexShader != 0) {
			glDetachShader(programId, vertexShader);
		}
		if (fragmentShader != 0) {
			glDetachShader(programId, fragmentShader);
		}
		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == GL_FALSE) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
		int numOfUniforms = glGetProgrami(programId, GL_ACTIVE_UNIFORMS) + 1;

		for (int i = 0; i < numOfUniforms; i++) {
			String name = glGetActiveUniform(programId,i,IntBuffer.allocate(1), IntBuffer.allocate(1));

			int location = glGetUniformLocation(programId, name);

			uniforms.put(name, location);
		}

	}
	public void setMat4(String name,Matrix4f mat4){
		use();
		glUniformMatrix4fv(uniforms.get(name),false, mat4.toFlatArray());
	}

	public void createVertexShader(String shaderCode){
		vertexShader = createShader(shaderCode,GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String shaderCode){
		fragmentShader = createShader(shaderCode,GL_FRAGMENT_SHADER);
	}

	public void use(){
		glUseProgram(programId);
	}

	public void unbind(){
		glUseProgram(0);
	}

	public void cleanup(){
		unbind();
		if(programId != 0){
			glDeleteProgram(programId);
		}
	}

	public int getProgramId() {
		return programId;
	}
}
