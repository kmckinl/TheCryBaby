package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Title {
	
	private VertexArray title;
	private Texture titleTex;
	
	public Title() {
		float[] vertices = new float[] {
			  0.0f,   0.0f, 0.0f,
			1280.0f,   0.0f, 0.0f,
			1280.0f, 960.0f, 0.0f,
			  0.0f, 960.0f, 0.0f,
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
		
		title = new VertexArray(vertices, indices, tcs);
		titleTex = new Texture("res/title/titlebackground.jpg");
		
	}
	
	public void render() {
		titleTex.bind();
		Shader.TITLE.enable();
		title.bind();
		Shader.TITLE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		title.draw();
		
		Shader.TITLE.disable();
		titleTex.unbind();
	}
	
	public void cleanUp() {
		title.cleanUp();
	}
}
