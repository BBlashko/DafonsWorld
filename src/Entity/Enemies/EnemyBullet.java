package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.MapObject;
import Entity.Player;
import TileMap.TileMap;

public class EnemyBullet extends Enemy{
private boolean hit;
private boolean remove;
private BufferedImage[] sprites;
private BufferedImage[] hitSprites;

private double rise;
private double run;
private double angle;
private double ratio;
	
private Player player;
public EnemyBullet(TileMap tm, double x, double y) {
	
	super(tm);
	
	width = 4;
	height = 4;
	cwidth = 4;
	cheight = 4;
	
	damage = 5;
	
	this.x = x;
	this.y = y;
	
	if(Player.getXposition() < x){
		run = (int)(x - Player.getXposition());
		moveSpeed = -10;
	
	}
	else if(Player.getXposition() > x) {
		run = (int)(Player.getXposition() - x);
		moveSpeed = 10;
	}
	if(Player.getYposition() < y){
		rise = (int)(Player.getYposition() - y);
		fallSpeed = 10;
	}else if(Player.getYposition() > y){
		rise = (int)(y - Player.getYposition());
		fallSpeed = -10;
	}
		
	ratio = rise/run;

	angle = Math.atan(ratio);
	
	dx = moveSpeed * Math.cos(angle);
	dy = fallSpeed * Math.sin(angle);
	
	// load sprites
	try {
		
		BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Eye.png"));
		
		sprites = new BufferedImage[1];
		for(int i = 0; i < sprites.length; i++) {
			sprites[i] = spritesheet.getSubimage(60, 0, width, height);
		}
		
		hitSprites = new BufferedImage[1];
		for(int i = 0; i < hitSprites.length; i++) {
			hitSprites[i] = spritesheet.getSubimage(60, 0, width, height);
		}
		
		setAnimation(new Animation());
		getAnimation().setFrames(sprites);
		getAnimation().setDelay(70);
			
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}

public void setHit() {
	if(hit) return;
	hit = true;
	getAnimation().setFrames(hitSprites);
	getAnimation().setDelay(70);
	dx = 0;
}
	
public boolean shouldRemove() { return remove; }

public void update() {
	checkTileMapCollision();
	setMapPosition();
	setPosition(xtemp, ytemp);
	if(dx == 0 && !hit || dy == 0 && !hit) {
		setHit();
	}
	getAnimation().update();
	if(hit && getAnimation().hasPlayedOnce()) {
		remove = true;
	}
	
}
	
public void draw(Graphics2D g) {
	setMapPosition();
	//g.drawRect( (int)(x + xmap - width / 2), (int)(y + ymap - height / 2),cwidth, cheight);
	super.draw(g);
	
}
}
