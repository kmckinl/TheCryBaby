package com.kmckinley.thecrybaby;

public class GameState {
	private boolean title, playing, paused, gameover;
	
	public GameState() {
		this.title = true;
		this.playing = false;
		this.paused = false;
		this.gameover = false;
	}
	
	public boolean getTitle() {
		return title;
	}
	
	public void setTitle(boolean state) {
		this.title = state;
	}
	
	public boolean getPlaying() {
		return playing;
	}
	
	public void setPlaying(boolean state) {
		this.playing = state;
	}
	
	public boolean getPaused() {
		return paused;
	}
	
	public void setPaused(boolean state) {
		this.paused = state;
	}
	
	public boolean getGameOver() {
		return gameover;
	}
	
	public void setGameOVer(boolean state) {
		this.gameover = state;
	}
}
