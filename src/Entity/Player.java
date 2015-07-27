package Entity;

import Audio.AudioOut;
import Entity.Enemies.EnemyBullet;
import Entity.Enemies.Eye;
import Entity.Enemies.Laser;
import GameState.GameStateManager;
import Main.Stats;
import TileMap.*;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends MapObject {

	// player stuff
	private static int health;
	private int maxHealth;
	private boolean fire;
	private int maxFire;
	private boolean dead = false;
	private boolean flinching;
	private long flinchTimer;
	private static int tokens = 0;
	private static int score = 0;

	public boolean playerLoaded = false;
	// Bullets
	private boolean firing;
	private int bulletCost;
	private int bulletDamage;
	private ArrayList<Bullet> bullets;

	// Arrows
	private int arrowDamage;
	private ArrayList<Arrow> arrows;

	// bomb
	private int bombDamage;
	private ArrayList<Bomb> bombs;

	// ShotgunShot
	private int shotgunDamage;
	private ArrayList<Shotgunshot> shots;

	// rocks
	private ArrayList<Rock> rocks;

	// animations
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = { 1, 1, 1, 1, 1, 1, 1, 1, 8, 8, 8, 8, 8, 8,
			8, 8, 1, 1, 1, 1, 1, 1, 1, 1, 6 };

	// animation actions
	private static final int SWORD_IDLE = 0;
	private static final int PISTOL_IDLE = 1;
	private static final int SHOTGUN_IDLE = 2;
	private static final int MACHINEGUN_IDLE = 3;
	private static final int BOMB_IDLE = 4;
	private static final int FLAMEBOW_IDLE = 5;
	private static final int ROCK_IDLE = 6;
	private static final int HEALTHKIT_IDLE = 7;
	private static final int SWORD_WALKING = 8;
	private static final int PISTOL_WALKING = 9;
	private static final int SHOTGUN_WALKING = 10;
	private static final int MACHINEGUN_WALKING = 11;
	private static final int BOMB_WALKING = 12;
	private static final int FLAMEBOW_WALKING = 13;
	private static final int ROCK_WALKING = 14;
	private static final int HEALTHKIT_WALKING = 15;
	private static final int SWORD_JUMPING = 16;
	private static final int PISTOL_JUMPING = 17;
	private static final int SHOTGUN_JUMPING = 18;
	private static final int MACHINEGUN_JUMPING = 19;
	private static final int BOMB_JUMPING = 10;
	private static final int FLAMEBOW_JUMPING = 21;
	private static final int ROCK_JUMPING = 22;
	private static final int HEALTHKIT_JUMPING = 23;
	private static final int SWORD_ATTACK_1 = 24;

	// Weapon Select
	private boolean attack = false;
	private static boolean sword = false;
	private static boolean rock = false;
	private static boolean bomb = false;
	private static boolean shotgun = false;
	private static boolean machineGun = false;
	private static boolean pistol = false;
	private static boolean flameBow = false;
	private static boolean healthKit = false;
	private static int weaponScroll = 1;
	private static boolean throwRock = false;

	// weapon ammo
	private static int bombAmmo = 10;
	private static int pistolAmmo = 10;
	private static int shotgunAmmo = 10;
	private static int machineGunAmmo = 20;
	private static int flameBowAmmo = 5;
	private static int healthKitAmmo = 0;

	public ArrayList<EnemyBullet> eBullets;

	private GameStateManager gsm;
	private Stats stats;
	private AudioOut audio;
	private AudioOut bowSound;
	private AudioOut pistolSound;
	private AudioOut shotgunSound;
	private AudioOut machinegunSound;
	private AudioOut explode;
	private AudioOut land;
	private AudioOut swordSwipe1;

	private boolean onPlatform = false;
	private int currentPlatform = -1;

	public Player(TileMap tm) {

		super(tm);

		Eye eye;

		width = 76;
		height = 76;
		cwidth = 20;
		cheight = 60;

		moveSpeed = .5;
		maxSpeed = 3;
		stopSpeed = .75;
		fallSpeed = 0.25;
		maxFallSpeed = 5.0;
		jumpStart = -6.5;
		stopJumpSpeed = 0.10;

		swimSpeed = .5;
		maxSwimSpeed = 2;
		sinkSpeed = 0.25;
		maxSinkSpeed = 2;
		riseSpeed = -0.3;
		maxRiseSpeed = -1;

		setFacingRight(true);

		health = maxHealth = 400;

		bulletCost = 1;
		bulletDamage = 1;
		bullets = new ArrayList<Bullet>();

		arrowDamage = 2;
		arrows = new ArrayList<Arrow>();

		bombDamage = 3;
		bombs = new ArrayList<Bomb>();

		shotgunDamage = 2;
		shots = new ArrayList<Shotgunshot>();

		rocks = new ArrayList<Rock>();

		bowSound = new AudioOut("/SFX/Archery arrow release.mp3");
		pistolSound = new AudioOut("/SFX/Gun Shot Sound Effect.mp3");
		shotgunSound = new AudioOut("/SFX/Shotgun blast.mp3");
		machinegunSound = new AudioOut(
				"/SFX/Machine Gun Burst Sound Effect.mp3");
		explode = new AudioOut("/SFX/Explosion, explode.mp3");
		land = new AudioOut("/SFX/Video Game Jump Landing Sound Effect.mp3");
		swordSwipe1 = new AudioOut("/SFX/Sword Swipe Sound.mp3");
		audio = new AudioOut("/SFX/Shotgun blast.mp3");

		tokens = Stats.getTokens();
		// load sprites
		try {

			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/Sprites/Player/DafonCharacter.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 25; i++) {

				BufferedImage[] bi = new BufferedImage[numFrames[i]];

				for (int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(j * width, i * height,
							width, height);

				}

				sprites.add(bi);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = SWORD_IDLE;
		animation.setFrames(sprites.get(SWORD_IDLE));
		animation.setDelay(400);

		playerLoaded = true;
	}

	public void setThrowRock(boolean b) {
		throwRock = b;
	}

	public boolean getLeft() {
		return left;
	}

	public boolean getRight() {
		return right;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean getFire() {
		return fire;
	}

	public int getMaxFire() {
		return maxFire;
	}

	public int getScore() {
		return score;
	}

	public int getTokens() {
		return tokens;
	}

	// set Weapon Ammo
	public static void setPistolAmmo(int num) {
		pistolAmmo += num;
	}

	public static void setShotgunAmmo(int num) {
		shotgunAmmo += num;
	}

	public static void setMachineGunAmmo(int num) {
		machineGunAmmo += num;
	}

	public static void setBombAmmo(int num) {
		bombAmmo += num;
	}

	public static void setFlameBowAmmo(int num) {
		flameBowAmmo += num;
	}

	public static void setHealthKitAmmo(int num) {
		healthKitAmmo += num;
	}

	// get Weapon Ammo
	public static int getPistolAmmo() {
		return pistolAmmo;
	}

	public static int getShotgunAmmo() {
		return shotgunAmmo;
	}

	public static int getMachineGunAmmo() {
		return machineGunAmmo;
	}

	public static int getBombAmmo() {
		return bombAmmo;
	}

	public static int getFlameBowAmmo() {
		return flameBowAmmo;
	}

	public static int getHealthKitAmmo() {
		return healthKitAmmo;
	}

	// set current Weapon
	public void setRock(boolean b) {
		rock = b;
	}

	public void setSword(boolean b) {
		sword = b;
	}

	public void setBomb(boolean b) {
		bomb = b;
	}

	public void setMachineGun(boolean b) {
		machineGun = b;
	}

	public void setShotgun(boolean b) {
		shotgun = b;
	}

	public void setFlameBow(boolean b) {
		flameBow = b;
	}

	public void setHealthKit(boolean b) {
		healthKit = b;
	}

	public void setPistol(boolean b) {
		pistol = b;
	}

	// get current Weapon
	public static boolean getSword() {
		return sword;
	}

	public static boolean getThrowRock() {
		return throwRock;
	}

	public static boolean getBomb() {
		return bomb;
	}

	public static boolean getMachineGun() {
		return machineGun;
	}

	public static boolean getShotgun() {
		return shotgun;
	}

	public static boolean getFlameBow() {
		return flameBow;
	}

	public static boolean getHealthKit() {
		return healthKit;
	}

	public static boolean getPistol() {
		return pistol;
	}

	public void setWeaponScroll(int num) {
		weaponScroll = num;
	}

	public void updateWeaponScroll(int num) {
		weaponScroll += num;
		if (weaponScroll > 8) {
			weaponScroll = 1;
		}
		if (weaponScroll < 1) {
			weaponScroll = 8;
		}
	}

	public boolean getPlayerLoaded() {
		return playerLoaded;
	}

	public int getWeaponScroll() {
		return weaponScroll;
	}

	public void setSwordAttack() {
		sword = true;
	}

	public static void updateScore(int num) {
		score += num;
	}

	public static void updateTokens(int num) {
		tokens += num;
	}

	public void setFiring(boolean b) {
		firing = b;
		if (firing) {
			attack();

		} else {
			return;
		}
	}

	public int getdx() {
		return (int) dx;
	}

	public static void updateHealth(int num) {
		health += num;
	}

	public void checkAttack(ArrayList<Enemy> enemies, ArrayList<Laser> lasers) {

		// loop through enemies
		for (int i = 0; i < enemies.size(); i++) {

			Enemy e = enemies.get(i);
			// bullets
			for (int j = 0; j < bullets.size(); j++) {
				if (bullets.get(j).intersects(e)) {
					e.hit(bulletDamage);
					bullets.get(j).setHit();
					break;
				}
			}
			// bombs
			for (int j = 0; j < bombs.size(); j++) {
				if (bombs.get(j).intersects(e)) {
					e.hit(bombDamage);
					bombs.get(j).setHit();
					break;
				}
			}
			// arrows
			for (int j = 0; j < arrows.size(); j++) {
				if (arrows.get(j).intersects(e)) {
					e.hit(arrowDamage);
					arrows.get(j).setHit();
					break;
				}
			}
			// shotgun
			for (int j = 0; j < shots.size(); j++) {
				if (shots.get(j).intersects(e)) {
					e.hit(shotgunDamage);
					break;
				}
			}
			// check enemy collision
			if (intersects(e)) {
				hit(e.getDamage());
			}

		}
		for (int i = 0; i < lasers.size(); i++) {

			Laser ls = lasers.get(i);

			if (intersectsLaser(ls)) {
				hit(ls.getDamage());
			}
		}
	}

	public void checkEnemyAttack(ArrayList<EnemyBullet> ebullets) {

		for (int i = 0; i < ebullets.size(); i++) {
			EnemyBullet e = ebullets.get(i);
			if (intersects(e)) {
				e.setHit();
				hit(e.getDamage());
			}

		}
	}

	public void hit(int damage) {
		if (flinching)
			return;
		health -= damage;
		if (health < 0) {
			health = 0;
		}
		flinching = true;
		flinchTimer = System.nanoTime();
	}

	public void checkPlatforms(ArrayList<Platform> platforms) {

		for (int i = 0; i < platforms.size(); i++) {

			Platform p = platforms.get(i);

			if (this.intersectsBottom(p)) {
				onPlatform = true;
				currentPlatform = i;
				falling = false;
				bottomLeft = bottomRight = true;
				dy = 0;
				dx = p.getdx();

				if (left) {
					dx -= moveSpeed + 1;

					if (dx < -maxSpeed + 1) {
						dx = -maxSpeed + 1;
					}
				} else if (right) {
					dx += moveSpeed + 2;
					if (dx > maxSpeed + 2) {
						dx = maxSpeed + 2;
					}
				}
			} else {
				if (i == currentPlatform) {
					onPlatform = false;
				}

			}

		}
	}

	public void checkRockPlatforms(ArrayList<RockPlatform> rockPlatforms) {

		for (int i = 0; i < rockPlatforms.size(); i++) {

			RockPlatform rp = rockPlatforms.get(i);

			if (this.intersectsBottom(rp) && rp.isPlayedonce() != true) {
				dy = 0;
				falling = false;
				bottomLeft = bottomRight = true;
				rp.setSteppedOn();
			}

		}
	}

	public void checkBoxes(ArrayList<Box> boxes) {
		for (int i = 0; i < boxes.size(); i++) {

			Box b = boxes.get(i);

			if (this.intersects(b)) {
				b.setMoveBox(true);
				b.update();
				dy = 0;
				topBox = true;
				falling = false;
			} else {
				b.setMoveBox(false);
				topBox = false;
			}
		}

	}

	private void getNextPosition() {

		// movement
		if (!swimming) {
			if (!onPlatform) {
				if (left) {
					dx -= moveSpeed;
					if (dx < -maxSpeed) {
						dx = -maxSpeed;
					}
				} else if (right) {
					dx += moveSpeed;
					if (dx > maxSpeed) {
						dx = maxSpeed;
					}
				} else {

					if (dx > 0) {
						dx -= stopSpeed;
						if (dx > 0) {
							dx = 0;
						}
					} else if (dx < 0) {
						dx += stopSpeed;
						if (dx > 0) {
							dx = 0;
						}

					}
				}
			}
			if (jumping && !falling) {
				dy = jumpStart;
				falling = true;
			}
			if (falling) {
				dy += fallSpeed;

				if (dy > 0) {
					jumping = false;
				}
				if (dy < 0 && !jumping) {
					dy += stopJumpSpeed;
				}

			}
		} else if (swimming) {
			jumping = false;
			falling = false;
			if (up) {
				dy += riseSpeed;
				if (dy > maxRiseSpeed) {
					dy = maxRiseSpeed;
				}
			} else {
				dy += sinkSpeed;
				if (dy > maxSinkSpeed) {
					dy = maxSinkSpeed;
				}
			}
			if (left) {
				dx -= moveSpeed;
				if (dx < -maxSpeed) {
					dx = -maxSpeed;
				}
			} else if (right) {
				dx += moveSpeed;
				if (dx > maxSpeed) {
					dx = maxSpeed;
				}
			} else {
				if (dx > 0) {
					dx -= stopSpeed;
					if (dx > 0) {
						dx = 0;
					}
				} else if (dx < 0) {
					dx += stopSpeed;
					if (dx > 0) {
						dx = 0;
					}
				}
			}
		}
	}

	public void attack() {

		attack = true;

		if (weaponScroll == 7) {
			if (currentAction != ROCK_WALKING) {
				currentAction = ROCK_WALKING;
			}
			Rock r = new Rock(tileMap);
			r.setPosition(x, y);
			rocks.add(r);
		}
		// machinegun attack
		if (weaponScroll == 4) {
			if (currentAction != MACHINEGUN_WALKING) {
				currentAction = MACHINEGUN_WALKING;
			}

			if (machineGunAmmo >= 3) {
				machineGunAmmo -= 3;
				machinegunSound.play();
				for (int i = 0; i < 3; i++) {
					Bullet b = new Bullet(tileMap, getFacingRight());
					b.setPosition(x, y);
					bullets.add(b);
					Stats.updateBulletsFired(1);
				}
			} else {
				machineGunAmmo = 0;
			}
		}
		// pistol attack
		if (weaponScroll == 2) {
			if (currentAction != PISTOL_WALKING) {
				currentAction = PISTOL_WALKING;
			}

			if (pistolAmmo >= bulletCost) {
				pistolAmmo -= bulletCost;
				pistolSound.play();
				Bullet b = new Bullet(tileMap, getFacingRight());
				b.setPosition(x, y);
				bullets.add(b);
				Stats.updateBulletsFired(1);
			} else {
				pistolAmmo = 0;
			}
		}
		// bomb attack
		if (weaponScroll == 5) {
			if (currentAction != BOMB_WALKING) {
				currentAction = BOMB_WALKING;
			}
			if (bombAmmo >= bulletCost) {
				bombAmmo -= bulletCost;
				Bomb bomb = new Bomb(tileMap, getFacingRight());
				bomb.setPosition(x, y);
				bombs.add(bomb);
				Stats.updateBombsThrown();
			}
		}
		// flamebow attack
		if (weaponScroll == 6) {

			if (currentAction != FLAMEBOW_WALKING) {
				currentAction = FLAMEBOW_WALKING;
			}

			if (flameBowAmmo >= bulletCost) {
				bowSound.play();
				flameBowAmmo -= bulletCost;
				Arrow a = new Arrow(tileMap, getFacingRight());
				a.setPosition(x, y);
				arrows.add(a);
				Stats.updateArrowShot();
			}
		}
		// shotgun attack
		if (weaponScroll == 3) {

			if (currentAction != SHOTGUN_WALKING) {
				currentAction = SHOTGUN_WALKING;
			}

			if (shotgunAmmo >= bulletCost) {
				shotgunSound.play();
				shotgunAmmo -= bulletCost;
				Shotgunshot sh = new Shotgunshot(tileMap, getFacingRight());
				if (getFacingRight()) {
					sh.setPosition(x + 37, y - 1);
				} else {
					sh.setPosition(x - 37, y - 1);
				}
				shots.add(sh);
				Stats.updateShotgunsFired();
			}
			/*
			 * if(healthKitAmmo >= 1) { healthKitAmmo -= 1; if(health <=
			 * maxHealth - 50) health += 50; else{ health = maxHealth; } }
			 */
		}
		if (weaponScroll == 8) {
			if (healthKitAmmo >= 1) {
				healthKitAmmo -= 1;
				health += 25;
				Stats.updateHealthKitsUsed();
			} else {
				healthKitAmmo = 0;
			}

		}
	}

	public void update() {

		// update position
		getNextPosition();

		checkTileMapCollisionPlayer();
		setPosition(xtemp, ytemp);
		Eye.checkPlayerHit(this);

		if (health < 0) {
			health = 0;
			StartRestartState();
			Stats.updateTotalDeaths();
			dead = true;

		}
		if (dy > 7) {
			double temp = dy;
			if (bottomLeft || bottomRight) {
				health -= (temp - 7 * 2);
				flinching = true;
			}
		}
		// update bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update();
			if (bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}

		// update bombs
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).update();
			if (bombs.get(i).shouldRemove()) {
				bombs.remove(i);
				i--;
			}
		}
		// update arrows
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).update();
			if (arrows.get(i).shouldRemove()) {
				arrows.remove(i);
				i--;
			}
		}

		// update shotgun shots
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).update();
			if (shots.get(i).shouldRemove()) {
				shots.remove(i);
				i--;
			}
		}
		// update rocks
		for (int i = 0; i < rocks.size(); i++) {
			rocks.get(i).update();
		}
		// check done flinching
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
				flinching = false;
			}
		}
		// set animation
		if (throwRock && weaponScroll == 7) {
			Rock r = new Rock(tileMap);
			r.setPosition(x, y);
			rocks.add(r);
			throwRock = false;

		} else {
			throwRock = false;
		}
		if (sword) {
			if (weaponScroll == 1 && currentAction != SWORD_ATTACK_1) {
				// stats.updateSwordAttacks();
				currentAction = SWORD_ATTACK_1;
				animation.setFrames(sprites.get(SWORD_ATTACK_1));
				animation.setDelay(40);
				swordSwipe1.play();
			}
			if (animation.hasPlayedOnce()) {
				sword = false;
			}
		} else if (dy > 0 || dy < 0) {
			if (weaponScroll == 1 && currentAction != SWORD_JUMPING) {
				currentAction = SWORD_JUMPING;
				animation.setFrames(sprites.get(SWORD_JUMPING));
				animation.setDelay(1);

			} else if (weaponScroll == 2 && currentAction != PISTOL_JUMPING) {
				currentAction = PISTOL_JUMPING;
				animation.setFrames(sprites.get(PISTOL_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 3 && currentAction != SHOTGUN_JUMPING) {
				currentAction = SHOTGUN_JUMPING;
				animation.setFrames(sprites.get(SHOTGUN_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 4 && currentAction != MACHINEGUN_JUMPING) {
				currentAction = MACHINEGUN_JUMPING;
				animation.setFrames(sprites.get(MACHINEGUN_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 5 && currentAction != BOMB_JUMPING) {
				currentAction = BOMB_JUMPING;
				animation.setFrames(sprites.get(BOMB_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 6 && currentAction != FLAMEBOW_JUMPING) {
				currentAction = FLAMEBOW_JUMPING;
				animation.setFrames(sprites.get(FLAMEBOW_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 7 && currentAction != ROCK_JUMPING) {
				currentAction = ROCK_JUMPING;
				animation.setFrames(sprites.get(ROCK_JUMPING));
				animation.setDelay(1);
			} else if (weaponScroll == 8 && currentAction != HEALTHKIT_JUMPING) {
				currentAction = HEALTHKIT_JUMPING;
				animation.setFrames(sprites.get(HEALTHKIT_JUMPING));
				animation.setDelay(1);
			}
		} else if (left || right) {
			if (weaponScroll == 1 && currentAction != SWORD_WALKING) {
				currentAction = SWORD_WALKING;
				animation.setFrames(sprites.get(SWORD_WALKING));
				animation.setDelay(50);

			} else if (weaponScroll == 2 && currentAction != PISTOL_WALKING) {
				currentAction = PISTOL_WALKING;
				animation.setFrames(sprites.get(PISTOL_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 3 && currentAction != SHOTGUN_WALKING) {
				currentAction = SHOTGUN_WALKING;
				animation.setFrames(sprites.get(SHOTGUN_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 4 && currentAction != MACHINEGUN_WALKING) {
				currentAction = MACHINEGUN_WALKING;
				animation.setFrames(sprites.get(MACHINEGUN_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 5 && currentAction != BOMB_WALKING) {
				currentAction = BOMB_WALKING;
				animation.setFrames(sprites.get(BOMB_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 6 && currentAction != FLAMEBOW_WALKING) {
				currentAction = FLAMEBOW_WALKING;
				animation.setFrames(sprites.get(FLAMEBOW_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 7 && currentAction != ROCK_WALKING) {
				currentAction = ROCK_WALKING;
				animation.setFrames(sprites.get(ROCK_WALKING));
				animation.setDelay(50);
			} else if (weaponScroll == 8 && currentAction != HEALTHKIT_WALKING) {
				currentAction = HEALTHKIT_WALKING;
				animation.setFrames(sprites.get(HEALTHKIT_WALKING));
				animation.setDelay(50);

			}

		} else {
			if (weaponScroll == 1) {
				currentAction = SWORD_IDLE;
				animation.setFrames(sprites.get(SWORD_IDLE));
				animation.setDelay(1);

			} else if (weaponScroll == 2) {
				currentAction = PISTOL_IDLE;
				animation.setFrames(sprites.get(PISTOL_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 3) {
				currentAction = SHOTGUN_IDLE;
				animation.setFrames(sprites.get(SHOTGUN_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 4) {
				currentAction = MACHINEGUN_IDLE;
				animation.setFrames(sprites.get(MACHINEGUN_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 5) {
				currentAction = BOMB_IDLE;
				animation.setFrames(sprites.get(BOMB_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 6) {
				currentAction = FLAMEBOW_IDLE;
				animation.setFrames(sprites.get(FLAMEBOW_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 7) {
				currentAction = ROCK_IDLE;
				animation.setFrames(sprites.get(ROCK_IDLE));
				animation.setDelay(100);
			} else if (weaponScroll == 8) {
				currentAction = HEALTHKIT_IDLE;
				animation.setFrames(sprites.get(HEALTHKIT_IDLE));
				animation.setDelay(100);
			}
		}

		animation.update();

		// set direction
		if (right)
			setFacingRight(true);
		if (left)
			setFacingRight(false);

	}

	public void StartRestartState() {

	}

	public void draw(Graphics2D g) {

		setMapPosition();
		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		// draw arrows
		for (int i = 0; i < arrows.size(); i++) {
			arrows.get(i).draw(g);
		}
		// draw bombs
		for (int i = 0; i < bombs.size(); i++) {
			bombs.get(i).draw(g);
		}
		// draw shots
		for (int i = 0; i < shots.size(); i++) {
			shots.get(i).draw(g);
		}
		// draw rocks
		for (int i = 0; i < rocks.size(); i++) {
			rocks.get(i).draw(g);
		}
		// draw player
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed / 100 % 2 == 0) {
				return;
			}
		}
		// g.drawArc((int)(x + xmap - width/2), (int)(y + ymap - height /2),
		// 200, 200, 60, 100);
		if (getFacingRight()) {
			g.drawImage(animation.getImage(), (int) (x + xmap - width / 2),
					(int) (y + ymap - height / 2 - 8), null);

		} else {
			g.drawImage(animation.getImage(),
					(int) (x + xmap - width / 2 + width), (int) (y + ymap
							- height / 2 - 8), -width, height, null);
		}

	}

	public boolean getDead() {
		return dead;
	}

	public boolean getAttack() {

		return attack;
	}

	public void setDead(boolean b) {
		dead = b;

	}

	public double getYmap() {
		return ymap;
	}
}
