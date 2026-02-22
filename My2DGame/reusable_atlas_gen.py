#!/usr/bin/env python3
"""
Generic Texture Atlas Generator for 2D Tile-Based Games
Creates a single atlas image from individual tile PNGs and generates metadata JSON.
Configuration-driven and reusable across projects.
"""

import os
import json
from PIL import Image

# ===================== CONFIGURATION =====================
# Original tile size in pixels (source images)
TILE_SIZE = 16

# Atlas grid dimensions
ATLAS_COLS = 8
ATLAS_ROWS = 8

# Derived atlas size
ATLAS_WIDTH = TILE_SIZE * ATLAS_COLS
ATLAS_HEIGHT = TILE_SIZE * ATLAS_ROWS

# Tile layout: row-major order list of tile names (without .png)
TILE_LAYOUT = [
    # Example layout: can be replaced with any project's tiles
    "grass00", "grass01", "water00", "water01", "water02", "water03", "water04", "water05",
    "water06", "water07", "water08", "water09", "water10", "water11", "water12", "water13",
    "road00", "road01", "road02", "road03", "road04", "road05", "road06", "road07",
    "road08", "road09", "road10", "road11", "road12", "earth", "wall", "tree"
]
# ==========================================================

def create_atlas(tiles_folder: str, output_folder: str, tile_size=TILE_SIZE,
                 cols=ATLAS_COLS, rows=ATLAS_ROWS, tile_layout=TILE_LAYOUT):
    """
    Creates a texture atlas from individual tile images.

    Args:
        tiles_folder: Folder containing individual tile PNGs.
        output_folder: Folder where atlas PNG and JSON metadata will be saved.
        tile_size: Pixel size of each tile.
        cols: Number of columns in the atlas.
        rows: Number of rows in the atlas.
        tile_layout: List of tile names in row-major order.
    """
    atlas_width = tile_size * cols
    atlas_height = tile_size * rows

    print("="*60)
    print("Texture Atlas Generator (Generic)")
    print("="*60)
    print(f"Tile size: {tile_size}×{tile_size}")
    print(f"Atlas size: {atlas_width}×{atlas_height} ({cols}×{rows} grid)")
    print(f"Source folder: {tiles_folder}")
    print(f"Output folder: {output_folder}\n")

    # Create blank transparent atlas
    atlas = Image.new("RGBA", (atlas_width, atlas_height), (0, 0, 0, 0))
    regions = []

    for idx, tile_name in enumerate(tile_layout):
        row = idx // cols
        col = idx % cols

        tile_path = os.path.join(tiles_folder, f"{tile_name}.png")
        if not os.path.exists(tile_path):
            print(f"⚠️  WARNING: {tile_name}.png not found, skipping...")
            continue

        try:
            img = Image.open(tile_path)
            if img.size != (tile_size, tile_size):
                print(f"⚠️  {tile_name}.png is {img.size}, resizing to {tile_size}×{tile_size}")
                img = img.resize((tile_size, tile_size), Image.NEAREST)

            x = col * tile_size
            y = row * tile_size
            atlas.paste(img, (x, y))

            regions.append({"id": tile_name, "row": row, "col": col})
            print(f"✅ [{row},{col}] {tile_name:15s} -> ({x},{y})")

        except Exception as e:
            print(f"❌ ERROR loading {tile_name}.png: {e}")

    print(f"\nTotal tiles processed: {len(regions)}")

    # Ensure output folder exists
    os.makedirs(output_folder, exist_ok=True)

    # Save atlas image
    atlas_path = os.path.join(output_folder, "atlas.png")
    atlas.save(atlas_path, "PNG")
    print(f"✅ Atlas saved: {atlas_path}")

    # Save metadata JSON
    metadata = {
        "tileSize": tile_size,
        "atlasWidth": atlas_width,
        "atlasHeight": atlas_height,
        "regions": regions
    }
    json_path = os.path.join(output_folder, "atlas.json")
    with open(json_path, "w") as f:
        json.dump(metadata, f, indent=2)
    print(f"✅ Metadata saved: {json_path}")

    print("\n" + "="*60)
    print("Atlas generation complete!")
    print("="*60)

    return atlas_path, json_path


if __name__ == "__main__":
    import sys

    default_tiles_folder = "res/tiles"
    default_output_folder = "res/atlas"

    tiles_folder = sys.argv[1] if len(sys.argv) > 1 else default_tiles_folder
    output_folder = sys.argv[2] if len(sys.argv) > 2 else default_output_folder

    if not os.path.exists(tiles_folder):
        print(f"❌ Tiles folder not found: {tiles_folder}")
        sys.exit(1)

    create_atlas(tiles_folder, output_folder)