package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.AudioOut;
import TileMap.TileMap;

public class Bomb extends MapObject {
		
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	
	private AudioOut audio;
	
	
	
	public Bomb(TileMap tm, boolean right) {
		
		super(tm);
		audio = new AudioOut("/SFX/Explosion, explode.mp3");
		setFacingRight(right);
				
		moveSpeed = 5;
		fallSpeed = -3;
				
		
		if(right) {
			dx = moveSpeed;
		}
		else {
			dx = -moveSpeed;
		
		}
		dy = fallSpeed;
	
		
		
		
		width = 12;
		height = 19;
		cwidth = 44;
		cheight = 44;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Player/Ammo.png"));
			
			sprites = new BufferedImage[2];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 10, width, height);
			}
			
			hitSprites = new BufferedImage[8];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * 44, 44, 44, 44);
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
		audio.play();
		
		hit = true;
		getAnimation().setFrames(hitSprites);
		getAnimation().setDelay(20);
		dx = 0;
	}
		
	public boolean shouldRemove() { return remove; }
	
	public void update() {
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		fallSpeed += .15;
		if(fallSpeed > 3){
			fallSpeed = 3;
		}
		dy = fallSpeed;
		
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
		
		super.draw(g);
		
	}
}
