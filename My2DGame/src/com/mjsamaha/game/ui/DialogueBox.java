package com.mjsamaha.game.ui;

import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;

public class DialogueBox {
    private final GamePanel gp;
    private final UIState state;
    private final FontManager fm = FontManager.getInstance();

    public DialogueBox(GamePanel gp, UIState state) {
        this.gp = gp;
        this.state = state;
    }

    public void draw(Graphics2D g2) {
        if (!state.dialogueActive) return;

        int x = gp.tileSize * 2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        UIUtils.drawSubWindow(g2, gp, x, y, width, height);

        g2.setFont(fm.getMedium());
        g2.setColor(Color.WHITE);

        int textX = x + gp.tileSize;
        int textY = y + gp.tileSize;

        String[] lines = wrapText(state.currentDialogue, g2, width - (gp.tileSize*2));
        for (String line : lines) {
            g2.drawString(line, textX, textY);
            textY += GamePanel.DIALOGUE_LINE_HEIGHT;
        }
    }

    // Simple text wrapper — keeps lines within width (approximate)
    private String[] wrapText(String text, Graphics2D g2, int maxWidth) {
        if (text == null) text = "";
        java.util.List<String> lines = new java.util.ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        for (String w : words) {
            String test = line.length() == 0 ? w : line + " " + w;
            int lw = (int) g2.getFontMetrics().getStringBounds(test, g2).getWidth();
            if (lw > maxWidth) {
                lines.add(line.toString());
                line = new StringBuilder(w);
            } else {
                line = new StringBuilder(test);
            }
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines.toArray(new String[0]);
    }
}
