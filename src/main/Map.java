package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class Map {

	RiverTile[][] river;
	BufferedImage grass;
	int xBoat1;
	int yBoat1;
	int xBoat2;
	int yBoat2;
	double rotationBoat1;
	double rotationBoat2;
	int winX;
	int winY;
	
	public Map(RiverTile[][] river, BufferedImage grass, int xBoat1, int yBoat1,
			int xBoat2, int yBoat2, double rotationBoat1, double rotationBoat2, int winX, int winY) {
		
		this.river = river;
		this.grass = grass;
		this.xBoat1 = xBoat1;
		this.yBoat1 = yBoat1;
		this.xBoat2 = xBoat1;
		this.yBoat2 = yBoat2;
		this.rotationBoat1 = rotationBoat1;
		this.rotationBoat2 = rotationBoat2;
		this.winX = winX;
		this.winY = winY;
	}
	
	public void draw(Graphics2D g) {
		for (int i = 0; i < river.length; i++) {
			for (int j = 0; j < river[0].length; j++) {
				river[i][j].draw(g, 150 + (i*25), j*25);
			}
		}
		g.drawImage(grass, 150, 0, null);
	}
	
}
