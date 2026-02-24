package com.mjsamaha.game.entity.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mjsamaha.game.Constants;
import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.util.UtilityTools;

public class Entity implements Movable, Collidable, Drawable {

	protected GamePanel gp;

	// Position and movement
	public int worldX, worldY;
	public int speed;
	public String direction = "down";
	public boolean collisionOn = false;

	// Sprites
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2;
	public BufferedImage attackLeft1, attackLeft2, attackRight1, attackRight2;

	// Collision
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;

	// Animation - now using controller
	protected AnimationController animationController;

	// Combat and state
	public boolean attacking = false;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	public boolean alive = true;
	public boolean dying = false;
	
	public boolean hpBarOn = false;

	// counter
	public int dyingCounter = 0;
	public int hpBarCounter = 0;

	// AI behavior
	public int actionLockCounter = 0;

	// Dialogue
	public String dialogue[] = new String[Constants.Dialogue.MAX_DIALOGUE_LINES];
	public int dialogueIndex = 0;

	// Stats
	public int maxHealth;
	public int health;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coins;
	public Entity currentWeapon;
	public Entity currentShield;
	
	public int attackValue;
	public int defenseValue;
	
	public String description = "";

	// Metadata
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int type; // 0 = player, 1 = npc, 2 = monster

	/**
	 * Creates a new Entity with default animation controller
	 */
	public Entity(GamePanel gp) {
		this.gp = gp;
		this.animationController = new AnimationController();
	}

	// Movable interface implementation
	@Override
	public int getWorldX() {
		return worldX;
	}

	@Override
	public int getWorldY() {
		return worldY;
	}

	@Override
	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	@Override
	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public String getDirection() {
		return direction;
	}

	@Override
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public boolean isCollisionOn() {
		return collisionOn;
	}

	@Override
	public void setCollisionOn(boolean collisionOn) {
		this.collisionOn = collisionOn;
	}

	// Collidable interface implementation
	@Override
	public Rectangle getSolidArea() {
		return solidArea;
	}

	@Override
	public int getSolidAreaDefaultX() {
		return solidAreaDefaultX;
	}

	@Override
	public int getSolidAreaDefaultY() {
		return solidAreaDefaultY;
	}

	/**
	 * Sets AI behavior for this entity. Override in subclasses.
	 */
	public void setAction() {
		// Override in subclasses
	}

	/**
	 * Handles dialogue interaction. Can be overridden for custom behavior.
	 */
	public void speak() {
		// Face the player
		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}
	
	public void damageReaction() {}

	public String getDialogue() {
		if (dialogueIndex >= dialogue.length || dialogue[dialogueIndex] == null) {
			dialogueIndex = 0; // reset if end reached
		}
		return dialogue[dialogueIndex++];
	}

	/**
	 * Updates entity state. Override in subclasses for custom behavior.
	 */
	public void update() {
		setAction();

		// Check collisions
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		// Monster collision with player
		if (this.type == 2 && contactPlayer == true) {
		    // Don't damage player if they're currently attacking
		    if (gp.player.invincible == false && gp.player.attacking == false) {
		        gp.playSE(SoundEvent.SFX_RECEIVE_DAMAGE); // Changed from SFX_HIT_MONSTER
		        
		        int damage = attack - gp.player.defense;
		        if (damage < 0) {
		            damage = 0;
		        }
		        gp.player.health -= damage;

		        gp.player.invincible = true;
		    }
		}

		// Apply movement
		if (collisionOn == false) {
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}

		// Update animation
		animationController.update();

		// Update invincibility
		if (invincible == true) {
			invincibleCounter++;
			if (invincibleCounter > 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}

	/**
	 * Draws the entity on screen.
	 */
	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		// Only draw if entity is within screen bounds
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			// Use animation controller to get the correct sprite
			image = animationController.getSprite(direction, up1, up2, down1, down2, left1, left2, right1, right2);
			
			// Monster HP Bar
			if (type == 2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize / maxHealth;
				double hpBarValue = oneScale * health;
				
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX-1, screenY - 16, gp.tileSize+2, 12);
				
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
				
				hpBarCounter++;
				
				if (hpBarCounter > 480) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			

			// Apply invincibility transparency
			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4f);
			}

			if (dying == true) {
				// death animation
				dyingAnimation(g2);
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			// Reset composite
			changeAlpha(g2, 1f);

		}
	}

	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;
		
		int i = 5;

		if (dyingCounter <= i) {
			changeAlpha(g2, 0f);
		}
		
		if (dyingCounter > i && dyingCounter <= i*2) {
			changeAlpha(g2, 1f);

		}
		if (dyingCounter > i*2 && dyingCounter <= i*3) {
			changeAlpha(g2, 0f);

		}
		if (dyingCounter > i*3 && dyingCounter <= i*4) {
			changeAlpha(g2, 1f);

		}
		if (dyingCounter > i*4 && dyingCounter <= i*5) {
			changeAlpha(g2, 0f);

		}
		if (dyingCounter > i*6 && dyingCounter <= i*7) {
			changeAlpha(g2, 1f);

		}
		if (dyingCounter > i*7 && dyingCounter <= i*8) {
			changeAlpha(g2, 0f);

		}
		if (dyingCounter > i*8 && dyingCounter <= i*9) {
			changeAlpha(g2, 1f);

		}

		if (dyingCounter > i*9) {
			dying = false;
			alive = false;
		}

	}

	public void changeAlpha(Graphics2D g2, float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

	}

	/**
	 * Utility method to load and scale images
	 */
	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTools uTool = new UtilityTools();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaledImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * Gets the animation controller for this entity
	 */
	protected AnimationController getAnimationController() {
		return animationController;
	}
}
