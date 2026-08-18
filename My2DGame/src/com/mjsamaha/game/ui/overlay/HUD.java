package com.mjsamaha.game.ui.overlay;

import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.object.HeartObject;
import com.mjsamaha.game.object.ManaObject;
import com.mjsamaha.game.ui.FontManager;
import com.mjsamaha.game.ui.UIState;

/**
 * Heads-up display renderer. Keeps drawing logic for player HUD and debug info.
 */
public class HUD {
    private final GamePanel gp;
    private final UIState state;
    private final FontManager fm = FontManager.getInstance();

    private final HeartObject hearts;
    private final ManaObject manas;

    public HUD(GamePanel gp, UIState state) {
        this.gp = gp;
        this.state = state;

        // Load hearts once
        hearts = new HeartObject(gp);
        manas = new ManaObject(gp);
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
        int maxHearts = (int) Math.ceil(gp.player.maxHealth / 2.0); // Max hearts based on max health

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
        
        // draw mana crystals (positioned directly below the hearts row)
        x = 10;
        y = 10 + gp.tileSize;

        // draw full mana crystals for current mana
        for (int i = 0; i < gp.player.mana; i++) {
            g2.drawImage(manas.image, x, y, gp.tileSize, gp.tileSize, null);
            x += gp.tileSize;
        }

        // draw blank mana crystals for the remaining slots up to maxMana
        for (int i = gp.player.mana; i < gp.player.maxMana; i++) {
            g2.drawImage(manas.image2, x, y, gp.tileSize, gp.tileSize, null);
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