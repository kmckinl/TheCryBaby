package com.kmckinley.thecrybaby;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.graphics.Texture;
import com.kmckinley.thecrybaby.graphics.VertexArray;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

public class Room {
	private VertexArray room;
	private Texture roomTex;
	
	private Clock clock1, clock2, clock3, clock4;
	private Flower flower;
	
	private int gameTime, day, hour, hourTen, hourOne, minuteTen, minuteOne;
	
	public Room() {
		this.gameTime = 0;
		this.day = 1;
		this.hour = 12;
		this.hourTen = 1;
		this.hourOne = 2;
		this.minuteTen = 0;
		this.minuteOne = 0;
		
		float[] vertices = new float[] {
			0.0f, 0.0f, 0.0f,
			1280.0f, 0.0f, 0.0f,
			1280.0f, 960.0f, 0.0f,
			0.0f, 960.0f, 0.0f
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
		
		room = new VertexArray(vertices, indices, tcs);
		
		roomTex = new Texture("res/room.png");
		
		flower = new Flower();
		
		clock1 = new Clock(hourTen, 0);
		clock2 = new Clock(hourOne, 1);
		clock3 = new Clock(minuteTen, 2);
		clock4 = new Clock(minuteOne, 3);
	}
	
	public void update() {
		clock1.update(hourTen);
		clock2.update(hourOne);
		clock3.update(minuteTen);
		clock4.update(minuteOne);
	}
	
	public void incrementTime() {
		gameTime++;
		
		if(gameTime%10 != 0) minuteOne++;
		else minuteOne = 0;
		
		if(gameTime%10 == 0) {
			if(minuteTen < 5) minuteTen++;
			else minuteTen = 0;
		}
		
		if(gameTime%60 == 0) {
			if(hour == 23) hour = 0;
			else hour++;
		}
		
		if(gameTime%60 == 0 && hour == 0) {
			day++;
			flower.update(day);
		}
		
		hourDisplay();
	}
	
	public void hourDisplay() {
		switch(hour) {
		case 23:
			hourTen = 1;
			hourOne = 1;
			break;
		case 22:
			hourTen = 1;
			hourOne = 0;
			break;
		case 0:
			hourTen = 1;
			hourOne = 2;
			break;
		case 12:
			hourTen = 1;
			hourOne = 2;
			break;
		case 11:
			hourTen = 1;
			hourOne = 1;
			break;
		case 10:
			hourTen = 1;
			hourOne = 0;
			break;
		default:
			hourTen = 0;
			if(hour > 12) hourOne = hour - 12;
			else hourOne = hour;
			break;
		}
		
		
	}

	
	public int getHour() {
		return hour;
	}
	
	public void render() {
		roomTex.bind();
		Shader.ROOM.enable();
		room.bind();
		Shader.ROOM.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.0f)));
		room.draw();
		
		Shader.ROOM.disable();
		roomTex.unbind();
		
		clock1.render();
		clock2.render();
		clock3.render();
		clock4.render();
		flower.render();
	}	
	
	public void restart() {
		this.gameTime = 0;
		this.day = 1;
		this.hour = 12;
		this.hourTen = 1;
		this.hourOne = 2;
		this.minuteTen = 0;
		this.minuteOne = 0;
	}
	
	public void cleanUp() {
		room.cleanUp();
		clock1.cleanUp();
		clock2.cleanUp();
		clock3.cleanUp();
		clock4.cleanUp();
		flower.cleanUp();
	}
}
