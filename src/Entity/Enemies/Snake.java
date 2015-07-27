package Entity.Enemies;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import TileMap.TileMap;

public class Snake extends Enemy {
	
	BufferedImage spritesheet;
	BufferedImage[] sprites;
	
	private boolean printRight;
	
	
	public Snake(TileMap tm){
		super(tm);
		
		falling = true;
		
		width = 50;
		height = 29;
		cheight = 29;
		cwidth = 50;
		
		printRight = true;
		moveSpeed = .3;
		maxSpeed = 1.5;
		stopSpeed = .3;
		fallSpeed = 2;
		damage = 1;
		
		health = maxHealth = 20;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Snake.png"));
			
			sprites = new BufferedImage[2];
			for(int i = 0; i < sprites.length; i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		setAnimation(new Animation());
		getAnimation().setFrames(sprites);
		getAnimation().setDelay(20);
	}
	public void getNextPosition(){
		setMapPosition();
		if(Player.getXposition() < x){
			printRight = false;
			dx -= moveSpeed;
			if(dx < -maxSpeed){
				dx = -maxSpeed;
			}
		}else if(Player.getXposition() > x){
			printRight = true;
			dx += moveSpeed;
			if(dx > maxSpeed){
				dx = maxSpeed;
			}
		}else {
			if(dx > 0){
				dx -= stopSpeed;
				if(dx > 0){
					dx = 0;
				}
			}else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		if(!bottomLeft || !bottomRight){
			dy += fallSpeed;
			
			if(dy > 0){
				jumping = false;
			}
			if(dy < 0 && !jumping){
				dy += stopJumpSpeed;
			}
			if(dy > maxFallSpeed){
				dy = maxFallSpeed;
			}
		}
	
	}
	public void update(){
		setMapPosition();
	
		
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
	
		getAnimation().update();
	}
	public void draw(Graphics2D g){
	
		if(printRight){
			g.drawImage(getAnimation().getImage(), (int)(x + xmap + width/2 - 15), (int)(y + ymap - height / 2 + 10), -width, height, null);
		}else{
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width / 2 -15), (int)(y + ymap - height / 2 + 10), null);
		}
	}
}
