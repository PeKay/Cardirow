package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class RiverTile extends Animator {
	
	private BufferedImage[] frames = new BufferedImage[3];
	
	public RiverTile() {
		super(3);
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(frames[0], x, y, null);
	}

}
