package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {
	
	boolean left, right, a, d;
	boolean leftReleased = true, rightReleased = true, aReleased = true, dReleased = true;
	
	boolean w, s, up, down, enter;
	boolean wReleased = true, sReleased = true, upReleased = true, downReleased = true, enterReleased = true;
	
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 37: 
				if (leftReleased) {
				left = true;
				leftReleased = false;
			}
				break;
			case 39: 
				if (rightReleased) {
					right = true;
					rightReleased = false;
				}
				break;
			case 65: 
				if (aReleased) {
					a = true;
					aReleased = false;
				}
				break;
			case 68: 
				if (dReleased) {
					d = true;
					dReleased = false;
				}
				break;
			case 38: if (upReleased) {
				up = true;
				upReleased = false;
			}
				break;
			case 40: if (downReleased) {
				down = true;
				downReleased = false;
			}
				break;
			case KeyEvent.VK_W: if (wReleased) {
				w = true;
				wReleased = false;
			}
				break;
			case KeyEvent.VK_S: if (sReleased) {
				s = true;
				sReleased = false;
			}
			break;
			case 10: if (enterReleased) {
				enter = true;
				enterReleased = false;
			}
			break;
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 37: left = false;
		leftReleased = true;
			break;
		case 39: right = false;
		rightReleased = true;
			break;
		case 65: a = false;
		aReleased = true;
			break;
		case 68: d = false;
		dReleased = true;
			break;
		case 10: enter = false;
			enterReleased = true;
			break;
		case 38: up = false;
		upReleased = true;
			break;
		case 40: down = false;
		downReleased = true;
			break;
		case KeyEvent.VK_W: w = false;
		wReleased = true;
			break;
		case KeyEvent.VK_S: s = false;
		sReleased = true;
			break;
	}
	}
	
	public void keyTyped(KeyEvent e) {
	}

}
