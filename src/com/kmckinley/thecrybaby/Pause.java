package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Pause {
	private VertexArray pause, transparent;
	private Texture pauseTex;
	
	public Pause() {
		float[] vertices = new float[] {
			0.0f, 0.0f, -0.4f,
			1280.0f, 0.0f, -0.4f,
			1280.0f, 960.0f, -0.4f,
			0.0f, 960.0f, -0.4f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		
		pause = new VertexArray(vertices, indices, tcs, true);
		transparent = new VertexArray(6);
		pauseTex = new Texture("res/hud/quit.png");
	}
	
	public void render() {
		pauseTex.bind();
		Shader.PAUSE.enable();
		pause.bind();
		Shader.PAUSE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		pause.draw();
		
		Shader.PAUSE.disable();
		pauseTex.unbind();
		
		Shader.TRANSPARENT.enable();
		transparent.render();
		Shader.TRANSPARENT.disable();
	}
	
	public void cleanUp() {
		pause.cleanUp();
		transparent.cleanUp();
	}
}
