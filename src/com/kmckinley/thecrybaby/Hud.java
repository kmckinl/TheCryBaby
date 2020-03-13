package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Hud {
	private VertexArray hud;
	private Texture hudTex;
	
	//private boolean[] hover = new boolean[3];
	private boolean start = true;
	private boolean controls = false;
	private boolean credits = false;
	
	private int menuOption = 1;
	
	public Hud() {
		/*for (int i = 0; i < hover.length; i++) {
			hover[i] = false;
		}*/
		float[] vertices = new float[] {
				0.0f, 0.0f, -0.01f,
				1280.0f, 0.0f, -0.01f,
				1280.0f, 960.0f, -0.01f,
				0.0f, 960.0f, -0.01f
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
		
		hud = new VertexArray(vertices, indices, tcs);
		hudTex = new Texture("res/hud/startselect.png");
	}
	
	public int getMenuOption() {
		return this.menuOption;
	}
	
	public void update(boolean state, int change, int menuScene) {
		if (state) {
			this.menuOption += change;
			if(menuOption < 1) menuOption = 3;
			else if (menuOption > 3) menuOption = 1;
			
			if(menuScene == 1) {
				if(menuOption == 1) {
					hudTex.reload("res/hud/startselect.png");
				} else if(menuOption == 2) {
					hudTex.reload("res/hud/controlsselect.png");
				} else if(menuOption == 3) {
					hudTex.reload("res/hud/creditsselect.png");
				}
			} else if (menuScene == 2) {
				hudTex.reload("res/hud/controls.png");
			} else if (menuScene == 3) {
				hudTex.reload("res/hud/credits.png");
			}
		}
		
		hudTex.bind();
		hudTex.update();
		hudTex.unbind();
	}
	
	public void render() {
		hudTex.bind();
		Shader.HUD.enable();
		hud.bind();
		Shader.HUD.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		hud.draw();
		
		Shader.HUD.disable();
		hudTex.unbind();
	}
	
	public int getHudState() {
		return this.getHudState();
	}
	
	public void setHudState(int state) {
		if(state == 0) {
			start = true;
			controls = false;
			credits = false;
		} else if (state == 1) {
			start = false;
			controls = true;
			credits = false;
		} else if (state == 2) {
			start = false;
			controls = false;
			credits = true;
		}
	}
	
	public boolean getStart() {
		return this.start;
	}
	
	public void setStart(boolean start) {
		this.start = start;
	}
	
	public boolean getCredits() {
		return this.credits;
	}
	
	public void setCredits(boolean credits) {
		this.credits = credits;
	}
	
	public boolean getControls() {
		return this.controls;
	}
	
	public void setControls(boolean controls) {
		this.controls = controls;
	}
	
	public void cleanUp() {
		hud.cleanUp();
	}
}
