package com.mjsamaha.game.object;

import com.mjsamaha.game.GamePanel;

public class ObjectFactory {

    public static GameObject create(GamePanel gp, String type) {

        switch (type) {

            case "KEY":
                return new OBJ_Key(gp);

            case "BOOTS":
                return new OBJ_Boots(gp);

            case "DOOR":
                return new OBJ_Door(gp);

            case "CHEST":
                return new OBJ_Chest(gp);

            default:
                return null;
        }
    }
}