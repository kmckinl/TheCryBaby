package com.kmckinley.thecrybaby;

import java.util.Timer;
import java.util.TimerTask;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Char {
	private int health = 100;
	
	private VertexArray character;
	private Texture charTex;
	
	private boolean crying = false;
	private boolean open = false;
	private int state = 1;
	private int dontCry = 0;
	
	
	private float width = 0.0f;
	private float height = 0.0f;
	
	private float xoff = 0.0f;
	private float yoff = 0.0f;
	
	private boolean timerTask = false;
	
	private Timer timer;
	
	public Char() {
		float[] vertices = new float[] {
			0.0f, 0.0f, -0.1f,
			200.0f, 0.0f, -0.1f,
			200.0f, 450.0f, -0.1f,
			0.0f, 450.0f, -0.1f
		};
		
		byte[] indices = new byte[] {
			0, 1, 2,
			2, 3, 0
		};
		
		float[] tcs = new float[] {
			width, height,
			width + 0.25f, height,
			width + 0.25f, height + 0.333f,
			width, height + 0.333f
		};
		
		character = new VertexArray(vertices, indices, tcs, true);
		charTex = new Texture("res/Character/character.png");
		
		timer = new Timer();
	}
	
	public void setTimerTask(boolean newTask) {
		this.timerTask = newTask;
	}
	
	public void timer() {
		if(timerTask) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					width -= xoff;
					height -= yoff;
				}
			};
			timer = new Timer();
			timer.schedule(task, 4000);
			timerTask = false;
		}
		
	}
	
	public void offSet(boolean crying, boolean open) {
		if (open && !crying) {
			width += 0.5f;
			xoff = 0.5f;
		} else if (open && crying) {
			width += 0.75f;
			xoff = 0.75f;
		} else if (!open && crying) {
			width += 0.25f;
			xoff = 0.25f;
		}
	}
	
	public void render() {
		Shader.CHAR.enable();
		Shader.CHAR.setUniformMat4f("ml_matrix", Matrix4f.translate(new Vector3f(350.0f, 400.0f, -0.2f)));
		Shader.CHAR.setUniform2f("texOffset", width, height);

		charTex.bind();
		character.render();
		Shader.CHAR.disable();

		charTex.unbind();
		
		if(state == 1 && health <= 66) {
			height += 0.333f;
			state += 1;
		}
		
		if(state == 2 && health <= 33) {
			height += 0.333f;
			state += 1;
		}
	}
	
	public void dontCry(int increment) {
		this.dontCry += increment;
	}
	
	public int getCry() {
		return this.dontCry;
	}
	
	public boolean getCrying() {
		return this.crying;
	}
	
	public void setCrying(boolean newCry) {
		this.crying = newCry;
	}
	
	public void checkCry() {
		
	}
	
	public void setOpen(boolean newOpen) {
		this.open = newOpen;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int decrement) {
		this.health -= decrement;
	}
	
	public void cleanUp() {
		character.cleanUp();
	}
}
