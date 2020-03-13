package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Sky {
	private VertexArray sky;
	private Vector3f color;
	
	public Sky() {
		
		color = new Vector3f(0.0f, 0.9f, 1.0f);
		
		float[] vertices = new float[] {
			520.0f, 120.0f, 0.1f,
			760.0f, 120.0f, 0.1f,
			760.0f, 360.0f, 0.1f,
			520.0f, 360.0f, 0.1f
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
		
		sky = new VertexArray(vertices, indices, tcs, true);

	}
	
	public void update(int hour, int gameTime) {
		if (hour >= 16 && hour <= 20) {
			if(gameTime%60 == 0) {
				color.y -= 0.1f;
				color.z -= 0.1f;
			}
		} 
		
		if(hour >= 5 && hour < 12) {
			if(gameTime%60 == 0) {
				color.y += 0.1f;
				color.z += 0.1f;
			}
		}
	}
	
	public void render() {
		Shader.SKY.enable();
		sky.bind();
		Shader.SKY.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.1f)));
		Shader.SKY.setUniform4f("u_Color", color, 1.0f);
		sky.draw();
		Shader.SKY.disable();
	}
	
	public void cleanUp() {
		sky.cleanUp();
	}
}
