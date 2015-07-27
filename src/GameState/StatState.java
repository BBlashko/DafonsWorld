package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import Entity.Box;
import Entity.Enemy;
import Entity.HUD;
import Entity.Platform;
import Entity.Player;
import Entity.RockPlatform;
import Entity.Enemies.Laser;
import Main.ControlPanel;
import Main.Stats;
import TileMap.Background;
import TileMap.TileMap;

public class StatState extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Background pausebg;
	private Player player;
	private HUD hud;
	public boolean checkEye = false;
	
	private ArrayList<Box> boxes;
	private ArrayList<Enemy> enemies;
	private ArrayList<Laser> lasers;
	private ArrayList<Platform> platforms;
	private ArrayList<RockPlatform> rockPlatforms;
	
	private boolean paused = false;
	private Font font;
	private Font pauseFont;
	private int currentChoice = 0;
	private String [] options = {"     Resume", "Commit Suicide", "         help", "    Main Menu"};;
	private int cursorX = 0;
	private int cursorY = 0;
	
	public StatState(GameStateManager gsm){
		this.gsm = gsm;
		init();
		System.out.println("StatState initialized");
	}
	public void init(){
		tileMap = new TileMap(30);
		tileMap.loadTiles("/TileSet/Tileset_1.png");
		tileMap.loadMap("/TileMaps/StatsMap.txt");
		tileMap.setPosition(0,0);
		
		bg = new Background("/Backgrounds/StatStateBG.png", 0);
		//pausebg = new Background("/Backgrounds/Blank.png",1);
		player = new Player(tileMap);
		player.setPosition(40,40);
		
		enemies = new ArrayList<Enemy>();
		hud = new HUD(player);
		populateBoxes();
	}
	private void populateBoxes() {
		
		boxes = new ArrayList<Box>();
		
		Box b;
		Point[] boxPoints = new Point[] {	new Point(100,100), new Point(100, 100), new Point(100, 100)};

		
		for(int i = 0; i < boxPoints.length; i++) {
			b = new Box(tileMap);
			b.setPosition(boxPoints[i].x, boxPoints[i].y);
			boxes.add(b);
			
		}
		
	}
	public void update() {
		if(!paused){
			
			if(player.gety()  > ControlPanel.HEIGHT + 60){
				player.setDead(true);
				
			}
			player.update();
			hud.update();
			tileMap.setPosition(ControlPanel.WIDTH / 2 - player.getx(),
					ControlPanel.HEIGHT / 2 - player.gety());
			
			bg.setPosition(tileMap.getx(), tileMap.gety());
			
			for (int i = 0; i < boxes.size(); i++) {
				Box b = boxes.get(i);
				b.update();
			}
			player.checkBoxes(boxes);
			if (player.getDead()) {
				//Stats.updateTotalDeaths();
				//Stats.updateStats();
				gsm.setState(1);

			}
			
		}
	}
	public void changeImage(){
		pausebg = new Background("/Backgrounds/Blank.png",1);
	}
	public void draw(Graphics2D g) {
		if(!paused){
			bg.draw(g);
			tileMap.draw(g);
			player.draw(g);
			hud.draw(g);
			for(int i = 0; i < boxes.size(); i++) {
				boxes.get(i).draw(g);
			}
		}else{
			pausebg.draw(g);
			font = new Font("Arial", Font.PLAIN, 28);
			pauseFont = new Font("Verdana", Font.BOLD, 54);
			g.setColor(Color.WHITE);
			g.setFont(pauseFont);
			g.drawString("PAUSED", 475, 200);
			
			g.setFont(font);
			for(int i = 0; i < 4; i++) {
				
				if(i == currentChoice) {
					g.setColor(Color.WHITE);
				}
				else {
					g.setColor(Color.RED);
				}
				g.drawString(options[i], 502, 302 + (i *70));
			}
		
		}
	}
	public void keyPressed(int k) {
		if(!paused){
			if (k == KeyEvent.VK_A) {
				player.setLeft(true);
				for (int i = 0; i < boxes.size(); i++) {
					Box b = boxes.get(i);
					b.setLeft(true);

				}
			}
			if (k == KeyEvent.VK_D) {
				player.setRight(true);
				for (int i = 0; i < boxes.size(); i++) {
					Box b = boxes.get(i);
					b.setRight(true);
				}
			}
			if (k == KeyEvent.VK_W)
				player.setUp(true);
			if (k == KeyEvent.VK_S)
				player.setDown(true);
			if (k == KeyEvent.VK_W)
				player.setJumping(true);
			if (k != KeyEvent.VK_A || k != KeyEvent.VK_D)
				player.setIdle(true);

			if (k == KeyEvent.VK_1) {
				player.setSword(true);
				player.setWeaponScroll(1);
			}
			if (k == KeyEvent.VK_7) {
				player.setRock(true);
				player.setWeaponScroll(7);
			}
			if (k == KeyEvent.VK_2) {
				player.setPistol(true);
				player.setWeaponScroll(2);
			}
			if (k == KeyEvent.VK_3) {
				player.setShotgun(true);
				player.setWeaponScroll(3);
			}
			if (k == KeyEvent.VK_4) {
				player.setMachineGun(true);
				player.setWeaponScroll(4);
			}
			if (k == KeyEvent.VK_5) {
				player.setBomb(true);
				player.setWeaponScroll(5);
			}
			if (k == KeyEvent.VK_6) {
				player.setFlameBow(true);
				player.setWeaponScroll(6);
			}
			if (k == KeyEvent.VK_8) {
				player.setHealthKit(true);
				player.setWeaponScroll(8);
			}
			if (k == KeyEvent.VK_SPACE) {
				player.attack();
				player.setSwordAttack();
			}
			if (k == KeyEvent.VK_Q)
				player.updateWeaponScroll(-1);
			if (k == KeyEvent.VK_E)
				player.updateWeaponScroll(1);
			if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_P) {
				paused = true;
			}
		}
		else{
			if(k == KeyEvent.VK_W){
				currentChoice--; 
				if(currentChoice < 0){
					currentChoice = 3;
				}
			}
			if(k == KeyEvent.VK_S){
				currentChoice++; 
				if(currentChoice > 3){
					currentChoice = 0;
				}
			}
			if(k == KeyEvent.VK_ENTER){
				select();
			}
		}
	
	}
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A) {
			player.setLeft(false);
			for(int i = 0; i < boxes.size(); i++){
				Box b = boxes.get(i);
				b.setLeft(false);
				
			}
		}
		if(k == KeyEvent.VK_D){
			player.setRight(false);
			for(int i = 0; i < boxes.size(); i++){
				Box b = boxes.get(i);
				b.setRight(false);
				
			}
		
		}
		
		if(k == KeyEvent.VK_W) player.setUp(false);
		if(k == KeyEvent.VK_S) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
	}
	public void select() { 
		if (currentChoice == 0) {
			paused = false;
		}
		if (currentChoice == 1) {
			paused = false;
			changeImage();
			player.setDead(true);
			Stats.updateTotalDeaths();
			Stats.updateStats();
		}
		if (currentChoice == 2) {
			paused = false;
		}
		if (currentChoice == 3) {
			paused = false;
			
		
			gsm.setState(0);
		}
	}
	public void mouseWheelMoved(MouseWheelEvent event) {
		player.updateWeaponScroll(event.getWheelRotation());
	}
	public void mouseClicked(MouseEvent event) {
		
	}
	public void mouseEntered(MouseEvent event) {
		
	}
	public void mouseExited(MouseEvent event) {
		
	}
	public void mousePressed(MouseEvent event) {
		if(paused){
			if(cursorX > 467 && cursorX < 732){
				if(cursorY > 264 && cursorY < 320 || cursorY > 334  && cursorY < 391 || cursorY > 404  && cursorY < 461 || cursorY > 474  && cursorY < 531){
					select();
					
				}
			}
		}
	}
	public void mouseReleased(MouseEvent event) {
		
	}
	public void mouseDragged(MouseEvent event) {
		
	}
	public void mouseMoved(MouseEvent event) {		
		if(paused){
			cursorX = event.getX();
			cursorY = event.getY();
			if(cursorX > 467 && cursorX < 732){
				if(cursorY > 264 && cursorY < 320){
					currentChoice = 0;
				}if(cursorY > 334  && cursorY < 391){
					currentChoice = 1;
				}if(cursorY > 404  && cursorY < 461){
					currentChoice = 2;
				}if(cursorY > 474  && cursorY < 531){
					currentChoice = 3;
				}
				
			}
			}
	}
}
