	package GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Main.Stats;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int numStates = 5;
	public static final int MENUSTATE = 0;
	public static final int RESTARTSTATE = 1;
	public static final int STATSTATE = 2;
	public static final int LEVEL1STATE = 3;
	public static final int LEVEL2STATE = 4;
	
	public GameStateManager() {
		
		gameStates = new GameState[numStates];
		
		
		currentState = MENUSTATE;
		loadState(currentState);
	}
	private void loadState(int state){
		if(state == MENUSTATE){
			gameStates[state] = new MenuState(this);
		}
		if(state == RESTARTSTATE){
			gameStates[state] = new RestartState(this);
		}
		if(state == STATSTATE){
			gameStates[state] = new StatState(this);
		}
		if(state == LEVEL1STATE){
			gameStates[state] = new Level1State(this);
		}
	}
	private void unloadState(int state){
		gameStates[state] = null;
	}
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		//gameStates[currentState].init();
	}
	public int getState(){
		return currentState;
	}
	public void update() {
		
		if(gameStates[currentState] != null){
			gameStates[currentState].update();
		}else return;
	}
	public void draw(java.awt.Graphics2D g) {
		if(gameStates[currentState] != null){
			gameStates[currentState].draw(g);
		} else return;
	}
	public void keyPressed(int k) {
		gameStates[currentState].keyPressed(k);
	}
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	public void mouseWheelMoved(MouseWheelEvent event) {
		gameStates[currentState].mouseWheelMoved(event);
		
	}
	public void mouseClicked(MouseEvent event) {
		gameStates[currentState].mouseClicked(event);
		
	}
	public void mouseEntered(MouseEvent event) {
		gameStates[currentState].mouseEntered(event);
		
	}
	public void mouseExited(MouseEvent event) {
		gameStates[currentState].mouseExited(event);
		
	}
	public void mouseReleased(MouseEvent event) {
		gameStates[currentState].mouseReleased(event);
		
	}
	public void mousePressed(MouseEvent event) {
		gameStates[currentState].mousePressed(event);
		
	}
	public void mouseDragged(MouseEvent event) {
		gameStates[currentState].mouseDragged(event);
		
	}
	public void mouseMoved(MouseEvent event) {
		gameStates[currentState].mouseMoved(event);
		
	}

	
}
