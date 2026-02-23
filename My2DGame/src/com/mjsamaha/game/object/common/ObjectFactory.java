package com.mjsamaha.game.object.common;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.object.BootObject;
import com.mjsamaha.game.object.ChestObject;
import com.mjsamaha.game.object.DoorObject;
import com.mjsamaha.game.object.KeyObject;

public class ObjectFactory {

    public static GameObject create(GamePanel gp, String type) {

        switch (type) {

            case "KEY":
                return new KeyObject(gp);

            case "BOOTS":
                return new BootObject(gp);

            case "DOOR":
                return new DoorObject(gp);

            case "CHEST":
                return new ChestObject(gp);

            default:
                return null;
        }
    }
}