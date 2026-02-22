#!/usr/bin/env python3
"""
Texture Atlas Generator for 2D Tile-Based Game
Creates a single atlas image from individual tile PNGs and generates metadata JSON.
"""

import os
import json
from PIL import Image

# Configuration
TILE_SIZE = 16  # Original tile size in pixels
ATLAS_COLS = 8  # 8 columns
ATLAS_ROWS = 8  # 8 rows (only first 4 used, rest reserved)
ATLAS_WIDTH = TILE_SIZE * ATLAS_COLS   # 128 pixels
ATLAS_HEIGHT = TILE_SIZE * ATLAS_ROWS  # 128 pixels

# Tile layout definition (in order)
# This matches your TileManager registration
TILE_LAYOUT = [
    # Row 0 (Grass + Water)
    "grass00", "grass01", "water00", "water01", "water02", "water03", "water04", "water05",
    
    # Row 1 (Water continued)
    "water06", "water07", "water08", "water09", "water10", "water11", "water12", "water13",
    
    # Row 2 (Roads)
    "road00", "road01", "road02", "road03", "road04", "road05", "road06", "road07",
    
    # Row 3 (Roads + Special)
    "road08", "road09", "road10", "road11", "road12", "earth", "wall", "tree",
]

def create_atlas(tiles_folder, output_folder):
    """
    Creates a texture atlas from individual tile images.
    
    Args:
        tiles_folder: Path to folder containing individual tile PNGs
        output_folder: Path where atlas PNG and JSON will be saved
    """
    print("=" * 60)
    print("Texture Atlas Generator")
    print("=" * 60)
    print(f"Tile size: {TILE_SIZE}×{TILE_SIZE} pixels")
    print(f"Atlas size: {ATLAS_WIDTH}×{ATLAS_HEIGHT} pixels ({ATLAS_COLS}×{ATLAS_ROWS} grid)")
    print(f"Source folder: {tiles_folder}")
    print(f"Output folder: {output_folder}")
    print()
    
    # Create blank atlas with transparency
    atlas = Image.new('RGBA', (ATLAS_WIDTH, ATLAS_HEIGHT), (0, 0, 0, 0))
    
    # Track regions for JSON metadata
    regions = []
    
    # Process each tile in the layout
    for idx, tile_name in enumerate(TILE_LAYOUT):
        row = idx // ATLAS_COLS
        col = idx % ATLAS_COLS
        
        # Load the tile image
        tile_path = os.path.join(tiles_folder, f"{tile_name}.png")
        
        if not os.path.exists(tile_path):
            print(f"⚠️  WARNING: {tile_name}.png not found, skipping...")
            continue
        
        try:
            tile_img = Image.open(tile_path)
            
            # Verify tile is correct size
            if tile_img.size != (TILE_SIZE, TILE_SIZE):
                print(f"⚠️  WARNING: {tile_name}.png is {tile_img.size}, expected {TILE_SIZE}×{TILE_SIZE}")
                # Resize if needed
                tile_img = tile_img.resize((TILE_SIZE, TILE_SIZE), Image.NEAREST)
            
            # Paste tile into atlas at correct position
            x = col * TILE_SIZE
            y = row * TILE_SIZE
            atlas.paste(tile_img, (x, y))
            
            # Record region metadata
            regions.append({
                "id": tile_name,
                "row": row,
                "col": col
            })
            
            print(f"✅ [{row},{col}] {tile_name:15s} -> ({x:3d}, {y:3d})")
            
        except Exception as e:
            print(f"❌ ERROR loading {tile_name}.png: {e}")
    
    print()
    print(f"Total tiles processed: {len(regions)}")
    
    # Save atlas image
    os.makedirs(output_folder, exist_ok=True)
    atlas_path = os.path.join(output_folder, "terrain_atlas.png")
    atlas.save(atlas_path, "PNG")
    print(f"\n✅ Atlas saved: {atlas_path}")
    
    # Create metadata JSON
    metadata = {
        "tileSize": TILE_SIZE,
        "atlasWidth": ATLAS_WIDTH,
        "atlasHeight": ATLAS_HEIGHT,
        "regions": regions
    }
    
    json_path = os.path.join(output_folder, "terrain_atlas.json")
    with open(json_path, 'w') as f:
        json.dump(metadata, f, indent=2)
    print(f"✅ Metadata saved: {json_path}")
    
    print("\n" + "=" * 60)
    print("Atlas generation complete!")
    print("=" * 60)
    
    return atlas_path, json_path


if __name__ == "__main__":
    import sys
    
    # Default paths (adjust these to match your project structure)
    default_tiles_folder = "res/tiles"
    default_output_folder = "res/atlas"
    
    # Allow command-line override
    tiles_folder = sys.argv[1] if len(sys.argv) > 1 else default_tiles_folder
    output_folder = sys.argv[2] if len(sys.argv) > 2 else default_output_folder
    
    # Check if tiles folder exists
    if not os.path.exists(tiles_folder):
        print(f"❌ ERROR: Tiles folder not found: {tiles_folder}")
        print("\nUsage: python3 create_atlas.py [tiles_folder] [output_folder]")
        print(f"Example: python3 create_atlas.py ../resources/tiles ../resources/atlas")
        sys.exit(1)
    
    # Generate the atlas
    create_atlas(tiles_folder, output_folder)