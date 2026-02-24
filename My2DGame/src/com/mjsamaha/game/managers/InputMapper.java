package com.mjsamaha.game.managers;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class InputMapper {

    public enum Action {
        MOVE_UP,
        MOVE_DOWN,
        MOVE_LEFT,
        MOVE_RIGHT,
        CONFIRM,
        CANCEL,
        CHARACTER_STATE,
        DEBUG,
        REFRESH
    }

    private final Map<Integer, Action> keyMap = new HashMap<>();

    public InputMapper() {
        // Default bindings
        keyMap.put(KeyEvent.VK_W, Action.MOVE_UP);
        keyMap.put(KeyEvent.VK_UP, Action.MOVE_UP);

        keyMap.put(KeyEvent.VK_S, Action.MOVE_DOWN);
        keyMap.put(KeyEvent.VK_DOWN, Action.MOVE_DOWN);

        keyMap.put(KeyEvent.VK_A, Action.MOVE_LEFT);
        keyMap.put(KeyEvent.VK_D, Action.MOVE_RIGHT);

        keyMap.put(KeyEvent.VK_ENTER, Action.CONFIRM);
        keyMap.put(KeyEvent.VK_ESCAPE, Action.CANCEL);
        keyMap.put(KeyEvent.VK_C, Action.CHARACTER_STATE);
        
        keyMap.put(KeyEvent.VK_F3, Action.DEBUG);
        keyMap.put(KeyEvent.VK_R, Action.REFRESH);
    }

        
        

    public Action getAction(int keyCode) {
        return keyMap.get(keyCode);
    }
}