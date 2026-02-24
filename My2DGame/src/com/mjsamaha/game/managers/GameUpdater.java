package com.mjsamaha.game.managers;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.ui.overlay.InventoryRenderer;

public class GameUpdater {
    private final GamePanel gp;
    
    public GameUpdater(GamePanel gp) {
        this.gp = gp;
    }
    
    public void update(KeyHandler keyH) {
    	handleGlobalInputs(keyH);
        handleCharacterScreenToggle(keyH);
        
        if (gp.stateManager.isMenuState()) {
            updateMenuState(keyH);
        } else if (gp.stateManager.isPlayState()) {
            updatePlayState(keyH);
        } else if (gp.stateManager.isPauseState()) {
            updatePauseState(keyH);
        } else if (gp.stateManager.isDialogueState()) {
            updateDialogueState(keyH);
        } else if (gp.stateManager.isCharacterState()) {
            updateCharacterState(keyH);
        }
    }
    
    private void handleGlobalInputs(KeyHandler keyH) {
        // Debug toggle (works in any state)
        if (keyH.debugTogglePressed) {
            gp.ui.getState().showDebug = !gp.ui.getState().showDebug;
            keyH.debugTogglePressed = false;
        }
        
        // Map refresh (works in any state)
        if (keyH.refreshPressed) {
            gp.tileM.reloadMap();
            gp.ui.addMessage("Map refreshed!");
            keyH.refreshPressed = false;
        }
    }
    
    private void updateMenuState(KeyHandler keyH) {
        gp.ui.getMenuScreen().update(keyH);
    }
    
    private void updatePlayState(KeyHandler keyH) {
        // Update player
        gp.player.update();
        
        // Update NPCs
        updateEntities(gp.npc);
        
        // Update monsters
        updateMonsters();
        
        // Check for world events
        gp.eHandler.checkEvent();
        
        // Pause toggle
        if (keyH.cancelPressed) {
            gp.stateManager.toPauseState();
            keyH.cancelPressed = false;
        }
        
     // Open character screen **only if play state AND not in dialogue**
        if (keyH.characterStatePressed && !gp.stateManager.isDialogueState()) {
            gp.stateManager.toCharacterState();
            gp.ui.getState().characterScreenActive = true;
            keyH.characterStatePressed = false;
        }
    }
    
    private void updateEntities(Entity[] entities) {
        for (int i = 0; i < entities.length; i++) {
            if (entities[i] != null) {
                entities[i].update();
            }
        }
    }
    
    private void updateMonsters() {
        for (int i = 0; i < gp.monster.length; i++) {
            if (gp.monster[i] != null) {
                if (gp.monster[i].alive && !gp.monster[i].dying) {
                    gp.monster[i].update();
                }
                if (!gp.monster[i].alive) {
                    gp.monster[i] = null;
                }
            }
        }
    }
    
    private void updatePauseState(KeyHandler keyH) {
        gp.ui.getPauseScreen().update(keyH);
        
        // Toggle back with ESC
        if (keyH.cancelPressed) {
            gp.stateManager.toPlayState();
            keyH.cancelPressed = false;
        }
    }
    
    private void updateDialogueState(KeyHandler keyH) {
        // Wait for confirm press to close dialogue
        if (keyH.confirmPressed) {
            gp.stateManager.toPlayState();
            keyH.confirmPressed = false;
            gp.ui.getState().dialogueActive = false;
        }
    }
    
    private void updateCharacterState(KeyHandler keyH) {
        // Exit character screen on C
        if (keyH.characterStatePressed) {
            gp.stateManager.toPlayState();                      // back to play
            gp.ui.getState().characterScreenActive = false;     // hide UI
            keyH.characterStatePressed = false;                 // reset key
            return;                                             // skip inventory navigation
        }
        
        // Only navigate inventory if character screen is still active
        if (gp.ui.getState().characterScreenActive) {
            handleInventoryNavigation(keyH);
        }
    }
    
    private void handleInventoryNavigation(KeyHandler keyH) {
        InventoryRenderer inventory = gp.ui.getInventoryRenderer();
        
        if (keyH.upPressed) {
            if (inventory.moveCursorUp()) {
                gp.playSE(SoundEvent.SFX_CURSOR);
            }
            keyH.upPressed = false;
        }
        if (keyH.downPressed) {
            if (inventory.moveCursorDown()) {
                gp.playSE(SoundEvent.SFX_CURSOR);
            }
            keyH.downPressed = false;
        }
        if (keyH.leftPressed) {
            if (inventory.moveCursorLeft()) {
                gp.playSE(SoundEvent.SFX_CURSOR);
            }
            keyH.leftPressed = false;
        }
        if (keyH.rightPressed) {
            if (inventory.moveCursorRight()) {
                gp.playSE(SoundEvent.SFX_CURSOR);
            }
            keyH.rightPressed = false;
        }
    }
    
    private void handleCharacterScreenToggle(KeyHandler keyH) {
        // Only toggle if not already in character screen
        if (keyH.characterStatePressed && !gp.stateManager.isCharacterState()) {
            if (gp.stateManager.isPlayState() || gp.stateManager.isPauseState()) {
                gp.stateManager.toCharacterState();
                gp.ui.getState().characterScreenActive = true;
            }
            keyH.characterStatePressed = false;
        }
    }
}