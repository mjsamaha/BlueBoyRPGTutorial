package com.mjsamaha.game.ui;

import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.object.HeartObject;

/**
 * Heads-up display renderer. Keeps drawing logic for player HUD and debug info.
 */
public class HUD {
    private final GamePanel gp;
    private final UIState state;
    private final FontManager fm = FontManager.getInstance();

    private final HeartObject hearts;

    public HUD(GamePanel gp, UIState state) {
        this.gp = gp;
        this.state = state;

        // Load hearts once
        hearts = new HeartObject(gp);
    }

    public void draw(Graphics2D g2) {
        // Debug info if enabled
        if (state.showDebug) drawDebugInformation(g2);

        drawPlayerHealth(g2);
    }

    private void drawPlayerHealth(Graphics2D g2) {
        int x = 10;
        int y = 10;

        int fullHearts = gp.player.health / 2;    // 2 health = 1 full heart
        int halfHearts = gp.player.health % 2;    // 1 health = half heart
        int maxHearts = gp.player.maxHealth / 2;  // total hearts

        // Draw full hearts
        for (int i = 0; i < fullHearts; i++) {
            g2.drawImage(hearts.image, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
        }

        // Draw half heart if needed
        if (halfHearts == 1) {
            g2.drawImage(hearts.image2, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
        }

        // Draw blank hearts
        int remaining = maxHearts - fullHearts - halfHearts;
        for (int i = 0; i < remaining; i++) {
            g2.drawImage(hearts.image3, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
        }
    }

    private void drawDebugInformation(Graphics2D g2) {
        g2.setFont(fm.getSmall());
        g2.setColor(java.awt.Color.WHITE);
        String debugText = "My2DGame v1.0 | Player: (" + gp.player.worldX + ", " + gp.player.worldY + ")";
        g2.drawString(debugText, 10, gp.screenHeight - 10);
    }
}