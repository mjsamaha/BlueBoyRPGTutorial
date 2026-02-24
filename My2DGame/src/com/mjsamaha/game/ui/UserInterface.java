package com.mjsamaha.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.ui.overlay.DebugOverlay;
import com.mjsamaha.game.ui.overlay.DialogueBox;
import com.mjsamaha.game.ui.overlay.HUD;
import com.mjsamaha.game.ui.overlay.InventoryRenderer;
import com.mjsamaha.game.ui.screen.CharacterScreen;
import com.mjsamaha.game.ui.screen.MenuScreen;
import com.mjsamaha.game.ui.screen.PauseScreen;



/**
 * Thin orchestrator that holds UI state and delegates rendering to
 * modular renderers (MenuScreen, HUD, DialogueBox). This file replaces the
 * large monolithic UserInterface.java and keeps only orchestration logic.
 */
public class UserInterface {
	
    private static final Logger LOGGER = Logger.getLogger(UserInterface.class.getSimpleName());

    private final GamePanel gp;
    private final UIState state = new UIState();
    private final FontManager fm = FontManager.getInstance();

    private MenuScreen menuScreen;
    private PauseScreen pauseScreen;
    private CharacterScreen characterScreen;
    private final InventoryRenderer inventoryRenderer;
    
    private final DebugOverlay debugOverlay;
    private final HUD hud;
    private final DialogueBox dialogueBox;
    public String currentDialogue;
    
    public ArrayList<String> msg = new ArrayList<>();
    public ArrayList<Integer> msgCounter = new ArrayList<>();

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        FontManager.getInstance();

        menuScreen = new MenuScreen(gp, state);
        pauseScreen = new PauseScreen(gp);
        characterScreen = new CharacterScreen(gp, state);
        inventoryRenderer = new InventoryRenderer(gp);
        hud = new HUD(gp, state);
        dialogueBox = new DialogueBox(gp, state);
        debugOverlay = new DebugOverlay(gp);
        
        LOGGER.info("UserInterface initialized with modular renderers.");

    }
    
    public MenuScreen getMenuScreen() { return menuScreen; }
    public PauseScreen getPauseScreen() { return pauseScreen; }
    public CharacterScreen getCharacterScreen() { return characterScreen; }
    public InventoryRenderer getInventoryRenderer() { return inventoryRenderer; }
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
        if (gp.stateManager.isMenuState()) {
            menuScreen.draw(g2);
        } else if (gp.stateManager.isPauseState()) {
            pauseScreen.draw(g2);
        } else if (gp.stateManager.isCharacterState()) {
            hud.draw(g2);
            characterScreen.draw(g2);
            inventoryRenderer.draw(g2);
        } else {
            // play + dialogue states
            hud.draw(g2);
            drawMessage(g2);
            if (gp.stateManager.isDialogueState() || state.dialogueActive)
                dialogueBox.draw(g2);
            if (state.messageOn)
                dialogueBox.draw(g2);
        }
        
        if (state.showDebug) {
            debugOverlay.draw(g2);
        }
    }
}