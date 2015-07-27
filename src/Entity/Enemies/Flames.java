package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import TileMap.TileMap;

public class Flames extends Enemy{
	
	BufferedImage[] sprites;
	BufferedImage spriteSheet;
	boolean flameguy;
	private int i = 0;
	
	public Flames(TileMap tm, boolean flameguy){
		super(tm);
		
		width = 112;
		height = 24;
		cwidth = 110;
		cheight = 22;
		this.flameguy = flameguy;
		
		try{
			sprites = new BufferedImage[8];
			spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/FlameGuy.png"));
			for(int i = 0; i < 8; i++){
				
				
				sprites[i] = spriteSheet.getSubimage(0, 159 + i*height, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(100);
	}
	public void getNextPosition(){
		if(flameguy){
			x = FlameGuy.getXposition();
		}
	}
	public void update(double x, double y){
		setMapPosition();
		getNextPosition();
		this.x = x;
		this.y = y;
		setPosition(x, y);
		if(Math.abs(x - Player.getXposition()) < 200){
			animation.update();
			if(animation.getFrame() == 8){
				animation.setFrame(7);
				i++;
				if(i == 5){
					i = 0;
					animation.setFrame(0);
				}
			}
		}else{
			animation.setFrame(0);
		}
	}
	public void draw(Graphics2D g, boolean left){
		if(left){
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + 35), (int)(y + ymap - height / 2 - 7), -width, height, null);
	
		}else{
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2 + 78), (int)(y + ymap - height / 2 - 7), width, height, null);

		}
	}
}
