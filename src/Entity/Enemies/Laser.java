package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;
import Entity.Animation;
import Entity.Enemy;

public class Laser extends Enemy{
	
	BufferedImage[] sprites;
	
	private int laserWidth;
	private boolean printRight;
	
	public Laser(TileMap tm, boolean right, int laserWidth, int orientation){
		super(tm);
		
		this.orientation = orientation;
		this.laserWidth = laserWidth;
		printRight = right;
		
		width = 15;
		height = 28;
		cwidth = 0;
		cheight = 0;
		
		damage = 10;
		
		health = maxHealth = 20;
		
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/Laser.png"));
			
			if(orientation == 0){
				sprites = new BufferedImage[18];
				for(int i = 0; i < sprites.length; i++){
					if(i < 12){
						sprites[i] = spritesheet.getSubimage(0, i*height, width, height);
					}
					else{
						sprites[i] = spritesheet.getSubimage(0, i*height, laserWidth, height);
					}
				}
			}else {
				sprites = new BufferedImage[18];
				width = 28;
				height = 15;
				for(int i = 0; i < sprites.length; i++){
					if(i < 12){
						sprites[i] = spritesheet.getSubimage(i*width, 505, width, height);
					}
					else{
						sprites[i] = spritesheet.getSubimage(i*width, 505, width, laserWidth);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setAnimation(new Animation());
		getAnimation().setDelay(200);
		getAnimation().setFrames(sprites);
	}
	public void update(){
		setMapPosition();
		getAnimation().update();

		if(getAnimation().getFrame() == 12 || getAnimation().getFrame() == 13 || getAnimation().getFrame() == 14 || getAnimation().getFrame() == 15 || getAnimation().getFrame() == 16 || getAnimation().getFrame() == 17){
			if(orientation == 0){
				cwidth = laserWidth + width;
				cheight = 2;
			}else { 
				cheight = laserWidth + width;
				cwidth = 2;
			}
		}
		else{
			cwidth = 0;
			cheight = 0;
		}
		
	}	
	public void draw(Graphics2D g){
		
		if(printRight) {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width/2 + width),	(int)(y + ymap - height / 2), -width,height,null);
		}
		
	}
}
