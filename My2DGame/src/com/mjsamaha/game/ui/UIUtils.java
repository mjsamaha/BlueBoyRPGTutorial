package com.mjsamaha.game.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.mjsamaha.game.GamePanel;

public final class UIUtils {
    private UIUtils() {}

    public static void drawSubWindow(Graphics2D g2, GamePanel gp, int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
}