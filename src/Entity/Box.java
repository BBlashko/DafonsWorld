package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Box extends MapObject{
	
	
	private BufferedImage[] boxes;
	
	private Player player;
	private Box box;
	
	private static boolean moveBox;
	private static boolean moveBoxLeft = false;
	
	public Box(TileMap tm) {
		
		super(tm);
		
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 33;
		
		moveSpeed = .5;
		maxSpeed = 3;
		stopSpeed = .75;
		fallSpeed = 0.25;
		maxFallSpeed = 5.0;
		jumpStart = -5;
		stopJumpSpeed = 0.3;
		
		try{
			BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/Sprites/MoveableBlocks/Box.png"));
			
			boxes = new BufferedImage[1];
			for(int i = 0; i < boxes.length; i++) {
				boxes[i] = img.getSubimage(i * width, 0, width, height);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		setAnimation(new Animation());
		getAnimation().setFrames(boxes);
		getAnimation().setDelay(70);
	}
	private void getNextPosition() {

			if(moveBox && player.getYposition() > y - 20){
				
				if(left){
					dx = (-maxSpeed);
				} else if(right) {
					dx = maxSpeed;
				}
			}else {
				dx = 0;
			}
			if(falling){
				dy += fallSpeed;
				if(dy > maxFallSpeed){
					dy = maxFallSpeed;
				}
			}
	}
	public void setMoveBox(boolean b){
		moveBox = b;
	}
	public static void setMoveBoxLeft(boolean b){
		moveBoxLeft = b;
	}
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

	}
	public void draw(Graphics2D g) {
		setMapPosition();
		
		g.drawImage(getAnimation().getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2+1), null);

		
		
	}
}
