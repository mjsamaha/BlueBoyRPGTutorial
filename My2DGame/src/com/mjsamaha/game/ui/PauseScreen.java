package com.mjsamaha.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.managers.KeyHandler;

public class PauseScreen {
	
	private final GamePanel gp;
	
	private final FontManager fm = FontManager.getInstance();
	
	// Options for pause menu
    private final String[] options = { "Resume", "Quit to Menu" };
    private int commandNum = 0;

    public PauseScreen(GamePanel gp) {
        this.gp = gp;
    }
    
    /**
     * Update input for pause menu navigation
     */
    public void update(KeyHandler keyH) {
        if (keyH.upPressed) {
            commandNum--;
            if (commandNum < 0) commandNum = options.length - 1;
            keyH.upPressed = false;
        }

        if (keyH.downPressed) {
            commandNum++;
            if (commandNum >= options.length) commandNum = 0;
            keyH.downPressed = false;
        }

        if (keyH.confirmPressed) {
            selectOption();
            keyH.confirmPressed = false;
        }
    }

    private void selectOption() {
        switch (commandNum) {
            case 0 -> gp.gameState = gp.playState; // Resume
            case 1 -> gp.gameState = gp.menuState; // Quit to menu
        }
    }

    /**
     * Draw pause screen overlay
     */
    public void draw(Graphics2D g2) {
        // Draw translucent background
        UIUtils.drawSubWindow(g2, gp, gp.screenWidth/4, gp.screenHeight/4,
                gp.screenWidth/2, gp.screenHeight/2);

        g2.setFont(fm.getLarge());
        g2.setColor(Color.WHITE);
        String title = "PAUSED";
        int titleX = gp.screenWidth/2 - g2.getFontMetrics().stringWidth(title)/2;
        int titleY = gp.screenHeight/4 + 60;
        g2.drawString(title, titleX, titleY);

        // Draw options
        g2.setFont(fm.getMedium());
        int optionY = titleY + 80;
        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = gp.screenWidth/2 - g2.getFontMetrics().stringWidth(text)/2;
            int textY = optionY + (i * 50);

            // Highlight selected option
            if (i == commandNum) g2.setColor(Color.YELLOW);
            else g2.setColor(Color.WHITE);

            g2.drawString(text, textX, textY);
        }
    }

    public void setCommandNum(int num) { this.commandNum = num; }


}
