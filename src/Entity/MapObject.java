package Entity;

import Audio.AudioOut;
import Main.ControlPanel;
import Main.Stats;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.Rectangle;

public abstract class MapObject {
	
	private AudioOut audio;
	// tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	// position and vector
	protected double x;
	protected double y;
	protected double dx;
	protected double dy;
	
	// dimensions
	protected int width;
	protected int height;
	
	// collision box
	protected int cwidth;
	protected int cheight;
	
	// collision
	protected int currRow;
	protected int currRowHead;
	protected int currRowFeet;
	protected int currCol;
	protected int tln;
	protected int trn;
	protected int sln;
	protected int srn;
	protected int kln;
	protected int krn;
	protected int bln;
	protected int brn;

	protected double xdest;
	protected double ydest;
	protected double xtemp;
	protected double ytemp;
	
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	protected boolean shoulderLeft;
	protected boolean shoulderRight;
	protected boolean kneeRight;
	protected boolean kneeLeft;
	
	// animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	private static boolean facingRight;
	
	// movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean idle;
	protected boolean jumping;
	protected boolean falling;
	protected boolean swimming;
	
	// movement attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jumpStart;
	protected double stopJumpSpeed;
	protected double swimSpeed;
	protected double maxSwimSpeed;
	protected double sinkSpeed;
	protected double maxSinkSpeed;
	protected double riseSpeed;
	protected double maxRiseSpeed;
	
	protected int orientation;
	public Rectangle rect;
	
	public static double xposition;
	public static double yposition;
	
	public boolean topBox;
	
	// constructor
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize(); 
	}
	public boolean intersects(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	public Rectangle getRectangle() {
		return new Rectangle((int)x - cwidth/2, (int)y - cheight/2,	cwidth, cheight);
	}
	public boolean intersectsBottom(MapObject o){
		Rectangle r1 = getBottomRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}
	public boolean intersectsLaser(MapObject o){
		Rectangle r1 = getRectangle();
		
		if(o.orientation == 0){
			Rectangle r2 = o.getLaserRectangle();
			return r1.intersects(r2);
		}else{
			Rectangle r2 = o.getLaserRectangle1();
			return r1.intersects(r2);
		}
		
	}
	public Rectangle getLaserRectangle(){
		
		return new Rectangle((int)x -20, (int)y -1, cwidth, cheight);
	}
	public Rectangle getLaserRectangle1(){
		return new Rectangle((int)x -15, (int)y -1, cwidth, cheight);
	}

	public Rectangle getBottomRectangle() {
		return new Rectangle((int)x - cwidth/2, (int)y + cheight/2, cwidth, 1);
	}
	public boolean intersectsBox(MapObject o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getBottomRectangle();
		return r1.intersects(r2);
	}

	
	public void calculateCorners(double x, double y) {
		
		int leftTile = (int)(x - cwidth / 2) / tileSize;
		int rightTile = (int)(x + cwidth / 2 - 1) / tileSize;
		int topTile = (int)(y - cheight / 2) / tileSize;
		int bottomTile = (int)(y + cheight / 2 - 1) / tileSize;
		
		if(topTile < 1){
			topTile = 0;
		}else if(leftTile < 1){
			leftTile = 0;
		}
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
	
		tln = tileMap.getNumber(topTile,leftTile);
		trn = tileMap.getNumber(topTile, rightTile);        
		bln = tileMap.getNumber(bottomTile, leftTile);              
		brn = tileMap.getNumber(bottomTile, rightTile);             
		
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		
	}
	
	public void checkTileMapCollision() {
		
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;
	
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		if(bln == 18 || brn == 18){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRow;
			
		}else if(bln == 8 || brn == 8){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRow;
		
		}
		calculateCorners(x, ydest);
		if(dy < 0) {
			if(topLeft || topRight) {
				dy = 0;
				ytemp = currRow * tileSize + cheight / 2;
			}
			else if(tln == 30 || trn == 30 || srn == 30 || sln == 30 || kln == 30 || krn == 30){
				ytemp += dy;
			}
			else {
				ytemp += dy;
			}
		}
		if(dy > 0) {
			
			if(bottomLeft || bottomRight) {
				dy = 0;
				falling = false;
				ytemp = (currRow + 1) * tileSize - cheight / 2;
				
			}
			
			else {
				ytemp += dy;
			}
		}
		
		calculateCorners(xdest, y);
		if(dx < 0) {
			if(topLeft || bottomLeft) {
				dx = 0;
				xtemp = currCol * tileSize + cwidth / 2;
			}
			if(tln == 30 || trn == 30 || srn == 30 || sln == 30 || kln == 30 || krn == 30){
				ytemp += dy;
			}
			else {
				xtemp += dx;
			}
		}
		if(dx > 0) {
			if(topRight || bottomRight) {
				dx = 0;
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}
			else {
				xtemp += dx;
			}
		}
		
		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
		
	}
	public void calculateCornersPlayer(double x, double y){
		
		int leftTile = (int)(x - cwidth /2) /tileSize;
		int rightTile = (int)(x + cwidth /2- 1) /tileSize;          
		int shoulderHeight = (int)(y-cheight/4)/tileSize;
		int kneeHeight = (int)(y+cheight/4)/tileSize;		
		int topTile = (int)(y - cheight /2) /tileSize;			
		int bottomTile = (int)(y + cheight/2 -1) /tileSize;
		
		if(topTile < 1){
			topTile = 1;
		}else if(leftTile < 1){
			leftTile = 1;
		}
		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);
		int sl = tileMap.getType(shoulderHeight, leftTile);
		int sr = tileMap.getType(shoulderHeight, rightTile);
		int kl = tileMap.getType(kneeHeight, leftTile);
		int kr = tileMap.getType(kneeHeight, rightTile);
	
		tln = tileMap.getNumber(topTile,leftTile);
		trn = tileMap.getNumber(topTile, rightTile);        
		bln = tileMap.getNumber(bottomTile, leftTile);              
		brn = tileMap.getNumber(bottomTile, rightTile);             
		sln = tileMap.getNumber(shoulderHeight, leftTile);          
		srn = tileMap.getNumber(shoulderHeight, rightTile);         
		kln = tileMap.getNumber(kneeHeight, leftTile);              
		krn = tileMap.getNumber(kneeHeight, rightTile);
	
		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
		shoulderLeft = sl == Tile.BLOCKED;
		shoulderRight = sr == Tile.BLOCKED;
		kneeLeft = kl == Tile.BLOCKED;
		kneeRight = kr == Tile.BLOCKED;
		
		
	
	}
	public void interactCollisions(){
		
		//Character intersects tokens
		if(krn == 108 && kln == 108 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 108 && sln == 108){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}else if(krn == 28 && kln == 28 && down){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			
		}else if(krn == 49 && kln == 49 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 49 && sln == 49){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}
		else if(krn == 59 && kln == 59 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 59 && sln == 59){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}
		else if(krn == 69 && kln == 69 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 69 && sln == 69){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}
		else if(krn == 79 && kln == 79 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 79 && sln == 79){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}
		else if(krn == 89 && kln == 89 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 89 && sln == 89){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}
		else if(krn == 99 && kln == 99 ){
			
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			TileMap.changeTile = true;
		
			
		}else if(srn == 99 && sln == 99){
			TileMap.currCol = currCol;
			TileMap.currRow = currRowHead;
			TileMap.changeTile = true;
			
		}else if(bln == 18 || brn == 18){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			//Stats.updateButtonsPressed();
			Stats.updateStats();
			
		}else if(bln == 8 || brn == 8){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
			Stats.updateButtonsPressed();
			Stats.updateStats();
		
		}else if(bln == 68 || brn == 68){
			TileMap.changeTile = true;
			TileMap.currCol = currCol;
			TileMap.currRow = currRowFeet;
		
		}
	
	}
	public void checkTileMapCollisionPlayer() {
	
		currCol = (int)x / tileSize;
		currRowHead = (int)(y - 15) / tileSize;
		currRowFeet = (int)(y + 15) / tileSize;
		
		TileMap.setCurrCol(currCol);
		TileMap.setCurrRow(currRowFeet);
		
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		interactCollisions();
		calculateCornersPlayer(x,ydest);
		if(!topBox){
			if(dy < 0){
				jumping = true;
				if(tln == 30 || trn == 30 || srn == 30 || sln == 30 || kln == 30 || krn == 30){
					ytemp += dy;
				}
				else if(topLeft || topRight){
					dy = 0;
					ytemp = (currRowHead+1) * tileSize;
				}
				else if(tln == 9 || tln == 29 || trn == 9 || trn == 29 || srn == 9 || srn == 29 || sln == 9 || sln == 29 || kln == 9 || kln == 29 || krn == 9 || krn == 29 || bln == 9 || bln == 29 || brn == 9 || brn == 29){
					swimming = true;
					if(tln == 29 || trn == 29 || bln == 29 || brn == 29 || krn == 29 || kln == 29 || srn == 29 || sln == 29){
					Player.updateHealth(-1);
					}
					ytemp += dy;
				}
				else {
					swimming = false;
					ytemp += dy;
				}
			
		
		
		
			}if(dy > 0) {	
				falling = true;
				if(tln == 30 || trn == 30 || srn == 30 || sln == 30 || kln == 30 || krn == 30){
					ytemp += dy;
				}
				else if(kneeRight && krn == 30 || kneeLeft && kln == 30){
					bottomLeft = false;
					bottomRight = false;
				}
				else if(tln == 9 || tln == 29 || trn == 9 || trn == 29 || srn == 9 || srn == 29 || sln == 9 || sln == 29 || kln == 9 || kln == 29 || krn == 9 || krn == 29 || bln == 9 || bln == 29 || brn == 9 || brn == 29){
					swimming = true;
					if(tln == 29 || trn == 29 || bln == 29 || brn == 29 || krn == 29 || kln == 29 || srn == 29 || sln == 29){
						Player.updateHealth(-1);
					}
					ytemp += dy;
				
				}
			
				else if(bottomLeft || bottomRight || bln == 30 && sln == 30 || brn == 30 && srn == 30){
					dy = 0;
					falling = false;
					jumping = false;
					ytemp = (currRowFeet-1) * tileSize + cheight/2;
					
				}else if(bln == 58 || brn == 58){
					audio = new AudioOut("/SFX/Comical sound series boing, spring.mp3");
					audio.play();
					dy = -16;
					falling = true;
					ytemp += dy;
				}
				else if(bln == 78 || brn == 78){
					dy = -7;
					falling = true;
					Player.updateHealth(-10);
				}
			
				else{
					ytemp += dy;
				}
		
			}
		}
		
		calculateCornersPlayer(xdest, y);
			if(dx < 0){
				if(tln == 30 || sln == 30 || kln == 30 || bln == 30){
					xtemp += dx;
					
				}else if(topLeft || bottomLeft || kneeLeft || shoulderLeft){
					dx = 0;
					xtemp = (currCol) * tileSize + cwidth/2;
				}
				else{ 
					xtemp += dx;
				}
				
			}if(dx > 0){
				if(trn == 30 || srn == 30 || krn == 30 || brn == 30){
					xtemp += dx;
				}
				else if(topRight || bottomRight || kneeRight || shoulderRight){
					dx = 0;
					xtemp = (currCol) * tileSize + cwidth/2 +10;
				}else{ 
					xtemp += dx;
				}
				
			
			}
		if(!falling){
			calculateCornersPlayer(x, ydest+1);
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
		setXposition(xtemp);
		setYposition(ytemp);
	}
	public void setTopBox(boolean b){
		topBox = b;
	}
	
	public static void setXposition(double x){
		xposition = x;
	}
	public static double getXposition(){
		return xposition;
	}
	public static void setYposition(double y){
		yposition = y;
	}
	public static double getYposition(){
		return yposition;
	}
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getCWidth() { return cwidth; }
	public int getCHeight() { return cheight; }
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	public void setIdle(boolean b){ idle = b; }
	
	public boolean notOnScreen() {
		return x + xmap + width < 0 ||
			x + xmap - width > ControlPanel.WIDTH ||
			y + ymap + height < 0 ||
			y + ymap - height > ControlPanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		
		
		if(getFacingRight()) {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width / 2), (int)(y + ymap - height / 2), null);
			
		}
		else {
			g.drawImage(getAnimation().getImage(), (int)(x + xmap - width/2 + width),	(int)(y + ymap - height / 2), -width,height,null);
		}
	
		
	}
	public boolean getFacingRight() {
		return facingRight;
	}
	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}
	public Animation getAnimation() {
		return animation;
	}
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
}
