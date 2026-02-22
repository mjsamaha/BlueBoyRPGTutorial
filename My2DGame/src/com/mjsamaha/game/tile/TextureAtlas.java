package com.mjsamaha.game.tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONObject;
import org.json.JSONArray;

import com.mjsamaha.game.GamePanel;

/**
 * Manages a single texture atlas and its regions.
 * An atlas is a large image containing multiple smaller textures stitched together.
 * 
 * This reduces the number of texture binds during rendering, improving performance.
 * Minecraft uses this exact approach for all block textures.
 */
public class TextureAtlas {
    
    private final String name;                          // Atlas identifier (e.g., "terrain")
    private final BufferedImage atlasImage;             // The full atlas image
    private final Map<String, TextureRegion> regions;   // Texture ID -> Region mapping
    private final int tileSize;                         // Default tile size for grid-based atlases
    
    /**
     * Creates a texture atlas from an image file and metadata JSON.
     * 
     * @param name Atlas identifier
     * @param atlasImagePath Path to atlas PNG (e.g., "/atlas/terrain_atlas.png")
     * @param metadataPath Path to metadata JSON (e.g., "/atlas/terrain_atlas.json")
     * @param gp GamePanel for scaling utilities
     * @throws IOException if loading fails
     */
    public TextureAtlas(String name, String atlasImagePath, String metadataPath, GamePanel gp) throws IOException {
        this.name = name;
        this.regions = new HashMap<>();
        
        // Load the atlas image
        InputStream atlasStream = getClass().getResourceAsStream(atlasImagePath);
        if (atlasStream == null) {
            throw new IOException("Atlas image not found: " + atlasImagePath);
        }
        this.atlasImage = ImageIO.read(atlasStream);
        atlasStream.close();
        
        // Load and parse the metadata JSON
        InputStream metadataStream = getClass().getResourceAsStream(metadataPath);
        if (metadataStream == null) {
            throw new IOException("Atlas metadata not found: " + metadataPath);
        }
        
        String jsonContent = new String(metadataStream.readAllBytes());
        metadataStream.close();
        
        JSONObject json = new JSONObject(jsonContent);
        this.tileSize = json.getInt("tileSize");
        
        // Parse regions array
        JSONArray regionsArray = json.getJSONArray("regions");
        for (int i = 0; i < regionsArray.length(); i++) {
            JSONObject regionData = regionsArray.getJSONObject(i);
            
            String id = regionData.getString("id");
            int row = regionData.getInt("row");
            int col = regionData.getInt("col");
            
            // Calculate pixel coordinates from grid position
            int x = col * tileSize;
            int y = row * tileSize;
            
            // Create and register the region
            TextureRegion region = new TextureRegion(atlasImage, x, y, tileSize, tileSize);
            regions.put(id, region);
        }
        
        System.out.println("✅ Loaded atlas '" + name + "' with " + regions.size() + " regions");
    }
    
    /**
     * Alternative constructor for grid-based atlases without JSON metadata.
     * Useful for quick prototyping or simple uniform grids.
     * 
     * @param name Atlas identifier
     * @param atlasImagePath Path to atlas PNG
     * @param tileSize Size of each tile in pixels
     * @param textureIds Array of texture IDs in row-major order
     */
    public TextureAtlas(String name, String atlasImagePath, int tileSize, String[] textureIds) throws IOException {
        this.name = name;
        this.tileSize = tileSize;
        this.regions = new HashMap<>();
        
        // Load the atlas image
        InputStream atlasStream = getClass().getResourceAsStream(atlasImagePath);
        if (atlasStream == null) {
            throw new IOException("Atlas image not found: " + atlasImagePath);
        }
        this.atlasImage = ImageIO.read(atlasStream);
        atlasStream.close();
        
        // Calculate grid dimensions
        int cols = atlasImage.getWidth() / tileSize;
        
        // Register each texture ID
        for (int i = 0; i < textureIds.length; i++) {
            int row = i / cols;
            int col = i % cols;
            int x = col * tileSize;
            int y = row * tileSize;
            
            TextureRegion region = new TextureRegion(atlasImage, x, y, tileSize, tileSize);
            regions.put(textureIds[i], region);
        }
        
        System.out.println("✅ Loaded atlas '" + name + "' (grid mode) with " + regions.size() + " regions");
    }
    
    /**
     * Retrieves a texture region by its ID.
     * 
     * @param textureId The texture identifier (e.g., "grass00", "water01")
     * @return The TextureRegion, or null if not found
     */
    public TextureRegion getRegion(String textureId) {
        return regions.get(textureId);
    }
    
    /**
     * Checks if a texture ID exists in this atlas.
     * 
     * @param textureId The texture identifier
     * @return true if the texture exists
     */
    public boolean hasRegion(String textureId) {
        return regions.containsKey(textureId);
    }
    
    /**
     * Gets the full atlas image (useful for debugging).
     * 
     * @return The complete atlas BufferedImage
     */
    public BufferedImage getAtlasImage() {
        return atlasImage;
    }
    
    /**
     * Gets the number of regions in this atlas.
     * 
     * @return Region count
     */
    public int getRegionCount() {
        return regions.size();
    }
    
    /**
     * Gets the atlas name/identifier.
     * 
     * @return Atlas name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the default tile size for this atlas.
     * 
     * @return Tile size in pixels
     */
    public int getTileSize() {
        return tileSize;
    }
    
    /**
     * Prints debug information about this atlas.
     */
    public void printDebugInfo() {
        System.out.println("\n=== Atlas: " + name + " ===");
        System.out.println("Image size: " + atlasImage.getWidth() + "x" + atlasImage.getHeight());
        System.out.println("Tile size: " + tileSize + "x" + tileSize);
        System.out.println("Regions: " + regions.size());
        System.out.println("Texture IDs: " + regions.keySet());
        System.out.println("========================\n");
    }
}