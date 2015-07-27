package TileMap;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage image;
	private int type;
	private int number;
	
	// tile types
	public static final int NORMAL = 0;
	public static final int BLOCKED = 1;
	
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}
	public Tile(BufferedImage image, int type, int number) {
		this.image = image;
		this.type = type;
		this.number = number;
	}
	
	public BufferedImage getImage() { return image; }
	public int getType() { return type; }
	public int getNumber() { return number; }
	
}
