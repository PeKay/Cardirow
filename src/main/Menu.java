package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Menu {

	int x, y, width, height;
	
	List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	int selectedItem = 0;
	
	public Menu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void addMenuItem(String text) {
		menuItems.add(new MenuItem(text));
	}
	
	public void moveUp() {
		selectedItem--;
		if (selectedItem < 0) selectedItem = (menuItems.size() - 1);
	}
	
	public void moveDown() {
		selectedItem++;
		selectedItem = selectedItem % menuItems.size();
	}
	
	public int getSelectedItem() {
		return selectedItem;
	}
	
	private final int SPACING = 10;
	
	public void draw(Graphics2D g) {
		int heightPerComponent = height / menuItems.size();
		
		for(int i = 0; i < menuItems.size(); i++) {
			if(i == selectedItem) {
				g.setColor(Color.WHITE);
				g.fillOval(x, y + (heightPerComponent * i), 8, 8);
			}
			menuItems.get(i).draw(g, x + SPACING, y + (heightPerComponent * i) + SPACING);
		}
	}
}
