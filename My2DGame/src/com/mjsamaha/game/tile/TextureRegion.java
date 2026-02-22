package com.mjsamaha.game.tile;

import java.awt.image.BufferedImage;

/**
 * Represents a rectangular region within a texture atlas.
 * This allows multiple tiles/sprites to be stored in a single image file,
 * reducing texture binds and improving performance.
 * 
 * Inspired by Minecraft's texture atlas system where all block textures
 * are stitched into one large atlas at startup.
 */
public class TextureRegion {
    
    private final BufferedImage atlas;      // The parent atlas image
    private final int x;                    // X coordinate in atlas (pixels)
    private final int y;                    // Y coordinate in atlas (pixels)
    private final int width;                // Region width (pixels)
    private final int height;               // Region height (pixels)
    
    // Optional: cache the extracted subimage for performance
    private BufferedImage cachedSubImage;
    
    /**
     * Creates a new texture region.
     * 
     * @param atlas The full atlas image
     * @param x X coordinate of region start
     * @param y Y coordinate of region start
     * @param width Width of the region
     * @param height Height of the region
     */
    public TextureRegion(BufferedImage atlas, int x, int y, int width, int height) {
        this.atlas = atlas;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cachedSubImage = null;
    }
    
    /**
     * Extracts and returns the subimage for this region.
     * First call extracts and caches. Subsequent calls return cached version.
     * 
     * @return The BufferedImage for this region
     */
    public BufferedImage getSubImage() {
        if (cachedSubImage == null) {
            cachedSubImage = atlas.getSubimage(x, y, width, height);
        }
        return cachedSubImage;
    }
    
    /**
     * Gets the subimage without caching (for dynamic use cases).
     * 
     * @return A fresh subimage from the atlas
     */
    public BufferedImage getSubImageFresh() {
        return atlas.getSubimage(x, y, width, height);
    }
    
    // Getters for region properties
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    /**
     * Clears the cached subimage to free memory.
     * Useful if you need to reload the atlas or optimize memory.
     */
    public void clearCache() {
        cachedSubImage = null;
    }
    
    @Override
    public String toString() {
        return "TextureRegion[x=" + x + ", y=" + y + ", w=" + width + ", h=" + height + "]";
    }
}