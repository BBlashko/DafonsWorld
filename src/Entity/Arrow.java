package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Arrow extends MapObject {
		
	private boolean hit;
	private boolean remove;
	private BufferedImage[] sprites;
	private BufferedImage[] hitSprites;
	private boolean right;

	private boolean once = true;	
	
	public Arrow(TileMap tm, boolean right) {
		
		super(tm);
		
		setFacingRight(right);
		this.right = right	;
		
		moveSpeed = 6;
		fallSpeed = -2;
				
		
		if(right) {
			dx = moveSpeed;
		}
		else {
			dx = -moveSpeed;
		
		}
		dy = fallSpeed;
	
		
		
		
		width = 30;
		height = 7;
		cwidth = 30;
		cheight = 7;
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/Sprites/Player/Ammo.png"));
			
			sprites = new BufferedImage[1];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(0, 3, width, height);
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
		fallSpeed += .05;
		if(fallSpeed > 2){
			fallSpeed = 2;
		}
		dy = fallSpeed;
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
		
		if(right) {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width/2 + width),	(int)(y + ymap - height / 2), -width,height,null);
		}
		
	}
}
