package com.mjsamaha.game.managers;

import com.mjsamaha.game.GamePanel;
import com.mjsamaha.game.entity.common.Entity;
import com.mjsamaha.game.tile.TileDefinition;
import com.mjsamaha.game.tile.TileRegistry;

public class CollisionDetection {

    GamePanel gp;

    public CollisionDetection(GamePanel gp) {
        this.gp = gp;
    }


    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidAreaDefaultX;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;

        int entityTopWorldY = entity.worldY + entity.solidAreaDefaultY;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {

            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                TileDefinition up1 = TileRegistry.get(tileNum1);
                TileDefinition up2 = TileRegistry.get(tileNum2);
                if ((up1 != null && up1.hasCollision()) || (up2 != null && up2.hasCollision())) {
                    entity.collisionOn = true;
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                TileDefinition down1 = TileRegistry.get(tileNum1);
                TileDefinition down2 = TileRegistry.get(tileNum2);
                if ((down1 != null && down1.hasCollision()) || (down2 != null && down2.hasCollision())) {
                    entity.collisionOn = true;
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                TileDefinition left1 = TileRegistry.get(tileNum1);
                TileDefinition left2 = TileRegistry.get(tileNum2);
                if ((left1 != null && left1.hasCollision()) || (left2 != null && left2.hasCollision())) {
                    entity.collisionOn = true;
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                TileDefinition right1 = TileRegistry.get(tileNum1);
                TileDefinition right2 = TileRegistry.get(tileNum2);
                if ((right1 != null && right1.hasCollision()) || (right2 != null && right2.hasCollision())) {
                    entity.collisionOn = true;
                }
                break;
        }
    }


    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        int futureX = entity.worldX;
        int futureY = entity.worldY;

        switch (entity.direction) {
            case "up": futureY -= entity.speed; break;
            case "down": futureY += entity.speed; break;
            case "left": futureX -= entity.speed; break;
            case "right": futureX += entity.speed; break;
        }

        int entityLeft   = futureX + entity.solidAreaDefaultX;
        int entityRight  = entityLeft + entity.solidArea.width;
        int entityTop    = futureY + entity.solidAreaDefaultY;
        int entityBottom = entityTop + entity.solidArea.height;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                int objLeft   = gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX;
                int objRight  = objLeft + gp.obj[i].solidArea.width;
                int objTop    = gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY;
                int objBottom = objTop + gp.obj[i].solidArea.height;

                boolean intersect =
                        entityRight  > objLeft &&
                        entityLeft   < objRight &&
                        entityBottom > objTop &&
                        entityTop    < objBottom;

                if (intersect) {

                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }

                    if (player) {
                        index = i;
                    }

                    break;
                }
            }
        }

        return index;
    }


    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        int futureX = entity.worldX;
        int futureY = entity.worldY;

        switch (entity.direction) {
            case "up": futureY -= entity.speed; break;
            case "down": futureY += entity.speed; break;
            case "left": futureX -= entity.speed; break;
            case "right": futureX += entity.speed; break;
        }

        int entityLeft   = futureX + entity.solidAreaDefaultX;
        int entityRight  = entityLeft + entity.solidArea.width;
        int entityTop    = futureY + entity.solidAreaDefaultY;
        int entityBottom = entityTop + entity.solidArea.height;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null && target[i] != entity) {

                int targetLeft   = target[i].worldX + target[i].solidAreaDefaultX;
                int targetRight  = targetLeft + target[i].solidArea.width;
                int targetTop    = target[i].worldY + target[i].solidAreaDefaultY;
                int targetBottom = targetTop + target[i].solidArea.height;

                boolean intersect =
                        entityRight  > targetLeft &&
                        entityLeft   < targetRight &&
                        entityBottom > targetTop &&
                        entityTop    < targetBottom;

                if (intersect) {
                    entity.collisionOn = true;
                    index = i;
                    break;
                }
            }
        }

        return index;
    }


    public boolean checkPlayer(Entity entity) {
        
        // Calculate future position based on entity's direction and speed
        int futureX = entity.worldX;
        int futureY = entity.worldY;
        
        switch (entity.direction) {
            case "up": futureY -= entity.speed; break;
            case "down": futureY += entity.speed; break;
            case "left": futureX -= entity.speed; break;
            case "right": futureX += entity.speed; break;
        }

        int entityLeft   = futureX + entity.solidAreaDefaultX;
        int entityRight  = entityLeft + entity.solidArea.width;
        int entityTop    = futureY + entity.solidAreaDefaultY;
        int entityBottom = entityTop + entity.solidArea.height;

        int playerLeft   = gp.player.worldX + gp.player.solidAreaDefaultX;
        int playerRight  = playerLeft + gp.player.solidArea.width;
        int playerTop    = gp.player.worldY + gp.player.solidAreaDefaultY;
        int playerBottom = playerTop + gp.player.solidArea.height;

        return (
            entityRight  > playerLeft &&
            entityLeft   < playerRight &&
            entityBottom > playerTop &&
            entityTop    < playerBottom
        );
    }
}