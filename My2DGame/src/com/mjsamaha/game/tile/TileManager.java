package com.mjsamaha.game.tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.mjsamaha.game.GamePanel;

public class TileManager {

    GamePanel gp;
    public int[][] mapTileNum;
    private String currentMapPath; 

    public TileManager(GamePanel gp) {
        this.gp = gp;

        // Initialize atlas before tiles
        AtlasManager.initialize(gp);

        // Allocate tile array
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        registerTiles();  // populate TileRegistry
        loadMap("/maps/worldV2.txt");
    }
    
    
    /*
     * Or you could load it from JSON, e.g.:
     *
     * [
     *   { "id": 0, "image": "grass00", "collision": false },
     *   { "id": 2, "image": "water00", "collision": true }
     * ]
     *
     * This is where the real pluggable behavior shines—you can attach a behavior per tile
     * by mapping the ID to a lambda or a class dynamically.
     */
    private void registerTiles() {
        // IDs 0–9: Placeholder grass tiles
        TileRegistry.register(new TileDefinition(0, "grass00", false, null));
        TileRegistry.register(new TileDefinition(1, "grass00", false, null));
        TileRegistry.register(new TileDefinition(2, "grass00", false, null));
        TileRegistry.register(new TileDefinition(3, "grass00", false, null));
        TileRegistry.register(new TileDefinition(4, "grass00", false, null));
        TileRegistry.register(new TileDefinition(5, "grass00", false, null));
        TileRegistry.register(new TileDefinition(6, "grass00", false, null));
        TileRegistry.register(new TileDefinition(7, "grass00", false, null));
        TileRegistry.register(new TileDefinition(8, "grass00", false, null));
        TileRegistry.register(new TileDefinition(9, "grass00", false, null));

        // IDs 10–25: Floors and water
        TileRegistry.register(new TileDefinition(10, "grass00", false, null));
        TileRegistry.register(new TileDefinition(11, "grass01", false, null));
        TileRegistry.register(new TileDefinition(12, "water00", true, null));
        TileRegistry.register(new TileDefinition(13, "water01", true, null));
        TileRegistry.register(new TileDefinition(14, "water02", true, null));
        TileRegistry.register(new TileDefinition(15, "water03", true, null));
        TileRegistry.register(new TileDefinition(16, "water04", true, null));
        TileRegistry.register(new TileDefinition(17, "water05", true, null));
        TileRegistry.register(new TileDefinition(18, "water06", true, null));
        TileRegistry.register(new TileDefinition(19, "water07", true, null));
        TileRegistry.register(new TileDefinition(20, "water08", true, null));
        TileRegistry.register(new TileDefinition(21, "water09", true, null));
        TileRegistry.register(new TileDefinition(22, "water10", true, null));
        TileRegistry.register(new TileDefinition(23, "water11", true, null));
        TileRegistry.register(new TileDefinition(24, "water12", true, null));
        TileRegistry.register(new TileDefinition(25, "water13", true, null));

        // IDs 26–38: Roads
        TileRegistry.register(new TileDefinition(26, "road00", false, null));
        TileRegistry.register(new TileDefinition(27, "road01", false, null));
        TileRegistry.register(new TileDefinition(28, "road02", false, null));
        TileRegistry.register(new TileDefinition(29, "road03", false, null));
        TileRegistry.register(new TileDefinition(30, "road04", false, null));
        TileRegistry.register(new TileDefinition(31, "road05", false, null));
        TileRegistry.register(new TileDefinition(32, "road06", false, null));
        TileRegistry.register(new TileDefinition(33, "road07", false, null));
        TileRegistry.register(new TileDefinition(34, "road08", false, null));
        TileRegistry.register(new TileDefinition(35, "road09", false, null));
        TileRegistry.register(new TileDefinition(36, "road10", false, null));
        TileRegistry.register(new TileDefinition(37, "road11", false, null));
        TileRegistry.register(new TileDefinition(38, "road12", false, null));

        // IDs 39–41: Special tiles
        TileRegistry.register(new TileDefinition(39, "earth", false, null));
        TileRegistry.register(new TileDefinition(40, "wall", true, null));
        TileRegistry.register(new TileDefinition(41, "tree", true, null));
    }
    

    /** Load tile map from text file */
    public void loadMap(String filePath) {
        this.currentMapPath = filePath;  // ADD THIS LINE at the very beginning
        
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Track which IDs are used
            java.util.Set<Integer> usedIds = new java.util.HashSet<>();

            int row = 0;
            String line;

            while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                String[] numbers = line.trim().split("\\s+");
                for (int col = 0; col < Math.min(numbers.length, gp.maxWorldCol); col++) {
                    try {
                        int num = Integer.parseInt(numbers[col]);
                        usedIds.add(num);

                        // Validate tile ID against TileRegistry
                        if (TileRegistry.get(num) == null) {
                            System.err.println("Invalid tile ID at [" + col + "][" + row + "]: " + num);
                            mapTileNum[col][row] = 0; // default to grass00
                        } else {
                            mapTileNum[col][row] = num;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format at [" + col + "][" + row + "]: " + numbers[col]);
                        mapTileNum[col][row] = 0; // default to grass00
                    }
                }
                row++;
            }

            br.close();

            // Print summary of used IDs
            System.out.println("\n=== Tile IDs used in " + filePath + " ===");
            java.util.List<Integer> sortedIds = new java.util.ArrayList<>(usedIds);
            java.util.Collections.sort(sortedIds);
            for (int id : sortedIds) {
                String mapping = TileRegistry.get(id) != null ? "✅ mapped" : "❌ missing";
                System.out.println("ID " + id + ": " + mapping);
            }
            System.out.println("=====================================\n");

        } catch (Exception e) {
            System.err.println("Error loading map file: " + filePath);
            e.printStackTrace();
        }
    }
    
    public void reloadMap() {
    	if (currentMapPath != null) {
    		System.out.println("🔄 Reloading map from: " + currentMapPath);
			loadMap(currentMapPath);
    	} else {
            System.err.println("❌ No map loaded to reload!");
    	}
    }

    /** Draw visible tiles to screen */
    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {

                int tileId = mapTileNum[worldCol][worldRow];
                TileDefinition def = TileRegistry.get(tileId);

                if (def == null) {
                    System.err.println("Invalid tile number at [" + worldCol + "][" + worldRow + "]: " + tileId);
                    continue;
                }

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Draw only if visible
                if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                	TextureRegion region = AtlasManager.getRegion(def.getTextureId());

                	if (region != null) {
                	    BufferedImage subImage = region.getSubImage();
                	    g2.drawImage(subImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
                	    
                	    // Optional behavior
                	    Tile tempTile = new Tile();
                	    tempTile.image = subImage;
                	    tempTile.collision = def.hasCollision();
                	    def.executeBehavior(gp, tempTile);
                	} else {
                	    System.err.println("Texture not found: " + def.getTextureId());
                	}
                }
            }
        }
    }
}
