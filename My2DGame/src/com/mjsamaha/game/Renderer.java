package com.mjsamaha.game;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;

import com.mjsamaha.game.entity.common.Entity;

public class Renderer {
    private final GamePanel gp;
    private final ArrayList<Entity> entityList = new ArrayList<>();
    
    public Renderer(GamePanel gp) {
        this.gp = gp;
    }
    
    public void render(Graphics2D g2) {
        if (gp.stateManager.isMenuState()) {
            renderMenu(g2);
        } else {
            // All other states render the game world
            renderGame(g2);
        }
    }
    
    private void renderMenu(Graphics2D g2) {
        gp.ui.draw(g2);
    }
    
    private void renderGame(Graphics2D g2) {
        // Draw tiles
        gp.tileM.draw(g2);
        
        // Build and sort entity list
        buildEntityList();
        sortEntitiesByDepth();
        drawEntities(g2);
        entityList.clear();
        
        // Draw UI on top
        gp.ui.draw(g2);
    }
    
    private void buildEntityList() {
        entityList.add(gp.player);
        
        addArrayToList(gp.npc);
        addArrayToList(gp.obj);
        addArrayToList(gp.monster);
    }
    
    private void addArrayToList(Entity[] entities) {
        for (Entity entity : entities) {
            if (entity != null) {
                entityList.add(entity);
            }
        }
    }
    
    private void sortEntitiesByDepth() {
        entityList.sort(Comparator.comparingInt(e -> e.worldY));
    }
    
    private void drawEntities(Graphics2D g2) {
        for (Entity entity : entityList) {
            entity.draw(g2);
        }
    }
}