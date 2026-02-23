package com.mjsamaha.game.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.ui.FontManager;
import com.mjsamaha.game.ui.UIState;
import com.mjsamaha.game.ui.UIUtils;

public class CharacterScreen {
    private final GamePanel gp;
    private final UIState state;
    private final FontManager fm = FontManager.getInstance();
    
    public CharacterScreen(GamePanel gp, UIState state) {
        this.gp = gp;
        this.state = state;
    }
    
    public void update(KeyHandler keyH) {
        // Handle input for character screen if needed
        if (keyH.characterStatePressed) {
            // Toggle off - handled in GamePanel.update()
            keyH.characterStatePressed = false;
        }
    }
    
    public void draw(Graphics2D g2) {
        // Semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Character stats window - positioned on left side
        int frameX = gp.tileSize; // Moved from tileSize * 2 to just tileSize
        int frameY = gp.tileSize * 2;
        int frameWidth = gp.tileSize * 7;
        int frameHeight = gp.tileSize * 10;

        // Draw stat window using UIUtils
        UIUtils.drawSubWindow(g2, gp, frameX, frameY, frameWidth, frameHeight);

        // Title - positioned above the left-side window
        g2.setFont(fm.getMedium());
        g2.setColor(Color.WHITE);
        String text = "CHARACTER";
        int x = frameX + (frameWidth / 2) - ((int) g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2);
        int y = gp.tileSize + 10;
        g2.drawString(text, x, y);

        // Draw stats
        g2.setFont(fm.getSmall());
        int textX = frameX + 30;
        int textY = frameY + 40;
        int lineHeight = 30;

        // Level
        g2.drawString("Level: " + gp.player.level, textX, textY);
        textY += lineHeight;

        // Health
        g2.drawString("Health: " + gp.player.health + "/" + gp.player.maxHealth, textX, textY);
        textY += lineHeight;

        // Strength
        g2.drawString("Strength: " + gp.player.strength, textX, textY);
        textY += lineHeight;

        // Dexterity
        g2.drawString("Dexterity: " + gp.player.dexterity, textX, textY);
        textY += lineHeight;

        // Attack
        g2.drawString("Attack: " + gp.player.attack, textX, textY);
        textY += lineHeight;

        // Defense
        g2.drawString("Defense: " + gp.player.defense, textX, textY);
        textY += lineHeight;

        // Experience
        g2.drawString("EXP: " + gp.player.exp, textX, textY);
        textY += lineHeight;

        // Next Level
        g2.drawString("Next: " + gp.player.nextLevelExp, textX, textY);
        textY += lineHeight;

        // Coins
        g2.drawString("Coins: " + gp.player.coins, textX, textY);
        textY += lineHeight + 5;

     // Equipment section
        g2.drawString("Equipment:", textX, textY);
        textY += lineHeight;

        // Weapon - draw icon and name
        g2.drawImage(gp.player.currentWeapon.down1, textX, textY - 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString("Weapon: " + gp.player.currentWeapon.name, textX + gp.tileSize / 2 + 10, textY);
        textY += lineHeight;

        // Shield - draw icon and name
        g2.drawImage(gp.player.currentShield.down1, textX, textY - 20, gp.tileSize / 2, gp.tileSize / 2, null);
        g2.drawString("Shield: " + gp.player.currentShield.name, textX + gp.tileSize / 2 + 10, textY);

        // Instructions at bottom right
        g2.setFont(fm.getSmall());
        text = "Press C to close";
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth - textWidth - 30;
        y = gp.screenHeight - 30;
        g2.drawString(text, x, y);
    }

    private int getXForCenteredText(Graphics2D g2, String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}