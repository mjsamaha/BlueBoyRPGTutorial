package com.mjsamaha.game.ui;

public class UIState {
    public boolean messageOn = false;
    public String message = "";
    public boolean gameFinished = false;
    
    // Dialogue
    public String currentDialogue = "";
    public boolean dialogueActive = false;
    
    // Character screen
    public boolean characterScreenActive = false; 
    
    // Title/menu state
    public int commandNum = 0;
    public int titleScreenState = 0;
    
    // Toggle debug display
    public boolean showDebug = false;
}