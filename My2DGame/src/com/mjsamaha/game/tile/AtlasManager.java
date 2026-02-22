package com.mjsamaha.game.tile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mjsamaha.game.GamePanel;

/**
 * Centralized manager for all texture atlases in the game.
 * Follows the singleton pattern to provide global access.
 * 
 * This is where all atlases (terrain, entities, effects, etc.) are loaded
 * and made available to the rest of the game.
 * 
 * Usage:
 *   AtlasManager.initialize(gamePanel);
 *   TextureRegion region = AtlasManager.getRegion("grass00");
 */
public class AtlasManager {
    
    private static AtlasManager instance;
    private final Map<String, TextureAtlas> atlases;
    private GamePanel gp;
    
    
    /**
     * Private constructor for singleton pattern.
     */
    private AtlasManager() {
        this.atlases = new HashMap<>();
    }
    
    /**
     * Gets the singleton instance.
     * 
     * @return The AtlasManager instance
     */
    public static AtlasManager getInstance() {
        if (instance == null) {
            instance = new AtlasManager();
        }
        return instance;
    }
    
    /**
     * Initializes the atlas manager and loads all atlases.
     * Call this once during game startup, before loading any tiles.
     * 
     * @param gamePanel The game panel (needed for scaling utilities)
     */
    public static void initialize(GamePanel gamePanel) {
        getInstance().gp = gamePanel;
        getInstance().loadAllAtlases();
    }
    
    /**
     * Loads all texture atlases.
     * Add new atlases here as you create them (entities, effects, UI, etc.)
     */
    private void loadAllAtlases() {
        System.out.println("\n=== Loading Texture Atlases ===");
        
        try {
            // Load terrain atlas (grass, water, roads, walls, trees)
            TextureAtlas terrainAtlas = new TextureAtlas(
                "terrain",
                "/atlas/terrain_atlas.png",
                "/atlas/terrain_atlas.json",
                gp
            );
            atlases.put("terrain", terrainAtlas);
            terrainAtlas.printDebugInfo();
            
            // Future atlases can be added here:
            // TextureAtlas entityAtlas = new TextureAtlas("entities", "/atlas/entity_atlas.png", ...);
            // atlases.put("entities", entityAtlas);
            
            System.out.println("✅ All atlases loaded successfully\n");
            
        } catch (IOException e) {
            System.err.println("❌ ERROR: Failed to load atlases!");
            e.printStackTrace();
            System.err.println("\nFalling back to individual tile loading...\n");
        }
    }
    
    /**
     * Retrieves a texture region by its ID.
     * Searches all loaded atlases.
     * 
     * @param textureId The texture identifier (e.g., "grass00", "water01")
     * @return The TextureRegion, or null if not found
     */
    public static TextureRegion getRegion(String textureId) {
        // First check terrain atlas (most common case)
        TextureAtlas terrainAtlas = getInstance().atlases.get("terrain");
        if (terrainAtlas != null && terrainAtlas.hasRegion(textureId)) {
            return terrainAtlas.getRegion(textureId);
        }
        
        // Search other atlases
        for (TextureAtlas atlas : getInstance().atlases.values()) {
            if (atlas.hasRegion(textureId)) {
                return atlas.getRegion(textureId);
            }
        }
        
        System.err.println("⚠️  Texture region not found: " + textureId);
        return null;
    }
    
    /**
     * Retrieves a texture region from a specific atlas.
     * 
     * @param atlasName The atlas to search (e.g., "terrain", "entities")
     * @param textureId The texture identifier
     * @return The TextureRegion, or null if not found
     */
    public static TextureRegion getRegion(String atlasName, String textureId) {
        TextureAtlas atlas = getInstance().atlases.get(atlasName);
        if (atlas == null) {
            System.err.println("⚠️  Atlas not found: " + atlasName);
            return null;
        }
        return atlas.getRegion(textureId);
    }
    
    /**
     * Gets a specific atlas by name.
     * 
     * @param atlasName The atlas identifier
     * @return The TextureAtlas, or null if not found
     */
    public static TextureAtlas getAtlas(String atlasName) {
        return getInstance().atlases.get(atlasName);
    }
    
    /**
     * Checks if the atlas system is initialized and working.
     * 
     * @return true if at least one atlas is loaded
     */
    public static boolean isInitialized() {
        return getInstance().atlases.size() > 0;
    }
    
    /**
     * Prints debug information about all loaded atlases.
     */
    public static void printDebugInfo() {
        System.out.println("\n=== AtlasManager Debug Info ===");
        System.out.println("Loaded atlases: " + getInstance().atlases.size());
        for (TextureAtlas atlas : getInstance().atlases.values()) {
            atlas.printDebugInfo();
        }
        System.out.println("==============================\n");
    }
}