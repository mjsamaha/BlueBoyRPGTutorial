package com.mjsamaha.game.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.ui.FontManager;
import com.mjsamaha.game.ui.UIState;
public class MenuScreen {
    private final GamePanel gp;
    private final UIState state;
    private final FontManager fm = FontManager.getInstance();

    public MenuScreen(GamePanel gp, UIState state) {
        this.gp = gp;
        this.state = state;
    }
    



    public void update(KeyHandler keyH) {

        if (!keyH.confirmPressed && !keyH.upPressed && !keyH.downPressed) return;

        if (state.titleScreenState == 0) {
            // main menu navigation
            if (keyH.upPressed) {
                state.commandNum--;
                if (state.commandNum < 0) state.commandNum = 2;
                gp.playSE(SoundEvent.SFX_MENU_SELECT);
                keyH.upPressed = false;
            }
            if (keyH.downPressed) {
                state.commandNum++;
                if (state.commandNum > 2) state.commandNum = 0;
                gp.playSE(SoundEvent.SFX_MENU_SELECT);
                keyH.downPressed = false;
            }
            if (keyH.confirmPressed) {
                handleMainMenuSelection();
                keyH.confirmPressed = false;
            }
        } 
        else if (state.titleScreenState == 1) {
            // class select navigation
            handleClassSelect(keyH);
        }
    }
	
	private void handleClassSelect(KeyHandler keyH) {
	    if (keyH.upPressed) {
	        state.commandNum--;
	        if (state.commandNum < 0) state.commandNum = 3; // 4 options
            gp.playSE(SoundEvent.SFX_MENU_SELECT);
	        keyH.upPressed = false;
	    }

	    if (keyH.downPressed) {
	        state.commandNum++;
	        if (state.commandNum > 3) state.commandNum = 0;
            gp.playSE(SoundEvent.SFX_MENU_SELECT);
	        keyH.downPressed = false;
	    }

	    if (keyH.confirmPressed) {
	        switch(state.commandNum) {
	            case 0, 1, 2 -> { // FIGHTER, THIEF, SORCERER
	                // Stop menu music
	                gp.soundManager.stopSound(SoundEvent.MUSIC_AMBIENT_LOOP);

	                // Start gameplay music
	                gp.playMusic(SoundEvent.MUSIC_BLUE_BOY);

	                // Switch to play state
	                gp.stateManager.toPlayState();

	                // Reset menu state for next time
	                state.titleScreenState = 0;
	                state.commandNum = 0;
	            }
	            case 3 -> { // BACK
	                state.titleScreenState = 0;
	                state.commandNum = 0;
	            }
	        }
	        keyH.confirmPressed = false;
	    }
	}
    
    private void handleMainMenuSelection() {
        switch (state.commandNum) {
            case 0 -> {
                state.titleScreenState = 1;
                state.commandNum = 0;
            }
            case 1 -> {
                // load game
            }
            case 2 -> System.exit(0);
        }
    }

    public void draw(Graphics2D g2) {
        g2.setFont(fm.getTitle());
        g2.setColor(Color.WHITE);

        int tileSize = gp.tileSize;

        if (gp.stateManager.isMenuState()) {
            if (state.titleScreenState == 0) {
                drawMainTitle(g2);
            } else if (state.titleScreenState == 1) {
                drawClassSelect(g2);
            }
        }
    }

    private void drawMainTitle(Graphics2D g2) {
    	// --- Main title ---
        String text = "My 2D Game";
        int x = gp.screenWidth / 2 - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2;
        int y = gp.tileSize * 2;

        // Draw shadow first (black, slightly offset)
        g2.setColor(Color.GRAY); // semi-transparent black for soft shadow
        g2.drawString(text, x + 3, y + 3);

        // Draw main text on top
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // --- Menu options ---
        g2.setFont(fm.getLarge());
        int menuY = y + gp.tileSize * 3;

        String[] options = {"NEW GAME", "LOAD GAME", "EXIT"};

        for (int i = 0; i < options.length; i++) {
            text = options[i];
            x = gp.screenWidth / 2 - (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2;

            // Draw shadow
            g2.setColor(new Color(0, 0, 0, 150));
            g2.drawString(text, x + 2, menuY + 2);

            // Draw main text
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, menuY);

            // Draw selector arrow
            if (state.commandNum == i) {
                g2.setColor(Color.YELLOW);
                g2.drawString(">", x - gp.tileSize, menuY);
            }

            menuY += gp.tileSize;
        }
    }

    private void drawClassSelect(Graphics2D g2) {
        // Simple class select stub — you can expand this
        String text = "CLASS SELECT";
        int x = gp.screenWidth/2 - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2;
        int y = gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(fm.getMedium());
        String[] options = {"FIGHTER", "THIEF", "SORCERER", "BACK"};
        for (int i = 0; i < options.length; i++) {
            y += gp.tileSize;
            text = options[i];
            x = gp.screenWidth/2 - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth()/2;
            g2.drawString(text, x, y);
            if (state.commandNum == i) g2.drawString(">", x - gp.tileSize, y);
        }
    }
}