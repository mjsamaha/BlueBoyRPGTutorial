package com.mjsamaha.game.managers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.mjsamaha.game.GamePanel;

public class MouseHandler implements MouseListener, MouseMotionListener {
	
	GamePanel gp;
	
	public boolean mousePressed = false;
	public int mouseX, mouseY;
	public int mouseButton = -1; // -1 = no button, 1 = left button, 2 = middle button, 3 = right button
	
	public MouseHandler(GamePanel gp) {
		
		this.gp = gp;
	}

	public void mouseClicked(MouseEvent e) {
		// Called when mouse is clicked (pressed and released quickly)

	}

	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		mouseX = e.getX();
		mouseY = e.getY();
		mouseButton = e.getButton(); // 1=left, 2=middle, 3=right
		
	}

	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		mouseButton = -1;
		
	}

	public void mouseEntered(MouseEvent e) {
		// Called when mouse enters the component

		
	}

	public void mouseExited(MouseEvent e) {
		// Called when mouse exits the component

		
	}

	public void mouseDragged(MouseEvent e) {
		// Called when mouse is moved while button is pressed
		mouseX = e.getX();
		mouseY = e.getY();
		
	}

	public void mouseMoved(MouseEvent e) {
		// Called when mouse is moved (no button pressed)
		mouseX = e.getX();
		mouseY = e.getY();
		
	}
	

}
