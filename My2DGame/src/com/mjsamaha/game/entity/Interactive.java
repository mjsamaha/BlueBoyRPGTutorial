package com.mjsamaha.game.entity;

public interface Interactive {
    
    /**
     * Initiates dialogue or interaction
     */
    void speak();
    
    /**
     * Gets the current dialogue text
     */
    String getCurrentDialogue();
}
