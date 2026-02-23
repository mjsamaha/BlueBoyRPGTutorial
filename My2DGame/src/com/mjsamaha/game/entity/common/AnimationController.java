package com.mjsamaha.game.entity.common;

import java.awt.image.BufferedImage;

import com.mjsamaha.game.Constants;

public class AnimationController {
    
    private int spriteCounter;
    private int spriteNum;
    private final int spriteChangeInterval;
    
    /**
     * Creates an AnimationController with default animation speed
     */
    public AnimationController() {
        this(Constants.Animation.SPRITE_CHANGE_INTERVAL);
    }
    
    /**
     * Creates an AnimationController with custom animation speed
     * @param spriteChangeInterval Frames between sprite changes
     */
    public AnimationController(int spriteChangeInterval) {
        this.spriteChangeInterval = spriteChangeInterval;
        this.spriteCounter = 0;
        this.spriteNum = 1;
    }
    
    /**
     * Updates the animation. Call this once per frame when the entity is moving/animating.
     */
    public void update() {
        spriteCounter++;
        
        if (spriteCounter > spriteChangeInterval) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }
    
    /**
     * Gets the current sprite number (1 or 2)
     */
    public int getSpriteNum() {
        return spriteNum;
    }
    
    /**
     * Sets the sprite number directly (useful for attack animations)
     */
    public void setSpriteNum(int spriteNum) {
        this.spriteNum = spriteNum;
    }
    
    /**
     * Resets the animation to frame 1
     */
    public void reset() {
        spriteCounter = 0;
        spriteNum = 1;
    }
    
    /**
     * Gets the current sprite counter value
     */
    public int getSpriteCounter() {
        return spriteCounter;
    }
    
    /**
     * Sets the sprite counter directly (useful for custom animation timing)
     */
    public void setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
    }
    
    /**
     * Selects the appropriate sprite based on direction and current animation frame
     * @param direction Current facing direction ("up", "down", "left", "right")
     * @param up1 First up sprite
     * @param up2 Second up sprite
     * @param down1 First down sprite
     * @param down2 Second down sprite
     * @param left1 First left sprite
     * @param left2 Second left sprite
     * @param right1 First right sprite
     * @param right2 Second right sprite
     * @return The appropriate sprite for current direction and frame
     */
    public BufferedImage getSprite(String direction, 
                                   BufferedImage up1, BufferedImage up2,
                                   BufferedImage down1, BufferedImage down2,
                                   BufferedImage left1, BufferedImage left2,
                                   BufferedImage right1, BufferedImage right2) {
        
        switch(direction) {
            case "up":
                return (spriteNum == 1) ? up1 : up2;
            case "down":
                return (spriteNum == 1) ? down1 : down2;
            case "left":
                return (spriteNum == 1) ? left1 : left2;
            case "right":
                return (spriteNum == 1) ? right1 : right2;
            default:
                return down1; // Default to facing down
        }
    }
}