package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Flower {
	private VertexArray flower;
	private Texture flowerTex;
	
	private float xOffset = 0.0f;
	
	public Flower() {
		float[] vertices = new float[] {
			580.0f, 240.0f, -0.3f,
			680.0f, 240.0f, -0.3f,
			680.0f, 360.0f, -0.3f,
			580.0f, 360.0f, -0.3f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
				xOffset, 0,
				xOffset + 0.1667f, 0,
				xOffset + 0.1667f, 1,
				xOffset, 1
		};
		
		flower = new VertexArray(vertices, indices, tcs, true);
		flowerTex = new Texture("res/flowers/flower.png");
	}
	
	public void update(int day) {
		xOffset = (float) (day - 1) / 6;
	}
	
	public void render() {
		flowerTex.bind();
		Shader.FLOWER.enable();
		flower.bind();
		Shader.FLOWER.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		Shader.FLOWER.setUniform2f("texOffset", xOffset, 0);
		flower.draw();
		
		Shader.FLOWER.disable();
		flowerTex.unbind();
	}
	
	public void cleanUp() {
		flower.cleanUp();
	}
}
