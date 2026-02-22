package com.mjsamaha.game.ui;

import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;



/**
 * Thin orchestrator that holds UI state and delegates rendering to
 * modular renderers (MenuScreen, HUD, DialogueBox). This file replaces the
 * large monolithic UserInterface.java and keeps only orchestration logic.
 */
public class UserInterface {
    private final GamePanel gp;
    private final UIState state = new UIState();

    private MenuScreen menuScreen; // no constructor call here
    private PauseScreen pauseScreen; // can be added later if needed

    private final HUD hud;
    private final DialogueBox dialogueBox;
    public String currentDialogue;


    public UserInterface(GamePanel gp) {
        this.gp = gp;
        FontManager.getInstance();

        menuScreen = new MenuScreen(gp, state); // correct constructor
        pauseScreen = new PauseScreen(gp); // can be added later if needed
        hud = new HUD(gp, state);
        dialogueBox = new DialogueBox(gp, state);
    }
    
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }
    
    public PauseScreen getPauseScreen() { return pauseScreen; }

    public UIState getState() { return state; }

    public void draw(Graphics2D g2) {
    	if (gp.gameState == gp.menuState) {
    	    menuScreen.draw(g2);
    	     
    	} else if (gp.gameState == gp.pauseState) {
    	    pauseScreen.draw(g2);
    	    
    	} else {
    	    // play + dialogue states
    	    hud.draw(g2);
    	    if (gp.gameState == gp.dialogueState || state.dialogueActive)
    	        dialogueBox.draw(g2);
    	    if (state.messageOn)
    	        dialogueBox.draw(g2);
    	}
    }
}