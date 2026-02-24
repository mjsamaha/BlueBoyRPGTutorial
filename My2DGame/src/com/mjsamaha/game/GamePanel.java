package com.mjsamaha.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.mjsamaha.game.audio.AudioInitializer;
import com.mjsamaha.game.audio.Sound;
import com.mjsamaha.game.audio.SoundEvent;
import com.mjsamaha.game.audio.SoundManager;
import com.mjsamaha.game.audio.SoundRegistry;
import com.mjsamaha.game.entity.Player;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.managers.AssetManager;
import com.mjsamaha.game.managers.CollisionDetection;
import com.mjsamaha.game.managers.EventHandler;
import com.mjsamaha.game.managers.GameStateManager;
import com.mjsamaha.game.managers.GameUpdater;
import com.mjsamaha.game.managers.KeyHandler;
import com.mjsamaha.game.managers.MouseHandler;
import com.mjsamaha.game.tile.TileManager;
import com.mjsamaha.game.ui.UserInterface;
import com.mjsamaha.game.ui.overlay.InventoryRenderer;

public class GamePanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    
    // Screen Settings
    public final int tileSize = Constants.Screen.TILE_SIZE;
    public final int maxScreenCol = Constants.Screen.MAX_SCREEN_COL;
    public final int maxScreenRow = Constants.Screen.MAX_SCREEN_ROW;
    public final int screenWidth = Constants.Screen.WIDTH;
    public final int screenHeight = Constants.Screen.HEIGHT;
    
    // World settings
    public final int maxWorldCol = Constants.World.MAX_COL;
    public final int maxWorldRow = Constants.World.MAX_ROW;
    
    // Game thread
    private Thread gameThread;
    
    // Core managers
    public GameStateManager stateManager;
    private GameUpdater gameUpdater;
    public EventHandler eHandler;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public TileManager tileM;
    public CollisionDetection cChecker;
    public AssetManager aManager;
    public SoundManager soundManager;
    private Renderer renderer;
    
    // UI
    public UserInterface ui;
    public static final int DIALOGUE_LINE_HEIGHT = 40;
    public boolean checkDrawTime = false;
    
    // Entities
    public Entity[] obj = new Entity[Constants.EntityArrays.MAX_OBJECTS];
    public Player player;
    public Entity[] npc = new Entity[Constants.EntityArrays.MAX_NPC];
    public Entity[] monster = new Entity[Constants.EntityArrays.MAX_MONSTERS];
    
    public GamePanel() {
        initializePanel();
        initializeManagers();
        initializeAudio();
    }
    
    private void initializePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }
    
    private void initializeManagers() {
        keyH = new KeyHandler();
        mouseH = new MouseHandler(this);
        stateManager = new GameStateManager();
        gameUpdater = new GameUpdater(this);
        eHandler = new EventHandler(this);
        tileM = new TileManager(this);
        cChecker = new CollisionDetection(this);
        aManager = new AssetManager(this);
        soundManager = new SoundManager();
        ui = new UserInterface(this);
        player = new Player(this, keyH);
        renderer = new Renderer(this);
        
        // Add listeners
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
    }
    
    private void initializeAudio() {
        AudioInitializer.initializeSounds();
    }
    
    public void setupGame() {
        aManager.setObject();
        aManager.setNPC();
        aManager.setMonster();
        playMusic(SoundEvent.MUSIC_AMBIENT_LOOP);
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            long currentTime = System.nanoTime();
            
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
    
    public void update() {
        gameUpdater.update(keyH);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        long drawStart = 0;
        if (checkDrawTime) {
            drawStart = System.nanoTime();
        }
        
        renderer.render(g2);
        
        if (checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            System.out.println("Draw time: " + passed);
        }
        
        g2.dispose();
    }
    
    public void playMusic(SoundEvent music) {
        soundManager.playSound(music);
    }
    
    public void playSE(SoundEvent sfx) {
        soundManager.playSound(sfx);
    }
}