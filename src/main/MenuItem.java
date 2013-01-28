package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class MenuItem {
	
	String text;
	
	public MenuItem(String text) {
		this.text= text;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans", Font.BOLD, 26));
		g.drawString(text, x, y);
	}
}