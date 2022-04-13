package com.draconisgames.engine.rendering;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
	Logger logger = LogManager.getLogger(ShaderProgram.class);

	private final int programId;

	private int vertexShader;
	private int fragmentShader;


	public ShaderProgram() throws Exception {
		programId = glCreateProgram();
		if(programId == 0)
			throw new Exception("Could not create shader.");
	}

	public void createVertexShader(String shaderCode){
		vertexShader = createShader(shaderCode,GL_VERTEX_SHADER);
	}
	public void createFragmentShader(String shaderCode){
		fragmentShader = createShader(shaderCode,GL_FRAGMENT_SHADER);
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

		glAttachShader(shaderId, programId);

		return shaderId;
	}

	public void link(){
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
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
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
	}

	public void bind(){
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
}