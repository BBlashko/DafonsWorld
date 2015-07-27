package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import GameState.GameStateManager;
import Main.Stats;

public class HUD {
	
	private Player player;
	private GameStateManager gsm;
	private Animation animation;
	private BufferedImage statsBar;
	private BufferedImage[] healthBar;
	private BufferedImage img;
	private BufferedImage[] weaponBar;
	private BufferedImage weapons;
	private BufferedImage[] powerBar;
	private BufferedImage powerBarSheet;
	private Font font;
	private Font statFont = new Font("Century Gothic", Font.BOLD, 14);
	private Font otherFont = new Font("Ariel", Font.BOLD, 24);
	private Font otherFont2 = new Font("Ariel", Font.BOLD, 18);
	private Color statColor = new Color(0,0,0);
	
	
	public HUD(Player p) {
		player = p;
		try {
			statsBar = ImageIO.read(getClass().getResourceAsStream("/HUD/statsBar.png"));
			
			healthBar = new BufferedImage[11];
			powerBarSheet = ImageIO.read(getClass().getResourceAsStream("/HUD/powerBar.png"));
			img = ImageIO.read(getClass().getResourceAsStream("/HUD/healthBar.png"));
			powerBar = new BufferedImage[95];
			
			for(int i = 0; i < powerBar.length; i++){
				powerBar[i] = powerBarSheet.getSubimage(0, i*32, 190, 16);
			}
			for(int i = 0; i < 11; i++){
				healthBar[i] = img.getSubimage(0, 0+ i*16 ,190, 16);
			}
			weaponBar = new BufferedImage[8];
			weapons = ImageIO.read(getClass().getResourceAsStream("/HUD/weaponBar.png"));
		
			for(int i = 0; i < 8; i++){
				weaponBar[i] = weapons.getSubimage(i*37, 0, 37, 38);
			}
					
		
		font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		animation = new Animation();
		animation.setDelay(0);
		animation.setFrames(powerBar);
		
		
	}
	public BufferedImage getHealthBarImage(int i) {
		return healthBar[i];
	}
	public void update(){
		if(player.getWeaponScroll() == 7){
			animation.update();
		}else{
			return;
		}
		
	}
	public void draw(Graphics2D g) {
		
		
		g.drawImage(statsBar, 0, 0, null);
		if(player.getWeaponScroll() == 7){
			if(player.getAttack()){
				animation.pause(animation.getFrame());
			}
				g.drawImage(animation.getImage(), 75, 665, null);
		}else{
			g.drawImage(powerBar[0], 75, 665, null);
		}
		if(player.getHealth() < 10){
			g.drawImage(healthBar[10], 75, 630, null);		
		}else if(player.getHealth() < 20){
			g.drawImage(healthBar[9], 75, 630, null);
		}else if(player.getHealth() < 30){
			g.drawImage(healthBar[8], 75, 630, null);
		}else if(player.getHealth() < 40){
			g.drawImage(healthBar[7], 75, 630, null);
		}else if(player.getHealth() < 50){
			g.drawImage(healthBar[6], 75, 630, null);		
		}else if(player.getHealth() < 60){
			g.drawImage(healthBar[5], 75, 630, null);		
		}else if(player.getHealth() < 70){
			g.drawImage(healthBar[4], 75, 630, null);			
		}else if(player.getHealth() < 80){
			g.drawImage(healthBar[3], 75, 630, null);
		}else if(player.getHealth() < 90){
			g.drawImage(healthBar[2], 75, 630, null);
		}else if(player.getHealth() < 100){
			g.drawImage(healthBar[1], 75, 630, null);
		}else {
			g.drawImage(healthBar[0], 75, 630, null);
		}
	
		g.setFont(statFont);
		g.setColor(Color.BLACK);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 279, 643);
		g.drawString(Stats.getTokens() + "", 900, 668);
		
		g.setColor(Color.WHITE);
		g.drawString(Stats.getScore() + "", 1095, 630);
		
		g.setColor(Color.WHITE);
		g.setFont(otherFont2);
		g.drawString("\u221E", 428, 618);
		g.drawString("" + Player.getPistolAmmo(), 475, 618);
		g.drawString("" + Player.getShotgunAmmo(), 525, 618);
		g.drawString("" + Player.getMachineGunAmmo(), 572, 618);
		g.drawString("" + Player.getBombAmmo(), 619, 618);
		g.drawString("" + Player.getFlameBowAmmo(), 666, 618);
		g.drawString("\u221E", 713, 618);
		g.drawString("" + Player.getHealthKitAmmo(), 760, 618);
		
		g.setFont(otherFont);
		g.setColor(Color.BLACK);
		g.drawString(Stats.getTotalDeaths() + "", 1075, 74);
		
		
		if(player.getWeaponScroll() == 1){
			g.drawImage(weaponBar[0],418, 621, null);
		}else if(player.getWeaponScroll() == 2){
			g.drawImage(weaponBar[1],465, 621, null);
		}else if(player.getWeaponScroll() == 3){
			g.drawImage(weaponBar[2],511, 621, null);
		}else if(player.getWeaponScroll() == 4){
			g.drawImage(weaponBar[3],558, 621, null);
		}else if(player.getWeaponScroll() == 5){
			g.drawImage(weaponBar[4],605, 621, null);
		}else if(player.getWeaponScroll() == 6){
			g.drawImage(weaponBar[5],652, 621, null);
		}else if(player.getWeaponScroll() == 7){
			g.drawImage(weaponBar[6],699, 621, null);
		}else if(player.getWeaponScroll() == 8){
			g.drawImage(weaponBar[7],746, 621, null);
		}
	}
	
}



