package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import Entity.Animation;
import Entity.Player;
import Main.Stats;
import TileMap.Background;

public class RestartState extends GameState{
	
	private Player player;
	Background bg;
	private String[] options = {" Respawn", "Main Menu", "     Quit"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private int currentChoice = 0;
	
	private int cursorX;
	private int cursorY;
	
	public RestartState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		
		try {
			bg = new Background("/Backgrounds/RestartState.png", 1);
			bg.setVector(0, 0);
			
			titleColor = new Color(255,215,51);
			titleFont = new Font("Century Gothic", Font.BOLD, 38);
			
			font = new Font("Arial", Font.PLAIN, 28);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void init(){}	
	public void update(){
		
	}
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
		if(currentChoice == 2) {
			Stats.updateStats();
			System.exit(0);
			
		}
		
	}
	public void draw(java.awt.Graphics2D g){
		
		// draw bg
		bg.draw(g);
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
		g.drawString("Play Again?", 492, 250);
		
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 532, 303+ (i *73));
		}
	}
	public void keyPressed(int k){
		if(k == KeyEvent.VK_W){
			currentChoice--;
			if(currentChoice < 0){
				currentChoice = 2;
			}
		}
		if(k == KeyEvent.VK_S){
			currentChoice++;
			if(currentChoice >2){
				currentChoice = 0;
			}
		}
		if(k == KeyEvent.VK_ENTER){
			select();
		}
	}
	public void keyReleased(int k){}

	public void mouseWheelMoved(MouseWheelEvent event) {
		
	}
	public void mouseClicked(MouseEvent event) {
		
	}
	public void mouseEntered(MouseEvent event) {
		
	}
	public void mouseExited(MouseEvent event) {
		
	}
	public void mousePressed(MouseEvent event) {
		if(cursorX > 468 && cursorX < 732){
			if(cursorY > 266 && cursorY < 324 || cursorY > 337 && cursorY < 394 || cursorY > 410 && cursorY < 467){
				select();
			}
		}
	}
	public void mouseReleased(MouseEvent event) {
		
	}
	public void mouseDragged(MouseEvent event) {
		
	}
	public void mouseMoved(MouseEvent event) {
		cursorX = event.getX();
		cursorY = event.getY();
		
		if(cursorX > 468 && cursorX < 732){
			if(cursorY > 266 && cursorY < 324){
				currentChoice = 0;
			}
			if(cursorY > 337 && cursorY < 394){
				currentChoice = 1;
			}
			if(cursorY >  410 && cursorY < 467 ){
				currentChoice = 2;
			}
		}
	}
}
