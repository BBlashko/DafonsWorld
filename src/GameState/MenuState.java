package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import Entity.Animation;
import Main.Stats;
import Main.Window;
import TileMap.Background;

public class MenuState extends GameState  {
	
	private Background bg;
	private Background bg2;
	private int currentChoice;
	private String[] options = {"Start Game", 
								" Statistics", 
								"   Options", 
								"     Help",	
								"     Quit"};
	
	
	private Color titleColor;
	private Font titleFont;
	Animation animation;
	private Font font;
	
	private int cursorX;
	private int cursorY;
	
	
	
	public MenuState(GameStateManager gsm) {
		
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/mainMenuBg.png", 1);
			bg.setVector(-.1, 0);
			bg2 = new Background("/Backgrounds/mainMenuBg2.png", 1);
			bg2.setVector(0, 0);
			
			titleColor = new Color(255,215,51);
			titleFont = new Font("Century Gothic", Font.BOLD, 45);
			
			font = new Font("Arial", Font.PLAIN, 28);			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("MenuState Loaded.");
	}
	
	public void init() {}
	
	public void update() {
		bg.update();

	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		bg.draw(g);
		bg2.draw(g);
		
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Dafon's World", 450, 130);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}
			g.drawString(options[i], 535, 270 + (i *76));
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.STATSTATE);
		}
		if(currentChoice == 2) {
		
		}
		if(currentChoice == 3) {
			
		}
		if(currentChoice == 4) {
			Stats.updateStats();
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_S){
			currentChoice++;
			if(currentChoice > 4){
				currentChoice = 0;
			}
		}else if(k == KeyEvent.VK_W){
			currentChoice--;
			if(currentChoice < 0){
				currentChoice = 4;
			}
		}
		if(k == KeyEvent.VK_ENTER){
			Stats.updateStats();
			select();
		}
	}
	public void keyReleased(int k) {}
	public void mouseWheelMoved(MouseWheelEvent event) {}
	public void mouseClicked(MouseEvent event) {
		
	}
	public void mouseEntered(MouseEvent event) {
	}
	public void mouseExited(MouseEvent event) {
		
	}
	public void mousePressed(MouseEvent event) {
		if(cursorX > 466 && cursorX < 735){
			if(cursorY > 232 && cursorY < 298 || cursorY > 307  && cursorY < 372 || cursorY > 382  && cursorY < 448 || cursorY > 382  && cursorY < 448 || cursorY > 457  && cursorY < 522 || cursorY > 533    && cursorY < 598){
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
		if(cursorX > 466 && cursorX < 735){
			if(cursorY > 232 && cursorY < 298){
				currentChoice = 0;
			}if(cursorY > 307  && cursorY < 372){
				currentChoice = 1;
			}if(cursorY > 382  && cursorY < 448){
				currentChoice = 2;
			}if(cursorY > 457  && cursorY < 522){
				currentChoice = 3;
			}if(cursorY > 533    && cursorY < 598){
				currentChoice = 4;
			}
			
		}
	}
}
