package com.mjsamaha.game.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FontManager {
    private static final Logger LOGGER = Logger.getLogger(FontManager.class.getSimpleName());

    private static FontManager instance;

    private Font minecraft20;
    private Font minecraft20B;
    private Font minecraft30;
    private Font minecraft50;
    private Font minecraft80B;

    private FontManager() {
        loadFonts();
    }

    public static synchronized FontManager getInstance() {
        if (instance == null) instance = new FontManager();
        return instance;
    }

    private void loadFonts() {
        try (InputStream is = getClass().getResourceAsStream("/fonts/Minecraft.ttf")) {
            if (is != null) {
                Font base = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(base);

                minecraft20 = base.deriveFont(Font.PLAIN, 20f);
                minecraft20B = base.deriveFont(Font.BOLD, 20f);
                minecraft30 = base.deriveFont(Font.PLAIN, 30f);
                minecraft50 = base.deriveFont(Font.PLAIN, 50f);
                minecraft80B = base.deriveFont(Font.BOLD, 80f);
                LOGGER.info("✅ Minecraft.ttf loaded successfully with all font sizes.");
                
                return;
                
            } else {
                LOGGER.warning("❌ Minecraft.ttf not found in resources; using fallback fonts.");
            }
        } catch (FontFormatException | IOException e) {
            LOGGER.log(Level.WARNING, "❌ Failed to load Minecraft.ttf; using fallback fonts.", e);
        }

        // fallback fonts
        minecraft20 = new Font("Arial", Font.PLAIN, 20);
        minecraft20B = new Font("Arial", Font.BOLD, 20);
        minecraft30 = new Font("Arial", Font.PLAIN, 30);
        minecraft50 = new Font("Arial", Font.PLAIN, 50);
        minecraft80B = new Font("Arial", Font.BOLD, 80);
        
        LOGGER.info("ℹ️ Fallback fonts loaded successfully.");

    }

    public Font getSmall() { return minecraft20; }
    public Font getSmallBold() { return minecraft20B; }
    public Font getMedium() { return minecraft30; }
    public Font getLarge() { return minecraft50; }
    public Font getTitle() { return minecraft80B; }
}
