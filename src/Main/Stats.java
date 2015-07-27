package Main;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Stats {
	private static String title;
	private static int numTimesPlayed;
	private static int timePlayed;
	private static int tokens;
	private static int score;
	private static int totalDeaths;
	private static int lives;
	private static int chests;
	private static int eyesKilled;
	private static int snakesKilled;
	private static int bulletsFired;
	private static int arrowShots;
	private static int bombsThrown;
	private static int healthKitsUsed;
	private static int shotgunsFired;
	private static int buttonsPressed;
	private static int swordAttacks;
	
	private static File file = new File("Resources/Stats/Statistics.txt");
	
	public Stats(){
		try{
			Scanner stdin = new Scanner(file);
			stdin.useDelimiter("\\s+=\\s+");
			title = stdin.nextLine();
			while (stdin.hasNext()) {
				String string = stdin.next();
				
				if (string.equals("numTimesPlayed")) {
					numTimesPlayed = stdin.nextInt();
					continue;
				}if (string.equals("timePlayed")) {
					timePlayed = stdin.nextInt();
					continue;
				}if (string.equals("tokens")) {
					tokens = stdin.nextInt();
					continue;
				}if (string.equals("score")) {
					score = stdin.nextInt();
					continue;
				}if (string.equals("totalDeaths")) {
					totalDeaths = stdin.nextInt();
					continue;
				}if (string.equals("lives")) {
					lives = stdin.nextInt();
					continue;
				}if (string.equals("chests")) {
					chests = stdin.nextInt();
					continue;
				}if (string.equals("eyesKilled")) {
					eyesKilled = stdin.nextInt();
					continue;
				}if (string.equals("snakesKilled")) {
					snakesKilled = stdin.nextInt();
					continue;
				}if (string.equals("bulletsFired")) {
					bulletsFired = stdin.nextInt();
					continue;
				}if (string.equals("arrowShots")) {
					arrowShots = stdin.nextInt();
					continue;
				}if (string.equals("bombsThrown")) {
					bombsThrown = stdin.nextInt();
					continue;
				}if (string.equals("healthKitsUsed")) {
					healthKitsUsed = stdin.nextInt();
					continue;
				}if (string.equals("shotgunsFired")) {
					shotgunsFired = stdin.nextInt();
					continue;
				}if (string.equals("buttonsPressed")) {
					buttonsPressed = stdin.nextInt();
					continue;
				}if (string.equals("swordAttacks")) {
					swordAttacks = stdin.nextInt();
					continue;
				}
			}
			stdin.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Satistics has initialized.");
		numTimesPlayed++;
	}
	
	public static void updateStats(){
		try{
			PrintWriter output = new PrintWriter(file);
			output.println(title);
			output.println("numTimesPlayed = " + numTimesPlayed + " = ");
			output.println("timePlayed = " + timePlayed + " = ");
			output.println("tokens = " + tokens + " = ");
			output.println("score = " + score + " = ");
			output.println("totalDeaths = " + totalDeaths + " = ");
			output.println("lives = " + lives + " = ");
			output.println("chests = " + chests + " = ");
			output.println("eyesKilled = " + eyesKilled + " = ");
			output.println("snakesKilled = " + snakesKilled + " = ");
			output.println("bulletsFired = " + bulletsFired + " = ");
			output.println("arrowShots = " + arrowShots + " = ");
			output.println("bombsThrown = " + bombsThrown + " = ");
			output.println("healthKitsUsed = " + healthKitsUsed + " = ");
			output.println("shotgunsFired = " + shotgunsFired + " = ");
			output.println("buttonsPressed = " + buttonsPressed + " = ");
			output.println("swordAttacks = " + swordAttacks + " = ");
			output.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		System.out.println("Statistics Updated");
	}
	public static void updateNumTimesPlayed(){
		numTimesPlayed ++;
	}
	public static int getNumTimesPlayed(){
		return numTimesPlayed;
	}
	public static void updateTimePlayed(int time){
		timePlayed += time;
	}
	public static int getTimePlayed(){
		return timePlayed;
	}
	public static void updateTokens(){
		tokens++;
	}
	public static int getTokens(){
		return tokens;
	}
	public static void updateScore(int num){
		score += num;
	}
	public static int getScore(){
		return score;
	}
	public static void updateTotalDeaths(){
		totalDeaths++;
	}
	public static int getTotalDeaths(){
		return totalDeaths;
	}
	public static void updateLives(){
		lives--;
	}
	public static int getLives(){
		return lives;
	}
	public static void updateChests(){
		chests += 1;
	}
	public static int getChests(){
		return chests;
	}
	public static void updateEyesKilled(){
		eyesKilled--;
	}
	public static int getEyesKilled(){
		return eyesKilled;
	}
	public static void updateSnakesKilled(){
		snakesKilled--;
	}
	public static int getSnakesKilled(){
		return snakesKilled;
	}
	public static void updateBulletsFired(int num){
		bulletsFired += num;
	}
	public static int getBulletsFired(){
		return bulletsFired;
	}
	public static void updateArrowShot(){
		arrowShots++;
	}
	public static int getArrowShot(){
		return arrowShots;
	}
	public static void updateBombsThrown(){
		bombsThrown++;
	}
	public static int getBombsThrown(){
		return bombsThrown;
	}
	public static void updateHealthKitsUsed(){
		healthKitsUsed++;
	}
	public static int getHealthKitsUsed(){
		return healthKitsUsed;
	}
	public static void updateShotgunsFired(){
		shotgunsFired++;
	}
	public static int getShotgunsFired(){
		return shotgunsFired;
	}
	public static void updateButtonsPressed(){
		buttonsPressed++;
	}
	public static int getButtonsPressed(){
		return buttonsPressed;
	}
	public static void updateSwordAttacks(){
		swordAttacks++;
	}
	public static int getSwordAttacks(){
		return swordAttacks;
	}
}