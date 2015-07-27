package Entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Platform extends MapObject {
	
	BufferedImage platform;
	BufferedImage sprites[];
	private double startX;
	private int randomInt;
	private boolean runOnce = true;
	private boolean movingLeft;
	private boolean onPlatform;
	
	public Platform(TileMap tm){
		super(tm);
		height = 10;
		width = 30;
		cwidth = 30;
		cheight = 10;
		
		moveSpeed = .5;
	
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(2);
		
		
		if(randomInt == 0){
			dx = moveSpeed;
		}
		if(randomInt == 1){
			dx = -moveSpeed;
		}
		try{
			platform = ImageIO.read(getClass().getResourceAsStream("/Sprites/MoveableBlocks/Platform.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void getNextPosition(){
		if(x > startX + 60){
			dx = -moveSpeed;
		}
		if(x < startX - 60){
			dx = moveSpeed;
		}
	}
	public void update(){
		if(runOnce){
			startX = x;
			runOnce = false;
		}
		
		setMapPosition();
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == .5){
			movingLeft = false;
		}
		if(dx == -.5){
			movingLeft = true;
		}
	}
	
	public void draw(Graphics2D g){
		g.drawImage(platform, (int)(x + xmap - width / 2), (int)(y + ymap - height /2), null);
		
	}
	public double getdx() {
	
		return dx;
	}
	public double getMoveSpeed() {
		return moveSpeed;
	}
	public boolean getMovingLeft(){
		return movingLeft;
	}
	public void setOnPlatform(boolean b){
		onPlatform = b;
	}
	public boolean getOnPlatform(){
		return onPlatform;
	}
}
