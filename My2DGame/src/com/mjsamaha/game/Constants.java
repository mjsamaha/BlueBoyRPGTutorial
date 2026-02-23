package com.mjsamaha.game;

public class Constants {

	/**
	 * Window Configuration
	 */
	public static final class Window {
		public static final String TITLE = "My 2D Game";
		public static final String VERSION = "1.0";

		private Window() {
		} // Prevent instantiation
	}

	/**
	 * Screen and Display Settings
	 */
	public static final class Screen {
		// Original tile size (16x16 pixels)
		public static final int ORIGINAL_TILE_SIZE = 16;

		// Scale factor for tiles
		public static final int SCALE = 3;

		// Actual tile size after scaling (48x48 pixels)
		public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

		// Screen dimensions in tiles (4:3 aspect ratio)
		public static final int MAX_SCREEN_COL = 16;
		public static final int MAX_SCREEN_ROW = 12;

		// Screen dimensions in pixels
		public static final int WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
		public static final int HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels

		private Screen() {
		} // Prevent instantiation
	}

	/**
	 * World Settings
	 */
	public static final class World {
		// World dimensions in tiles
		public static final int MAX_COL = 50;
		public static final int MAX_ROW = 50;

		// World dimensions in pixels
		public static final int WIDTH = Screen.TILE_SIZE * MAX_COL;
		public static final int HEIGHT = Screen.TILE_SIZE * MAX_ROW;

		private World() {
		} // Prevent instantiation
	}

	/**
	 * Game Loop Settings
	 */
	public static final class GameLoop {
		// Target frames per second
		public static final int FPS = 60;

		// Draw interval in nanoseconds (1 second / FPS)
		public static final double DRAW_INTERVAL = 1000000000.0 / FPS;

		// Timer interval for FPS display (1 second in nanoseconds)
		public static final long TIMER_INTERVAL = 1000000000L;

		private GameLoop() {
		} // Prevent instantiation
	}

	/**
	 * Game State Constants
	 */
	public static final class GameState {
		public static final int MENU = 0;
		public static final int PLAY = 1;
		public static final int PAUSE = 2;
		public static final int DIALOGUE = 3;
		public static final int CHARACTER_STATE = 4;

		private GameState() {
		} // Prevent instantiation
	}

	/**
	 * Entity and Object Array Sizes
	 */
	public static final class EntityArrays {
		// Maximum number of objects in the game world
		public static final int MAX_OBJECTS = 10;

		// Maximum number of NPCs
		public static final int MAX_NPC = 10;

		// Maximum number of monsters
		public static final int MAX_MONSTERS = 20;

		// Default entity speed
		public static final int DEFAULT_SPEED = 1;

		// Player speed
		public static final int PLAYER_SPEED = 4;

		private EntityArrays() {
		} // Prevent instantiation
	}

	/**
	 * Player Settings
	 */
	public static final class Player {
		// Starting position in tiles
		public static final int START_WORLD_X = Screen.TILE_SIZE * 23;
		public static final int START_WORLD_Y = Screen.TILE_SIZE * 21;

		// Health settings
		public static final int MAX_HEALTH = 6;

		// Collision box settings
		public static final int COLLISION_BOX_X = 8;
		public static final int COLLISION_BOX_Y = 16;
		public static final int COLLISION_BOX_WIDTH = 32;
		public static final int COLLISION_BOX_HEIGHT = 32;

		// Movement settings
		public static final int SPEED = 4;
		public static final double DIAGONAL_SPEED_MULTIPLIER = 0.707; // sqrt(2)/2

		// Invincibility settings
		public static final int INVINCIBILITY_DURATION = 60; // frames (1 second at 60 FPS)
		public static final float INVINCIBILITY_ALPHA = 0.3f;

		private Player() {
		} // Prevent instantiation
	}

	/**
	 * Animation Settings
	 */
	public static final class Animation {
		// Sprite animation speed (frames per sprite change)
		public static final int SPRITE_CHANGE_INTERVAL = 12;

		// Number of sprite frames per animation
		public static final int SPRITE_FRAMES = 2;

		private Animation() {
		} // Prevent instantiation
	}

	/**
	 * NPC Settings
	 */
	public static final class NPC {
		// Action lock duration (frames before changing direction)
		public static final int ACTION_LOCK_DURATION = 120; // 2 seconds at 60 FPS

		private NPC() {
		} // Prevent instantiation
	}

	/**
	 * Monster Settings
	 */
	public static final class Monster {
		// Green Slime settings
		public static final int SLIME_SPEED = 1;
		public static final int SLIME_MAX_HEALTH = 2;
		public static final int SLIME_DAMAGE = 1;

		// Collision box for Green Slime
		public static final int SLIME_COLLISION_X = 3;
		public static final int SLIME_COLLISION_Y = 18;
		public static final int SLIME_COLLISION_WIDTH = 42;
		public static final int SLIME_COLLISION_HEIGHT = 30;

		// Action lock duration
		public static final int ACTION_LOCK_DURATION = 120;

		private Monster() {
		} // Prevent instantiation
	}

	/**
	 * Dialogue Constants
	 */
	public static final class Dialogue {
		// Maximum number of dialogue lines per entity
		public static final int MAX_DIALOGUE_LINES = 20;

		private Dialogue() {
		} // Prevent instantiation
	}

	/**
	 * Private constructor to prevent instantiation of Constants class
	 */
	private Constants() {
		throw new AssertionError("Cannot instantiate Constants class");
	}

}
