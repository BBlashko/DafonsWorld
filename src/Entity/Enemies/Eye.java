package Entity.Enemies;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Bullet;
import Entity.Enemy;
import Entity.MapObject;
import Entity.Player;


public class Eye extends Enemy {
	
	private BufferedImage[] sprites;
	private BufferedImage[] healthBar;
	Player player;
	
	public static ArrayList<EnemyBullet> enemyBullets;
	
	private int updateCount = 0;
	
	
	public Eye(TileMap tm) {
		super(tm);
		
		
		width = 30;
		height = 31;
		cwidth = 30;
		cheight = 31;
		
		health = maxHealth = 30;
		damage = 5;
		
		enemyBullets = new ArrayList<EnemyBullet>();
		
		//load sprites
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Eye.png"));
			BufferedImage healthBar1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/EnemyHealthBar.png"));
			healthBar = new BufferedImage[11];
			for(int i = 0; i < healthBar.length;i++){
				healthBar[i] = healthBar1.getSubimage(0, i*4,33, 4);
			}
			sprites = new BufferedImage[2];
			for(int i = 0; i < sprites.length;i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		setAnimation(new Animation());
		getAnimation().setFrames(sprites);
		getAnimation().setDelay(100);
	}
	public static void checkPlayerHit(Player p) {
		
		Player player = p;
		try{
			int eb = enemyBullets.size();
			for(int i = 0; i < eb; i++){
			if(enemyBullets.get(i).intersects(player)) {
				player.hit(damage);
				enemyBullets.get(i).setHit();
				break;
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	public void update(){
		
		setMapPosition();
		setPosition(x, y);
		
		getAnimation().update();
		
		updateCount++;
		if(updateCount > 100){
			EnemyBullet b = new EnemyBullet(tileMap,x,y);
			b.setPosition(x, y);
			enemyBullets.add(b);
			updateCount = 0;
		}
		for(int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).update();
			if(enemyBullets.get(i).shouldRemove()) {
				enemyBullets.remove(i);
				i--;
			}
		}
	}
	public void draw(Graphics2D g){
		
		setMapPosition();
	
		
		if(Player.getXposition() < x) {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap -(width-10)), (int)(y + ymap - (height-1)),null);
		}
		else {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap +width/2-4), (int)(y + ymap - (height-1)), -width, height,null);
		}
		if(health == maxHealth){
			g.drawImage(healthBar[0], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		}
		if(health < maxHealth){
			g.drawImage(healthBar[1], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.10)){
			g.drawImage(healthBar[2], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.20)){
			g.drawImage(healthBar[3], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.30)){
			g.drawImage(healthBar[4], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.40)){
			g.drawImage(healthBar[5], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.50)){
			g.drawImage(healthBar[6], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.60)){
			g.drawImage(healthBar[7], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.70)){
			g.drawImage(healthBar[8], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.80)){
			g.drawImage(healthBar[9], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		if(health < (maxHealth - maxHealth*.90)){
			g.drawImage(healthBar[10], (int)(x + xmap -(width-9)), (int)(y + ymap - (height+10)),null);
		
		}
		for(int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).draw(g);
		}
	}
}
