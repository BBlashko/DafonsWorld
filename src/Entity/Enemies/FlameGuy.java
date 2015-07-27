package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import TileMap.TileMap;

public class FlameGuy extends Enemy{
	Animation animation;
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {1, 1, 2};
	private static final int IDLE = 0;
	private static final int JUMPING = 1;
	private static final int WALKING = 2;
	private static final int STARTFLAME = 0;
	private static final int CONTFLAME = 1;
	private Flames flames;
	private boolean left;

	public FlameGuy(TileMap tm){
		super(tm);
		
		width = 42;
		height = 53;
		cwidth = 38;
		cheight = 52;
		
		moveSpeed = .2;
		maxSpeed = .4;
		stopSpeed = .75;
		fallSpeed = 0.25;
		maxFallSpeed = 5.0;
		jumpStart = -6.5;
		stopJumpSpeed = 0.10;
		dx = .1;
		dy = 0;
		
		sprites = new ArrayList<BufferedImage[]>();
		flames = new Flames(tm, true);
		
		try{
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/FlameGuy.png"));
			for(int i = 0; i < 3; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
			
				for(int j = 0; j < numFrames[i]; j++) {
					bi[j] = spriteSheet.getSubimage(j * width,i * height, width, height);
				}
				sprites.add(bi);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(100);
	}
	public void getNextPosition(){
		if(!falling){
			if(x < Player.getXposition()){
				left = false;
				animation.setFrames(sprites.get(WALKING));
				dx += moveSpeed;
				if(dx > maxSpeed){
					dx = maxSpeed;
				}
			}else if(x > Player.getXposition()){
				left = true;
				animation.setFrames(sprites.get(WALKING));
				dx -= moveSpeed;
				if(dx < -maxSpeed){
					dx = -maxSpeed;
				}
			}else{
				dx = 0;
				animation.setFrames(sprites.get(IDLE));
			}
		}else{
			dy += fallSpeed;
			if(dy > maxFallSpeed){
				dy = maxFallSpeed;
			}
		}
		if(!bottomLeft && x > Player.getXposition()|| !bottomRight && x < Player.getXposition()){
			dx = 0;
		}
	}
	public void update(){
		setMapPosition();
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		animation.update();
		flames.update(x, y);
		
		
	}
	public void draw(Graphics2D g){
		flames.draw(g, left);
		if(x < Player.getXposition()) {
			g.drawImage(animation.getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
		}
		else {
			g.drawImage(animation.getImage(), (int)(x + xmap - width/2 + width),	(int)(y + ymap - height / 2), -width,height,null);
		}	
		
	}
}
