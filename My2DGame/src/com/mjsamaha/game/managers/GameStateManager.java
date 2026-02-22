package com.mjsamaha.game.managers;

import com.mjsamaha.game.Constants;

public class GameStateManager {
	
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
	}
	
	/**
	 * Transition to play state
	 */
	public void toPlayState() {
		currentState = Constants.GameState.PLAY;
	}
	
	/**
	 * Transition to pause state
	 */
	public void toPauseState() {
		currentState = Constants.GameState.PAUSE;
	}
	
	/**
	 * Transition to dialogue state
	 */
	public void toDialogueState() {
		currentState = Constants.GameState.DIALOGUE;
	}
	
	/**
	 * Toggle between play and pause states
	 */
	public void togglePause() {
		if (currentState == Constants.GameState.PLAY) {
			currentState = Constants.GameState.PAUSE;
		} else if (currentState == Constants.GameState.PAUSE) {
			currentState = Constants.GameState.PLAY;
		}
	}
}






