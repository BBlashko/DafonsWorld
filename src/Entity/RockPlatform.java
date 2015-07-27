package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class RockPlatform extends MapObject {
	
	BufferedImage rockPlatformSheet;
	BufferedImage rockPlatform;
	BufferedImage fallingRockPlatform;
	BufferedImage[] breakPlatform;
	
	
	private boolean steppedOn;
	private boolean playedonce = false;
	private boolean rockFalling = false;
	
	public RockPlatform(TileMap tm){
		super(tm);
		
		width = 31;
		height = 25;
		cwidth = 31;
		cheight = 25;
		
		fallSpeed = .5;
		maxFallSpeed = 5;
		
		try{
			rockPlatformSheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/MoveableBlocks/rockPlatform.png"));
			rockPlatform = rockPlatformSheet.getSubimage(0, 0, width, height);
			fallingRockPlatform = rockPlatformSheet.getSubimage(279, 0, width, height);
			breakPlatform = new BufferedImage[10];
			
			for(int i = 0; i < breakPlatform.length; i++){
				breakPlatform[i] = rockPlatformSheet.getSubimage(i*width, 0, width, height);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		setAnimation(new Animation());
	}
	public void setSteppedOn(){
		if(steppedOn) return;
		steppedOn = true;
		getAnimation().setFrames(breakPlatform);
		getAnimation().setDelay(60);
	}
	public boolean getSteppedOn(){
		return steppedOn;
	}
		
	//public boolean shouldRemove() { return remove; }
	public void getNextPosition(){
		dy += fallSpeed;
		if(dy > maxFallSpeed){
			dy = maxFallSpeed;
		}
	}
	public void update(){
		setMapPosition();
		if(steppedOn && !animation.hasPlayedOnce()){
			getAnimation().update();
			if(animation.hasPlayedOnce()){
				setPlayedonce(true);
			}
		}
		if(animation.hasPlayedOnce()){
			rockFalling = true;
		}
		if(rockFalling){
			getNextPosition();
			checkTileMapCollision();
			setPosition(xtemp, ytemp);
		}		
	}
	public void draw(Graphics2D g){
		
		if(steppedOn && !animation.hasPlayedOnce()){
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - cwidth /2), (int)(y + ymap - cheight/2), null);
			rockFalling = false;
		}else if(animation.hasPlayedOnce()){
			rockFalling = true;
			g.drawImage(fallingRockPlatform, (int)(x + xmap - cwidth /2), (int)(y + ymap - cheight/2), null);
		}
		else{
			g.drawImage(rockPlatform, (int)(x + xmap - cwidth /2), (int)(y + ymap - cheight/2), null);
			rockFalling = false;
		}
	}
	public boolean getBottomLeft(){
		return bottomLeft;
	}
	public boolean getBottomRight(){
		return bottomRight;
	}
	public boolean isPlayedonce() {
		return playedonce;
	}
	public void setPlayedonce(boolean b) {
		this.playedonce = b;
	}
}
