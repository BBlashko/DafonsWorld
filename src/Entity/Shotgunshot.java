package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Shotgunshot extends MapObject {

	private BufferedImage[] sprites;
	private boolean remove;
	private boolean hit;
	private boolean right;
	
	public Shotgunshot(TileMap tm, boolean right) {
		
		super(tm);
		
		setFacingRight(right);
		
		this.right = right;
		
		width = 22;
		height = 22;
		cwidth = 22;
		cheight = 22;
		
		
		
		// load sprites
		try {
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Ammo.png"));
			
			sprites = new BufferedImage[7];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 88, width, height);
			}
			
			setAnimation(new Animation());
			getAnimation().setFrames(sprites);
			getAnimation().setDelay(50);
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public boolean shouldRemove() { 
		return remove;
		
	}
	
	public void update() {
		
		checkTileMapCollision();
		
		getAnimation().update();
		if(getAnimation().hasPlayedOnce()) {
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
