package Entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Entity.Enemies.EnemyBullet;
import TileMap.TileMap;

public class Enemy extends MapObject{
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected static int damage;
	
	protected boolean flinching;
	protected long flinchTimer;
	
	public Enemy(TileMap tm){
		super(tm);
	}
	
	public boolean isDead() { return dead;}
	
	public int getDamage() { return damage;}
	
	public void getNextPosition(){
		
	}
	public void hit(int damage){
		if(dead) return;
		health -= damage;
		if(health < 0){
			health = 0;
		}
		if(health == 0){
			dead = true;
		}
		return;
		//flinching = true;
		//flinchTimer = System.nanoTime();
	}
	public void update() {
		
	}
	public void draw(Graphics2D g) {
		setMapPosition();
		
		super.draw(g);
		
	}
}
