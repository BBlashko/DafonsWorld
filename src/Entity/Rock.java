package Entity;

import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Rock extends MapObject {
	
	private BufferedImage rock;
	private double mouseX, mouseY;
	private double angle, distance, amplitude, rise, run, ratio;
	
	private Player player;
	
	public Rock(TileMap tm){
		super(tm);
		
		cwidth = 5;
		cheight = 5;
		height = 5;
		width = 5;
		
		moveSpeed = 5;
			
		
		ratio = rise/run;

		angle = Math.atan(ratio);
		
		dx = moveSpeed * Math.cos(angle);
		dy = fallSpeed * Math.sin(angle);
	
		try{
			rock = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Rock.png"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}
	public void getNextPosition(){
		
	}
	public void update(){
		setMapPosition();
		checkTileMapCollision();
		getNextPosition();
		setPosition(xtemp, ytemp);
		
		
	}
	public void setMouseX(double num){
		mouseX = num;
	}
	public void setMouseY(double num){
		mouseY = num;
	}
	public void draw(Graphics2D g){
		
		g.drawImage(rock, (int)(x + xmap - width), (int)(y + ymap - height/2), null);
		
	}
}
