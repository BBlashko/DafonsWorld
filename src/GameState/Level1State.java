package GameState;

import Main.ControlPanel;
import Main.Stats;
import TileMap.*;
import Entity.*;
import Entity.Enemies.Eye;
import Entity.Enemies.FlameGuy;
import Entity.Enemies.Laser;
import Entity.Enemies.Snake;
import Audio.AudioOut;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	private Background pausebg;
	private boolean paused = false;

	private Player player;
	private Box box;
	private HUD hud;
	private AudioOut audio;
	private Platform platform;
	private Rock rock;
	private RockPlatform rockPlatform;
	
	private ArrayList<Box> boxes;
	private ArrayList<Enemy> enemies;
	private ArrayList<Laser> lasers;
	private ArrayList<Platform> platforms;
	private ArrayList<RockPlatform> rockPlatforms;
	
	
	private Font  pauseFont;
	private Font font;
	private int currentChoice = 0;
	private String [] options = {"     Resume", "Commit Suicide", "         help", "    Main Menu"};
	private int cursorX;
	private int cursorY;
	//private ArrayList<Explosion> explosions;
	
	
	
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		System.out.println("Level1State initialized");
	}
	
	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/TileSet/Tileset_1.png");
		tileMap.loadMap("/TileMaps/Level_1...txt");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/Level1BG.png", 0.1);
		bg.setVector(-.025, 0);
		pausebg = new Background("/Backgrounds/PauseState.png", 1);
		
		player = new Player(tileMap);
		player.setPosition(150, 1300);

		hud = new HUD(player);
		enemies = new ArrayList<Enemy>();
		populateBoxes();
		populateEnemies();
		populatePlatforms();
		populateRockPlatforms();
		
		audio = new AudioOut("/Music/POL cave story short.mp3");
		//audio.loop();
		
		
	//	explosions = new ArrayList<Explosion>();
		
		
		System.out.println("Level 1 initialization... Success");
	}
	private void populateRockPlatforms(){
		rockPlatforms = new ArrayList<RockPlatform>();
		Point rockplatformPoints[] = new Point[] {new Point(300, 1000),new Point(300, 1000),new Point(350, 960),new Point(400, 9000),new Point(450, 860)};
		
		
		for(int i =0; i < rockplatformPoints.length; i++){
			rockPlatform = new RockPlatform(tileMap);
			rockPlatform.setPosition(rockplatformPoints[i].x, rockplatformPoints[i].y);
			rockPlatforms.add(rockPlatform);
		}
		
	}
	private void populatePlatforms(){
		platforms = new ArrayList<Platform>();
		Point platformPoints[] = new Point[] {new Point(400, 1300), new Point(300, 1230), new Point(400, 1160), new Point(350, 1090)};
		
		
		for(int i =0; i < platformPoints.length; i++){
			platform = new Platform(tileMap);
			platform.setPosition(platformPoints[i].x, platformPoints[i].y);
			platforms.add(platform);
		}
		
	}
	private void populateEnemies() {
		
		enemies = new ArrayList<Enemy>();
		lasers = new ArrayList<Laser>();
		
		FlameGuy fg;
		Point[] flameGuyPoints = new Point[] {new Point(50, 1200)};
		for(int i = 0; i < flameGuyPoints.length; i++) {
			fg = new FlameGuy(tileMap);
			fg.setPosition(flameGuyPoints[i].x, flameGuyPoints[i].y);
			enemies.add(fg);
		}
		
		Laser l;
		Point[] laserPoints = new Point[] { new Point(245, 1100)};
		
	
		l = new Laser(tileMap, true, 250, 0);			
		l.setPosition(laserPoints[0].x, laserPoints[0].y);
		lasers.add(l);
		Snake sn;
		
		Point[] snakePoints = new Point[] {	/*new Point(50, 1055)*/};
		

		for(int i = 0; i < snakePoints.length; i++) {
			sn = new Snake(tileMap);
			sn.setPosition(snakePoints[i].x, snakePoints[i].y);
			enemies.add(sn);
		}
		Eye e;
		Point[] points = new Point[] {	new Point(200, 1000)};
	
		for(int i = 0; i < points.length; i++) {
			e = new Eye(tileMap);
			e.setPosition(points[i].x, points[i].y);
			enemies.add(e);
		}
		
	}
	private void populateBoxes() {
		
		boxes = new ArrayList<Box>();
		
		Box b;
		Point[] boxPoints = new Point[] {	new Point(315, 700), new Point(1050, 970), new Point(1080, 1100), new Point(150, 1200), new Point(1000, 1000)};

		
		for(int i = 0; i < boxPoints.length; i++) {
			b = new Box(tileMap);
			b.setPosition(boxPoints[i].x, boxPoints[i].y);
			boxes.add(b);
			
		}
		
	}
	public void update() {
		
		if(!paused){
			// update player
			player.update();
			hud.update();
			tileMap.setPosition(ControlPanel.WIDTH / 2 - player.getx(),
					ControlPanel.HEIGHT / 2 - player.gety());

			// set background
			bg.setPosition(tileMap.getx(), tileMap.gety());

			for (int i = 0; i < boxes.size(); i++) {
				Box b = boxes.get(i);
				b.update();
			}
			// update platforms
			for (int i = 0; i < platforms.size(); i++) {
				Platform p = platforms.get(i);

				p.update();
			}
			// update rockplatforms
			for (int i = 0; i < rockPlatforms.size(); i++) {
				RockPlatform rp = rockPlatforms.get(i);

				rp.update();
				if (rp.getBottomLeft() || rp.getBottomLeft()) {
					rockPlatforms.remove(i);
					i--;
				}
			}

			// attack enemies
			player.checkAttack(enemies, lasers);
			player.checkBoxes(boxes);
			player.checkPlatforms(platforms);
			player.checkRockPlatforms(rockPlatforms);

			// update all enemies
			for (int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				e.update();
				if (e.isDead()) {
					enemies.remove(i);
					i--;

					// explosions.add(
					// new Explosion(e.getx(), e.gety()));
				}
			}
			for (int i = 0; i < lasers.size(); i++) {
				Laser ls = lasers.get(i);
				ls.update();
				if (ls.isDead()) {
					lasers.remove(i);
					i--;

					// explosions.add(
					// new Explosion(e.getx(), e.gety()));
				}
			}

			/*
			 * // update explosions for(int i = 0; i < explosions.size(); i++) {
			 * explosions.get(i).update(); if(explosions.get(i).shouldRemove())
			 * { explosions.remove(i); i--; } }
			 */
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
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw box
		for(int i = 0; i < boxes.size(); i++) {
			boxes.get(i).draw(g);
		}
		//draw platforms
		for(int i = 0; i < platforms.size(); i++){
			platforms.get(i).draw(g);
		}
		//draw platforms
		for(int i = 0; i < rockPlatforms.size(); i++){
			rockPlatforms.get(i).draw(g);
		}	
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		for(int i = 0; i < lasers.size(); i++) {
			
			lasers.get(i).draw(g);
		}
		
		// draw explosions
		/*for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		*/

		// draw hud
		hud.draw(g);
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
	public void keyTyped(int k) {
		/*if(k == KeyEvent.VK_SPACE) {
			player.attack();
		}*/
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
