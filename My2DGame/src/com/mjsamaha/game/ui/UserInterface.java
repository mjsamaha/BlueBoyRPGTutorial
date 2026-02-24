package com.mjsamaha.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.ui.overlay.DialogueBox;
import com.mjsamaha.game.ui.overlay.HUD;
import com.mjsamaha.game.ui.screen.CharacterScreen;
import com.mjsamaha.game.ui.screen.MenuScreen;
import com.mjsamaha.game.ui.screen.PauseScreen;



/**
 * Thin orchestrator that holds UI state and delegates rendering to
 * modular renderers (MenuScreen, HUD, DialogueBox). This file replaces the
 * large monolithic UserInterface.java and keeps only orchestration logic.
 */
public class UserInterface {
    private final GamePanel gp;
    private final UIState state = new UIState();
    
    private final FontManager fm = FontManager.getInstance();

    private MenuScreen menuScreen; // no constructor call here
    private PauseScreen pauseScreen; // can be added later if needed
    private CharacterScreen characterScreen; // ADD THIS

    private final HUD hud;
    private final DialogueBox dialogueBox;
    public String currentDialogue;
    
    public ArrayList<String> msg = new ArrayList<>();
    public ArrayList<Integer> msgCounter = new ArrayList<>();


    public UserInterface(GamePanel gp) {
        this.gp = gp;
        FontManager.getInstance();

        menuScreen = new MenuScreen(gp, state); // correct constructor
        pauseScreen = new PauseScreen(gp); // can be added later if needed
        characterScreen = new CharacterScreen(gp, state); // ADD THIS
        hud = new HUD(gp, state);
        dialogueBox = new DialogueBox(gp, state);
    }
    
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }
    
    public PauseScreen getPauseScreen() { return pauseScreen; }
    
    public CharacterScreen getCharacterScreen() {
        return characterScreen; 
    }

    public UIState getState() { return state; }
    
    public void addMessage(String text) {
    	msg.add(text);
    	msgCounter.add(0);
    }
    
    public void drawMessage(Graphics2D g2) {
		int messageX = gp.tileSize;
		int messageY = gp.tileSize * 4;
		g2.setFont(fm.getSmallBold());
		
		for (int i = 0; i < msg.size(); i++) {
			
			if (msg.get(i) != null) {
				g2.setColor(Color.BLACK);
				g2.drawString(msg.get(i), messageX+2, messageY+2);
				
				g2.setColor(Color.WHITE);
				g2.drawString(msg.get(i), messageX, messageY);
				
				int counter = msgCounter.get(i) + 1;
				msgCounter.set(i, counter);
								
				if (msgCounter.get(i) > 180) {
					msg.remove(i);
					msgCounter.remove(i);
				}
				
				messageY += 50;

				
				
			}
		}
		
	}

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.menuState) {
            menuScreen.draw(g2);
        } else if (gp.gameState == gp.pauseState) {
            pauseScreen.draw(g2);
        } else if (gp.gameState == gp.characterState) { // ADD THIS
            // Draw game world first, then overlay
            hud.draw(g2);
            characterScreen.draw(g2);
        } else {
            // play + dialogue states
            hud.draw(g2);
            drawMessage(g2);
            if (gp.gameState == gp.dialogueState || state.dialogueActive)
                dialogueBox.draw(g2);
            if (state.messageOn)
                dialogueBox.draw(g2);
        }
    }
}