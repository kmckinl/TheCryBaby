package com.kmckinley.thecrybaby;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import com.kmckinley.thecrybaby.graphics.Shader;
import com.kmckinley.thecrybaby.input.Input;
import com.kmckinley.thecrybaby.utilities.Matrix4f;
import com.kmckinley.thecrybaby.utilities.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.Timer;
import java.util.TimerTask;

import static org.lwjgl.opengl.GL13.*;

public class Main implements Runnable {

	private int width = 1280;
	private int height = 960;
	private int gameTime = 0;
	private int hour = 12;
	
	private Thread thread;
	private boolean running = true;
	
	private long window;
	
	private float green = 0.9f;
	private float blue = 1.0f;
	
	private Title title;
	private Room room;
	private Sky sky;
	private GameState gs;
	private Pause pause;
	private Dialogue dialogue;
	private Hud hud;
	private Char character;
	
	public void start() {
		running = true;
		thread = new Thread(this, "The Cry Baby");
		thread.start();
	}
	
	private void init() {	
		
		if(!glfwInit()) {
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(width, height, "The Cry Baby", NULL, NULL);
		if(window == NULL) {
			return;
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities(); //Have to tell the system what the context is
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1280, 960, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(0, 1280, 960, 0, 1, -1);
		Shader.TITLE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.TITLE.setUniform1i("tex", 1);
		
		Shader.ROOM.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.ROOM.setUniform1i("tex", 1);
		
		Shader.CHAR.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.CHAR.setUniform1i("tex", 1);
		
		Shader.CLOCK.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.CLOCK.setUniform1i("tex", 1);
		
		Shader.FLOWER.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.FLOWER.setUniform1i("tex", 1);
		
		Shader.HUD.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.HUD.setUniform1i("tex", 1);
		
		Shader.SKY.setUniformMat4f("pr_matrix", pr_matrix);
		
		Shader.PAUSE.setUniformMat4f("pr_matrix",  pr_matrix);
		Shader.PAUSE.setUniform1i("tex", 1);
		
		Shader.DIALOGUE.setUniformMat4f("pr_matrix", pr_matrix);
		Shader.DIALOGUE.setUniform1i("tex", 1);
		
		gs = new GameState();
		title = new Title();
		room = new Room();
		sky = new Sky();
		pause = new Pause();
		dialogue = new Dialogue();
		hud = new Hud();
		character = new Char();
		
	}
	
	@Override
	public void run() {
		init();

		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		long gTimer = System.currentTimeMillis();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1.0) {
				update();
				updates++;
				delta --;
			}
			
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				updates = 0;
				frames = 0;
			}
			
			if(System.currentTimeMillis() - gTimer > 200) {
				if(gs.getPlaying() && !gs.getPaused()) Clock();
				gTimer += 200;
			}
			if(glfwWindowShouldClose(window)) {
				running = false;
			}
		}
		cleanUp();
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void update() {
		glfwPollEvents();
		
		if(gs.getPlaying()) {
			room.update();
		}
		
		if(Input.keys[GLFW_KEY_ENTER]) {
			Input.keys[GLFW_KEY_ENTER] = false;
			if(gs.getTitle() && hud.getMenuOption() == 1) {
				gs.setTitle(false);
				gs.setPlaying(true);
			} else if (gs.getTitle() && hud.getMenuOption() == 2) {
				hud.update(true, 0, hud.getMenuOption());
			} else if (gs.getTitle() && hud.getMenuOption() == 3) {
				hud.update(true, 0, hud.getMenuOption());
			}
			
			if(gs.getPaused()) {
				gs.setPaused(false);
				gs.setPlaying(false);
				gs.setTitle(true);
				resetState();
			}
		}
		
		if(Input.keys[GLFW_KEY_S] | Input.keys[GLFW_KEY_DOWN]) {
			Input.keys[GLFW_KEY_S] = false;
			Input.keys[GLFW_KEY_DOWN] = false;
			if(gs.getTitle()) {
				hud.update(true, 1, 1); 
			}
			
			if(gs.getPlaying()) {
				dialogue.setActive(true);
				dialogue.setTimerTask(true);
				character.setTimerTask(true);
				character.offSet(character.getCrying(), true);
				character.timer();
			}
		}
		
		if(Input.keys[GLFW_KEY_A] | Input.keys[GLFW_KEY_LEFT]) {
			Input.keys[GLFW_KEY_A] = false;
			Input.keys[GLFW_KEY_LEFT] = false;
			if(gs.getPlaying()) {
				dialogue.setActive(true);
				dialogue.setTimerTask(true);
				character.setTimerTask(true);
				character.offSet(character.getCrying(), true);
				character.timer();
			}
		}
		
		if(Input.keys[GLFW_KEY_D] | Input.keys[GLFW_KEY_RIGHT]) {
			Input.keys[GLFW_KEY_D] = false;
			Input.keys[GLFW_KEY_RIGHT] = false;
			if(gs.getPlaying()) {
				dialogue.setActive(true);
				dialogue.setTimerTask(true);
				character.setTimerTask(true);
				character.offSet(character.getCrying(), true);
				character.timer();
			}
		}
		
		if(Input.keys[GLFW_KEY_W] | Input.keys[GLFW_KEY_UP]) {
			Input.keys[GLFW_KEY_UP] = false;
			Input.keys[GLFW_KEY_W] = false;
			if(gs.getTitle()) {
				hud.update(true, -1, 1); 
			}
			
			if(gs.getPlaying()) {
				dialogue.setActive(true);
				dialogue.setTimerTask(true);
				character.setTimerTask(true);
				character.offSet(character.getCrying(), true);
				character.timer();
			}
		}
		
		if (Input.keys[GLFW_KEY_ESCAPE]) {
			Input.keys[GLFW_KEY_ESCAPE] = false;
			if(gs.getTitle()) {
				if(hud.getMenuOption() != 1) {
					hud.update(true, 0, 1);
				}
			} else if (gs.getPaused()) {
				gs.setPaused(false);
				gs.setPlaying(true);
			} 
		}
		
		if (Input.keys[GLFW_KEY_P] | Input.keys[GLFW_KEY_ESCAPE]) {
			Input.keys[GLFW_KEY_P] = false;
			Input.keys[GLFW_KEY_ESCAPE] = false;
			if(gs.getPlaying() && !gs.getPaused()) {
				gs.setPaused(true);
			} else if (gs.getPlaying() && gs.getPaused()) {
				gs.setPaused(false);;
			}
		}
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if(gs.getTitle()) {
			title.render();
			hud.render();
		}
		if(gs.getPlaying()) {
			room.render();
			sky.render();
			character.render();
		}
		
		if(gs.getPaused()) pause.render();
		
		if(gs.getPlaying() && dialogue.getActive()) {
			dialogue.render();
			dialogue.hide();
		}

		int error = glGetError();
		if(error != GL_NO_ERROR) {
			System.out.println(error);
		}
		glfwSwapInterval(1); //May not be the best place to put this
		glfwSwapBuffers(window);
	}
	
	public void Clock() {
		gameTime++;
		room.incrementTime();
		hour = room.getHour();
		sky.update(hour, gameTime);
		
		/*if(gameTime%360 == 0) {

			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					
				}
				
			};
			
			Timer timer = new Timer("cry");
			timer.schedule(task, 5000);
		}	*/
		
		if(gameTime >= 8640) {
			//Game over state
			cleanUp();
			glfwDestroyWindow(window);
			glfwTerminate();
		}
	}
	
	//reset the game upon returning to main menu
	public void resetState() {
		gs.setPlaying(false);
		gs.setPaused(false);
		gs.setTitle(true);
		
		gameTime = 0;
		room.restart();
	}
	
	//Clean up all vao/vbo/ibo and exit system
	public void cleanUp() {
		title.cleanUp();
		room.cleanUp();
		pause.cleanUp();
		dialogue.cleanUp();
		hud.cleanUp();
		sky.cleanUp();
		character.cleanUp();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}
