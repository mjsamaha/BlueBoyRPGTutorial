package com.mjsamaha.game.managers;

import java.util.logging.Logger;

import com.mjsamaha.game.Constants;

public class GameStateManager {

	private static final Logger LOGGER = Logger.getLogger(GameStateManager.class.getSimpleName());

	private int currentState;

	public GameStateManager() {
		this.currentState = Constants.GameState.MENU;
	}

	/**
	 * Get the current game state
	 */
	public int getCurrentState() {
		return currentState;
	}

	/**
	 * Set the game state
	 */
	public void setState(int state) {
		this.currentState = state;
	}

	/**
	 * Check if currently in menu state
	 */
	public boolean isMenuState() {
		return currentState == Constants.GameState.MENU;
	}

	/**
	 * Check if currently in play state
	 */
	public boolean isPlayState() {
		return currentState == Constants.GameState.PLAY;
	}

	public boolean isCharacterState() {
		return currentState == Constants.GameState.CHARACTER_STATE;
	}

	/**
	 * Check if currently in pause state
	 */
	public boolean isPauseState() {
		return currentState == Constants.GameState.PAUSE;
	}

	/**
	 * Check if currently in dialogue state
	 */
	public boolean isDialogueState() {
		return currentState == Constants.GameState.DIALOGUE;
	}

	/**
	 * Transition to menu state
	 */
	public void toMenuState() {
		currentState = Constants.GameState.MENU;
		LOGGER.info("Transitioned to MENU state");
	}

	/**
	 * Transition to play state
	 */
	public void toPlayState() {
		currentState = Constants.GameState.PLAY;
		LOGGER.info("Transitioned to PLAY state");
	}

	public void toCharacterState() {
		currentState = Constants.GameState.CHARACTER_STATE;
		LOGGER.info("Transitioned to CHARACTER screen state");
	}

	/**
	 * Transition to pause state
	 */
	public void toPauseState() {
		currentState = Constants.GameState.PAUSE;
		LOGGER.info("Transitioned to PAUSE state");
	}

	/**
	 * Transition to dialogue state
	 */
	public void toDialogueState() {
		currentState = Constants.GameState.DIALOGUE;
		LOGGER.info("Transitioned to DIALOGUE state");
	}

	/**
	 * Toggle between play and pause states
	 */
	public void togglePause() {
	    if (currentState == Constants.GameState.PLAY) {
	        toPauseState();  // Logs automatically
	    } else if (currentState == Constants.GameState.PAUSE) {
	        toPlayState();   // Logs automatically
	    }
	}
}
