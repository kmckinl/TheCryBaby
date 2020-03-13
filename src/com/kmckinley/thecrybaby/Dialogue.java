package com.kmckinley.thecrybaby;

import java.util.Timer;
import java.util.TimerTask;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Dialogue {
	private VertexArray dialogue;
	private Texture dialogueTex;
	
	private boolean activate = false;
	private boolean timerTask = false;
	
	private Timer timer;
	
	public Dialogue() {
		float[] vertices = new float[] {
			0.0f, 0.0f, -0.5f,
			1280.0f, 0.0f, -0.5f,
			1280.0f, 960.0f, -0.5f,
			0.0f, 960.0f, -0.5f
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
		
		dialogue = new VertexArray(vertices, indices, tcs);
		dialogueTex = new Texture("res/hud/moving.png");
		
		timer = new Timer();
	}
	
	public boolean getActive() {
		return this.activate;
	}
	
	public void setActive(boolean active) {
		this.activate = active;
	}
	
	public void setTimerTask(boolean newTask) {
		this.timerTask = newTask;
	}
	
	public void hide() {
		if(timerTask) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					activate = false;
				}			
			};
			timer = new Timer();
			timer.schedule(task, 4000);
			timerTask = false;
		}
		
	}
	
	public void update() {
		
	}
	
	public void render() {
		dialogueTex.bind();
		Shader.DIALOGUE.enable();
		dialogue.bind();
		
		Shader.DIALOGUE.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		dialogue.draw();
		
		Shader.DIALOGUE.disable();
		dialogueTex.unbind();
	}
	
	public void cleanUp() {
		dialogue.cleanUp();
	}
}
