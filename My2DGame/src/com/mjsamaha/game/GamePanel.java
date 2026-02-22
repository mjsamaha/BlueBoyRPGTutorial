package com.mjsamaha.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.mjsamaha.game.audio.Sound;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.audio.SoundManager;
import com.mjsamaha.game.audio.SoundRegistry;
import com.mjsamaha.game.entity.Entity;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.managers.AssetManager;
import com.mjsamaha.game.managers.CollisionDetection;
import com.mjsamaha.game.managers.EventHandler;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.managers.MouseHandler;
import com.mjsamaha.game.tile.TileManager;
import com.mjsamaha.game.ui.UserInterface;

public class GamePanel extends JPanel implements Runnable {

	// Screen Settings (using Constants)
	public final int tileSize = Constants.Screen.TILE_SIZE;
	public final int maxScreenCol = Constants.Screen.MAX_SCREEN_COL;
	public final int maxScreenRow = Constants.Screen.MAX_SCREEN_ROW;
	public final int screenWidth = Constants.Screen.WIDTH;
	public final int screenHeight = Constants.Screen.HEIGHT;

	// World settings (using Constants)
	public final int maxWorldCol = Constants.World.MAX_COL;
	public final int maxWorldRow = Constants.World.MAX_ROW;

	// Game thread
	public Thread gameThread;

	// System managers
	public EventHandler eHandler = new EventHandler(this);
	public KeyHandler keyH = new KeyHandler();
	public MouseHandler mouseH = new MouseHandler(this);
	public TileManager tileM = new TileManager(this);
	public CollisionDetection cChecker = new CollisionDetection(this);
	public AssetManager aManager = new AssetManager(this);

	// Audio manager for new system
	public SoundManager soundManager = new SoundManager();

	// UI
	public UserInterface ui = new UserInterface(this);
	public static final int DIALOGUE_LINE_HEIGHT = 40;
	public boolean checkDrawTime = false;

	// Entity and Objects (using Constants for array sizes)
	public Entity obj[] = new Entity[Constants.EntityArrays.MAX_OBJECTS];
	public Player player = new Player(this, keyH);
	public Entity[] npc = new Entity[Constants.EntityArrays.MAX_NPC];
	public Entity monster[] = new Entity[Constants.EntityArrays.MAX_MONSTERS];
	public ArrayList<Entity> entityList = new ArrayList<>();

	// Game state (using Constants)
	public int gameState;
	public final int menuState = Constants.GameState.MENU;
	public final int playState = Constants.GameState.PLAY;
	public final int pauseState = Constants.GameState.PAUSE;
	public final int dialogueState = Constants.GameState.DIALOGUE;

	/**
	 * Constructor - initializes the game panel
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);

		this.addKeyListener(keyH);
		this.addMouseListener(mouseH);
		this.addMouseMotionListener(mouseH);
		this.setFocusable(true);

		initSounds();
	}

	private void initSounds() {
		// Music
		SoundRegistry.register(new Sound("music_blue_boy", "/audio/blueBoyAdventure.wav", 1.0f, 1.0f, true));
		SoundRegistry.register(new Sound("music_first_sample", "/audio/FirstSample.wav", 1.0f, 1.0f, true));
		SoundRegistry.register(new Sound("music_ambient_loop", "/audio/AmbientLoopFour.wav", 1.0f, 1.0f, true));

		// SFX
		SoundRegistry.register(new Sound("sfx_coin", "/audio/coin.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("sfx_powerup", "/audio/powerup.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("sfx_unlock", "/audio/unlock.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("sfx_fanfare", "/audio/fanfare.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("sfx_menu_select", "/audio/menu_selection.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("hitmonster", "/audio/hitmonster.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("receivedamage", "/audio/receivedamage.wav", 1.0f, 1.0f, false));
		SoundRegistry.register(new Sound("sword_sfx", "/audio/sword_sfx.wav", 1.0f, 1.0f, false));
	}

	/**
	 * Setup game - initialize game objects and state
	 */
	public void setupGame() {
		aManager.setObject();
		aManager.setNPC();
		aManager.setMonster();

		playMusic(SoundEvent.MUSIC_AMBIENT_LOOP);
		gameState = menuState;
	}

	/**
	 * Start the game thread
	 */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	/**
	 * Main game loop using delta time
	 */
	@Override
	public void run() {
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / Constants.GameLoop.DRAW_INTERVAL;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();

				delta--;
				drawCount++;
			}

			if (timer >= Constants.GameLoop.TIMER_INTERVAL) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}

		soundManager.tick();
	}

	/**
	 * Update game logic
	 */
	public void update() {

		switch (gameState) {

		case menuState -> {
			// Menu handles input, selection, and sound
			ui.getMenuScreen().update(keyH);
		}

		case playState -> {
			// Update player
			player.update();

			// Update NPCs
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null)
					npc[i].update();
			}

			// Update monsters
			for (int i = 0; i < monster.length; i++) {
			    if (monster[i] != null) {
			        if (monster[i].alive && !monster[i].dying) {
			            monster[i].update();
			        }
			        if (!monster[i].alive) {
			            monster[i] = null;
			        }
			    }
			}

			// Check for world events
			eHandler.checkEvent();

			// Game state switching (pause)
			if (keyH.cancelPressed) {
				gameState = pauseState;
				keyH.cancelPressed = false;
			}
		}

		case pauseState -> {
		    // Let pause screen handle input
		    ui.getPauseScreen().update(keyH);

		    // Toggle back with ESC
		    if (keyH.cancelPressed) {
		        gameState = playState;
		        keyH.cancelPressed = false;
		    }
		}

		case dialogueState -> {
			// Wait for confirm press to close dialogue
			if (keyH.confirmPressed) {
				gameState = playState; // back to gameplay
				keyH.confirmPressed = false;
				ui.getState().dialogueActive = false; // reset dialogue flag
			}
		}
		}
	}

	/**
	 * Render the game
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// Optional debug timing
		long drawStart = 0;
		if (checkDrawTime)
			drawStart = System.nanoTime();

		switch (gameState) {

		case menuState -> ui.draw(g2); // Menu handles its own drawing

		case playState, pauseState, dialogueState -> {
			// Draw tiles
			tileM.draw(g2);

			// Build entity list for sorting
			entityList.add(player);

			for (int i = 0; i < npc.length; i++)
				if (npc[i] != null)
					entityList.add(npc[i]);

			for (int i = 0; i < obj.length; i++)
				if (obj[i] != null)
					entityList.add(obj[i]);

			for (int i = 0; i < monster.length; i++)
				if (monster[i] != null)
					entityList.add(monster[i]);

			// Sort entities by Y position
			entityList.sort((e1, e2) -> Integer.compare(e1.worldY, e2.worldY));

			// Draw all entities
			for (Entity e : entityList)
				e.draw(g2);

			// Clear for next frame
			entityList.clear();

			// Draw UI on top
			ui.draw(g2);
		}
		}

		g2.dispose();
	}

	/**
	 * Play background music
	 */
	public void playMusic(SoundEvent music) {
		soundManager.playSound(music);
	}

	/**
	 * Play sound effect
	 */
	public void playSE(SoundEvent sfx) {
		soundManager.playSound(sfx);
	}

}