package TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import javax.imageio.ImageIO;

import Entity.Player;
import Main.ControlPanel;
import Main.Stats;

public class TileMap {
	
	// position
	private double x;
	private double y;
	
	// bounds
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	private double tween;
	
	// map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// drawing
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	//changetiles
	public static boolean changeTile;
	public static boolean changeBlueBarrier;
	public static boolean changeRedBarrier;
	public static boolean changeGreenBarrier;
	public static int currRow;
	public static int currCol;
	
	
	public TileMap(int tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw =  ControlPanel.HEIGHT / tileSize + 2;
		numColsToDraw = ControlPanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s) {
		
		try {

			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tileSize;
			tiles = new Tile[11][numTilesAcross];
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				if(col == 0){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.NORMAL, 0);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 30);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.NORMAL);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.BLOCKED);
				}
				else if(col < 3 && col > 0){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.BLOCKED);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.BLOCKED);
				}
				else if(col == 3){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 3);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 13);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED, 23);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 33);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED, 43);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED, 53);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED, 63);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED, 73);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.NORMAL, 83);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.NORMAL, 93);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.NORMAL, 103);
				}
				else if(col == 4){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 4);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 14);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED, 24);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 34);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED, 44);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED, 54);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED, 64);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED, 74);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.NORMAL, 84);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.NORMAL, 94);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.NORMAL, 104);
				}else if(col == 5){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 5);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 15);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED, 25);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 35);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED, 45);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED, 55);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED, 65);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED, 75);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.NORMAL, 85);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.NORMAL, 95);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.NORMAL, 105);
				}
				else if(col == 6){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 6);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 16);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED, 26);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 36);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED, 46);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED, 56);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED, 66);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED, 76);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.BLOCKED, 86);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.BLOCKED, 96);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.BLOCKED, 106);
				}
				else if(col == 7){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 7);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 17);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.BLOCKED, 27);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.BLOCKED, 37);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.BLOCKED, 47);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.BLOCKED, 57);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.BLOCKED, 67);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.BLOCKED, 77);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.BLOCKED, 87);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.BLOCKED, 97);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.BLOCKED, 107);
				}
				else if(col == 8){
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.BLOCKED, 8);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.BLOCKED, 18);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.NORMAL, 28);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.NORMAL, 38);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.NORMAL, 48);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.NORMAL, 58);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.NORMAL, 68);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.NORMAL, 78);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.BLOCKED, 88);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.BLOCKED, 98);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.NORMAL, 108);
				}
				else{
					subimage = tileset.getSubimage(col * tileSize,0,tileSize,tileSize);
					tiles[0][col] = new Tile(subimage, Tile.NORMAL, 9);
					subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize, tileSize);
					tiles[1][col] = new Tile(subimage, Tile.NORMAL, 9);
					subimage = tileset.getSubimage(col * tileSize,tileSize *2, tileSize, tileSize);
					tiles[2][col] = new Tile(subimage, Tile.NORMAL, 29);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 3, tileSize, tileSize);
					tiles[3][col] = new Tile(subimage, Tile.NORMAL, 29);
					subimage = tileset.getSubimage(col * tileSize,tileSize * 4,tileSize, tileSize);
					tiles[4][col] = new Tile(subimage, Tile.NORMAL, 49);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 5, tileSize, tileSize);
					tiles[5][col] = new Tile(subimage, Tile.NORMAL, 59);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 6, tileSize, tileSize);
					tiles[6][col] = new Tile(subimage, Tile.NORMAL, 69);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 7, tileSize, tileSize);
					tiles[7][col] = new Tile(subimage, Tile.NORMAL, 79);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 8, tileSize, tileSize);
					tiles[8][col] = new Tile(subimage, Tile.NORMAL, 89);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 9, tileSize, tileSize);
					tiles[9][col] = new Tile(subimage, Tile.NORMAL, 99);
					subimage = tileset.getSubimage(col * tileSize, tileSize * 10, tileSize, tileSize);
					tiles[10][col] = new Tile(subimage, Tile.NORMAL, 109);
				}
			
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s) {
		
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = ControlPanel.WIDTH - width;
			xmax = 0;
			ymin = ControlPanel.HEIGHT - height;
			ymax = 0;
			
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
					
				}
				
			}
		
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
	
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	public int getNumber(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getNumber();
	}
	public static void setChangeTile(){
		changeTile = true;
	}
	public static void setCurrCol(int col){
		currCol = col;
	}
	public static void setCurrRow(int row){
		currRow = row;
	}
	public void loadBox(){
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if(row >= numRows) break;
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
			}
		}
	}
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		for(
			int row = rowOffset;
			row < rowOffset + numRowsToDraw;
			row++) {
			
			if(row >= numRows) break;
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				if(changeGreenBarrier){
					if(map[row][col] == 23) map[row][col] = 83;
					if(map[row][col] == 24) map[row][col] = 0;
					if(map[row][col] == 25) map[row][col] = 84;
					if(map[row][col] == 33) map[row][col] = 93;
					if(map[row][col] == 34) map[row][col] = 0;
					if(map[row][col] == 35) map[row][col] = 94;
					if(map[row][col] == 6) map[row][col] = 85;
					if(map[row][col] == 16) map[row][col] = 0;
					if(map[row][col] == 26) map[row][col] = 95;
					if(map[row][col] == 7) map[row][col] = 105;
					if(map[row][col] == 17) map[row][col] = 0;
					if(map[row][col] == 27) map[row][col] = 104;
				}
				if(changeRedBarrier){
					if(map[row][col] == 43) map[row][col] = 83;
					if(map[row][col] == 44) map[row][col] = 0;
					if(map[row][col] == 45) map[row][col] = 84;
					if(map[row][col] == 53) map[row][col] = 93;
					if(map[row][col] == 54) map[row][col] = 0;
					if(map[row][col] == 55) map[row][col] = 94;
					if(map[row][col] == 46) map[row][col] = 85;
					if(map[row][col] == 56) map[row][col] = 0;
					if(map[row][col] == 66) map[row][col] = 95;
					if(map[row][col] == 47) map[row][col] = 105;
					if(map[row][col] == 57) map[row][col] = 0;
					if(map[row][col] == 67) map[row][col] = 104;
				}
				if(changeBlueBarrier){
					if(map[row][col] == 3) map[row][col] = 83;
					if(map[row][col] == 4) map[row][col] = 0;
					if(map[row][col] == 5) map[row][col] = 84;
					if(map[row][col] == 13) map[row][col] = 93;
					if(map[row][col] == 14) map[row][col] = 0;
					if(map[row][col] == 15) map[row][col] = 94;
					if(map[row][col] == 86) map[row][col] = 85;
					if(map[row][col] == 96) map[row][col] = 0;
					if(map[row][col] == 106) map[row][col] = 95;
					if(map[row][col] == 87) map[row][col] = 105;
					if(map[row][col] == 97) map[row][col] = 0;
					if(map[row][col] == 107) map[row][col] = 104;

				}
			
				if(changeTile){
					changeTile = false;
					if(map[currRow][currCol] == 108) {
						map[currRow][currCol] = 0;
						Player.updateTokens(1);
						Stats.updateTokens();
						Stats.updateScore(10);
					}else if(map[currRow][currCol] == 28) {
						map[currRow][currCol] = 48;
						map[currRow-1][currCol] = 38;
						Stats.updateChests();
						Random randomGenerator = new Random();
						int randomInt = randomGenerator.nextInt(7);
							if(randomInt == 1){
								map[currRow-2][currCol] = 108;
							}
							else if(randomInt == 2){
								map[currRow-2][currCol] = 49;
							}
							else if(randomInt == 3){
								map[currRow-2][currCol] = 59;
							}
							else if(randomInt == 4){
								map[currRow-2][currCol] = 69;
							}
							else if(randomInt == 5){
								map[currRow-2][currCol] = 79;
							}
							else if(randomInt == 6){
								map[currRow-2][currCol] = 89;
							}
							else if(randomInt == 0){
								map[currRow-2][currCol] = 99;
							}
						
					}else if(map[currRow][currCol] == 49) {
							map[currRow][currCol] = 0;
							Player.setPistolAmmo(10);
							Stats.updateScore(50);
							
					}else if(map[currRow][currCol] == 59) {
							map[currRow][currCol] = 0;
						
							Player.setShotgunAmmo(5);
							Stats.updateScore(50);
					}else if(map[currRow][currCol] == 69) {
							map[currRow][currCol] = 0;
						
							Player.setMachineGunAmmo(20);
							Stats.updateScore(50);
					}else if(map[currRow][currCol] == 79) {
							map[currRow][currCol] = 0;
						
							Player.setBombAmmo(3);
							Stats.updateScore(50);
					}else if(map[currRow][currCol] == 89) {
							map[currRow][currCol] = 0;
						
							Player.setFlameBowAmmo(10);
							Stats.updateScore(50);
					}else if(map[currRow][currCol] == 99) {
							map[currRow][currCol] = 0;
						
							Player.setHealthKitAmmo(1);
							Stats.updateScore(50);
					}else if(map[currRow + 1][currCol] == 18){
						map[currRow +1][currCol] = 98;
						changeBlueBarrier = true;
						Stats.updateScore(100);
					}else if(map[currRow + 1][currCol] == 8){
						map[currRow + 1][currCol] = 88;
						changeRedBarrier = true;
						Stats.updateScore(100);
					}else if(map[currRow + 1][currCol] == 68){
						map[currRow + 1][currCol] = 98;
						changeGreenBarrier = true;
						Stats.updateScore(100);
					}
				Stats.updateStats();
				}
				
				g.drawImage(tiles[r][c].getImage(),	(int)x + col * tileSize, (int)y + row * tileSize, null);
				
			}
			
		}
		
	}
	
}
