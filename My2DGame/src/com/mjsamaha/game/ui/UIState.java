package com.mjsamaha.game.ui;

public class UIState {
    public boolean messageOn = false;
    public String message = "";

    public boolean gameFinished = false;

    // Dialogue
    public String currentDialogue = "";
    public boolean dialogueActive = false;   // NEW: tracks if dialogue is ongoing

    // Title/menu state
    public int commandNum = 0;
    public int titleScreenState = 0; // 0 = main menu, 1 = class select

    // Toggle debug display
    public boolean showDebug = false;
}