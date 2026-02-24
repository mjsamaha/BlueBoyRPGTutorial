package com.mjsamaha.game.managers;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean confirmPressed, cancelPressed;
    public boolean characterStatePressed;
    public boolean debugTogglePressed = false;  
    public boolean refreshPressed = false;  
    
    private final InputMapper inputMapper = new InputMapper();
    
    @Override
    public void keyPressed(KeyEvent e) {
        InputMapper.Action action = inputMapper.getAction(e.getKeyCode());
        if (action == null) return;
        
        switch (action) {
            case MOVE_UP -> upPressed = true;
            case MOVE_DOWN -> downPressed = true;
            case MOVE_LEFT -> leftPressed = true;
            case MOVE_RIGHT -> rightPressed = true;
            case CONFIRM -> confirmPressed = true;
            case CANCEL -> cancelPressed = true;
            case CHARACTER_STATE -> characterStatePressed = true;
            case DEBUG -> debugTogglePressed = true; 
            case REFRESH -> refreshPressed = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        InputMapper.Action action = inputMapper.getAction(e.getKeyCode());
        if (action == null) return;
        
        switch (action) {
            case MOVE_UP -> upPressed = false;
            case MOVE_DOWN -> downPressed = false;
            case MOVE_LEFT -> leftPressed = false;
            case MOVE_RIGHT -> rightPressed = false;
            case CONFIRM -> confirmPressed = false;
            case CANCEL -> cancelPressed = false;
            case CHARACTER_STATE -> characterStatePressed = false;
            case DEBUG -> debugTogglePressed = false;  
            case REFRESH -> refreshPressed = false;  
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}
}