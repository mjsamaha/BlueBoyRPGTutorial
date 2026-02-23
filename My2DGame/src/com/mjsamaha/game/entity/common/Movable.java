package com.mjsamaha.game.entity.common;

public interface Movable {
    
    /**
     * Gets the current world X coordinate
     */
    int getWorldX();
    
    /**
     * Gets the current world Y coordinate
     */
    int getWorldY();
    
    /**
     * Sets the world X coordinate
     */
    void setWorldX(int worldX);
    
    /**
     * Sets the world Y coordinate
     */
    void setWorldY(int worldY);
    
    /**
     * Gets the movement speed
     */
    int getSpeed();
    
    /**
     * Gets the current direction
     */
    String getDirection();
    
    /**
     * Sets the current direction
     */
    void setDirection(String direction);
    
    /**
     * Checks if collision is currently active
     */
    boolean isCollisionOn();
    
    /**
     * Sets collision state
     */
    void setCollisionOn(boolean collisionOn);
}
