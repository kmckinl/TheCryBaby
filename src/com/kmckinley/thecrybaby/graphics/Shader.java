package com.kmckinley.thecrybaby.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.ShaderUtils;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Shader {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader TITLE, ROOM, CHAR, CLOCK, FLOWER, SKY, HUD, PAUSE, TRANSPARENT, DIALOGUE;
	
	private boolean enabled = false;
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	public Shader(String vertex, String fragment) {
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll() {
		TITLE = new Shader("shaders/title.vs", "shaders/title.fs");
		ROOM = new Shader("shaders/vertex.vs", "shaders/fragment.fs");
		CHAR = new Shader("shaders/char.vs", "shaders/char.fs");
		CLOCK = new Shader("shaders/char.vs", "shaders/char.fs");
		FLOWER = new Shader("shaders/flower.vs", "shaders/flower.fs");
		SKY = new Shader("shaders/sky.vs", "shaders/sky.fs");
		HUD = new Shader("shaders/vertex.vs", "shaders/fragment.fs");
		PAUSE = new Shader("shaders/vertex.vs", "shaders/fragment.fs");
		TRANSPARENT = new Shader("shaders/pause.vs", "shaders/pause.fs");
		DIALOGUE = new Shader("shaders/vertex.vs", "shaders/fragment.fs");
	}
	
	public int getUniform(String name) {
		if(locationCache.containsKey(name)) {
			return locationCache.get(name);
		}
		
		int result = glGetUniformLocation(ID, name);
		
		if(result == -1) {
			System.err.println("Could not find uniform variable " + name + ".");
		} else {
			locationCache.put(name, result);
		}
		return result;
	}
	
	public void setUniform1i(String name, int value) {
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value) {
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y) {
		if(!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f vector) {
		if(!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniform4f(String name, Vector3f vector, float alpha) {
		if(!enabled) enable();
		glUniform4f(getUniform(name), vector.x, vector.y, vector.z, alpha);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix) {
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void enable() {
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable() {
		glUseProgram(0);
		enabled = false;
	}
}
