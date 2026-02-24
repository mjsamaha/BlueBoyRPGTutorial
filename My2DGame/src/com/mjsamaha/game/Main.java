package com.mjsamaha.game;

import java.util.logging.Logger;

import javax.swing.JFrame;

import com.mjsamaha.game.util.GameLogger;

public class Main {
	
	private static final Logger LOGGER =
	        Logger.getLogger(Main.class.getSimpleName());

	public static void main(String[] args) {
	    GameLogger.init();
	    LOGGER.info("Game starting...");

		JFrame window = new JFrame();
	    LOGGER.info("Window initialized.");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle(Constants.Window.TITLE + " " + Constants.Window.VERSION);
		
		GamePanel gamePanel = new GamePanel();

		window.add(gamePanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.setupGame();
	    LOGGER.info("Game setup complete.");


		gamePanel.startGameThread();
	    LOGGER.info("Game loop started.");

	}

}
