package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Bullet extends MapObject {
		
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
		
	public Bullet(TileMap tm, boolean right) {
		
		super(tm);
		
		setFacingRight(right);
		
		moveSpeed = 6;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 5;
		height = 3;
		cwidth = 5;
		cheight = 3;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Player/Ammo.png"));
			
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
			}
			
			hitSprites = new BufferedImage[1];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
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
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
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

