package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Clock {
	private VertexArray clock;
	private Texture clockTex;
	
	private int digit, pos;
	private float xCoord = 0.0f;
	private float xOffset = 0.0f;
	
	private float width = 15.0f;
	
	public Clock(int digit, int pos) {
		this.digit = digit;
		this.pos = pos;
		
		if(pos >= 2) {
			xCoord = 5.0f;
		}
		
		float[] vertices = new float[] {
			(745.0f + xCoord + pos*width), 520.0f, -0.2f,
			(760.0f + xCoord + pos*width), 520.0f, -0.2f,
			(760.0f + xCoord + pos*width), 540.0f, -0.2f,
			(745.0f + xCoord + pos*width), 540.0f, -0.2f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			xOffset, 0,
			xOffset + 0.1f, 0,
			xOffset + 0.1f, 1,
			xOffset, 1
		};
		
		clock = new VertexArray(vertices, indices, tcs, true);
		clockTex = new Texture("res/digits/digits.png");
	}
	
	public void update(int newDigit) {
		this.digit = newDigit;
		xOffset = (float) digit/10;
	}
	
	public void render() {
		clockTex.bind();
		Shader.CLOCK.enable();
		clock.bind();
		Shader.CLOCK.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, -0.2f)));
		Shader.CLOCK.setUniform2f("texOffset", xOffset, 0);
		clock.draw();
		
		Shader.CLOCK.disable();
		clockTex.unbind();
		
	}
	
	public void cleanUp() {
		clock.cleanUp();
	}
}
