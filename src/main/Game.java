package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 400;
	public static int height = (width/16) * 9;
	public static int scale = 3;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	Boat boat1;
	Boat boat2;
	
	Map[] maps = new Map[4];
	BufferedImage[] profiles = new BufferedImage[4];
	
	int currentMap;
	
	boolean inGame = false;
	Menu menu;
	
	private Keyboard key;
	
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		frame = new JFrame();
		
		key = new Keyboard();
		addKeyListener(key);
				
		currentMap = 0;
		
		File path;
		BufferedImage[] mapImages = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++) {
			//load maps
			path = new File("../Cardirow/src/Images/map"+i+".png");
			try {
				mapImages[i] = ImageIO.read(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//load river tile sprite
		BufferedImage riverImage = null;
		path = new File("../Cardirow/src/Images/river.png");
		try {
			riverImage = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RiverTile riverTile = new RiverTile();
		BufferedImage[] riverTiles = new BufferedImage[3];
		for (int i = 0; i < 3; i++) {
			riverTiles[i] = riverImage.getSubimage(i*25, 0, 25, 25);
		}
		
		riverTile.setFrames(riverTiles);
		
		RiverTile[][] river = new RiverTile[36][27];
		for (int i = 0; i < 36; i++) {
			for (int j = 0; j < 27; j++) {
				river[i][j] = riverTile;
			}
		}
			
		maps[0] = new Map(river, mapImages[0], 730+150, 620, 800+150, 620, Math.PI/2, Math.PI/2, 160, -1);
		maps[1] = new Map(river, mapImages[1], 830+150, 250, 830+150, 400, 0, 0, 160, -1);
		maps[2] = new Map(river, mapImages[2], 190+150, 620, 680+150, 620, Math.PI/2, Math.PI/2, -1, 10);
		maps[3] = new Map(river, mapImages[3], 680+150, 40, 740+150, 40, -Math.PI/2, -Math.PI/2, -1, 670);
		
		boat1 = new Boat(0, maps[0].xBoat1, maps[0].yBoat1, width*scale, height*scale, maps[0]);
		boat2 = new Boat(1, maps[0].xBoat2, maps[0].yBoat2, width*scale, height*scale, maps[0]);
		
		BufferedImage sprites = null;
		path = new File("../Cardirow/src/Images/sprites.png");
		try {
			sprites = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 4; i++) {
			BufferedImage idle = sprites.getSubimage(0, 40*i, 52, 40);
			BufferedImage[] frames = new BufferedImage[6];
			for (int j = 1; j < 7; j++) {
				frames[j-1] = sprites.getSubimage(j*52, i*40, 52, 40);
			}
			if (i==0) {
				boat1.getLeftRower().setFrames(idle, frames);
			}
			if (i==1) {
				boat1.getRightRower().setFrames(idle, frames);
			}
			if (i==2) {
				boat2.getLeftRower().setFrames(idle, frames);
			}
			if (i==3) {
				boat2.getRightRower().setFrames(idle, frames);
			}
		}
		
		//load profiles
		BufferedImage profilePictures = null;
		path = new File("../Cardirow/src/Images/profiles.png");
		try {
			profilePictures = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < profiles.length; i++) {
			profiles[i] = profilePictures.getSubimage(i*130, 0, 130, 130);
		}
		
		menu = new Menu(200, 200, width - 80, height - 80);
		menu.addMenuItem("Start");
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Cardirow");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.requestFocus();
		
		game.start();
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer  = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle("Cardirow" + " | " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
		
	public void update() {
//		if (key.up) y--;
//		if (key.down) y++;
//		if (key.left) x--;
//		if (key.right) x++;
		
		if(inGame) {
		
			boat1.update();
			boat2.update();
			
			if (boat1.hasWon() || boat2.hasWon()) {
				changeLevel();
			}
			
			if(key.left) {
				key.left = false;
				boat1.leftRow();
			}
			if(key.right) {
				key.right = false;
				boat1.rightRow();
			}
			if(key.a) {
				key.left = false;
				boat2.leftRow();
			}
			if(key.d) {
				key.right = false;
				boat2.rightRow();
			}
		} else {
			if(key.up || key.w) {
				key.up = false;
				key.w = false;
				menu.moveUp();
			} else if(key.down || key.s) {
				key.down = false;
				key.s = false;
				menu.moveDown();
			}
			if(key.enter) {
				key.enter = false;
				
				inGame = true;
			}
		}
		
	}
	
	public void changeLevel() {
		currentMap++;
		if (currentMap < maps.length) {
			boat1 = new Boat(0, maps[currentMap].xBoat1, maps[currentMap].yBoat1, width*scale, height*scale, maps[currentMap], maps[currentMap].rotationBoat1);
			boat2 = new Boat(1, maps[currentMap].xBoat2, maps[currentMap].yBoat2, width*scale, height*scale, maps[currentMap], maps[currentMap].rotationBoat2);
			
			BufferedImage sprites = null;
			File path = new File("../Cardirow/src/Images/sprites.png");
			try {
				sprites = ImageIO.read(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < 4; i++) {
				BufferedImage idle = sprites.getSubimage(0, 40*i, 52, 40);
				BufferedImage[] frames = new BufferedImage[6];
				for (int j = 1; j < 7; j++) {
					frames[j-1] = sprites.getSubimage(j*52, i*40, 52, 40);
				}
				if (i==0) {
					boat1.getLeftRower().setFrames(idle, frames);
				}
				if (i==1) {
					boat1.getRightRower().setFrames(idle, frames);
				}
				if (i==2) {
					boat2.getLeftRower().setFrames(idle, frames);
				}
				if (i==3) {
					boat2.getRightRower().setFrames(idle, frames);
				}
			}
		}
		else {
			inGame = false;
		}
	}
	
	public void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if(inGame) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 150, getHeight());
			g.fillRect(1050, 0, 150, getHeight());
			g.drawImage(profiles[0], 0, 30, null);
			g.drawImage(profiles[1], 0, 330, null);
			g.drawImage(profiles[2], 1070, 30, null);
			g.drawImage(profiles[3], 1070, 330, null);
			maps[currentMap].draw(g);
			renderStats(g, boat1);
			renderStats(g, boat2);
			AffineTransform at = g.getTransform();
			boat1.draw(g);
			g.setTransform(at);
			boat2.draw(g);
			g.setTransform(at);
		} else {
			menu.draw(g);
			g.drawString("Enter", 600, 600);
		}
		drawRightControlsString(g, 30, 600);
		drawLeftControlsString(g, 1100, 600);
		g.dispose();
		bs.show();
	}
	
	private void renderStats(Graphics2D g, Boat boat) {
		//g.setColor(Color.WHITE);
		//g.drawString("Left Rower: ", 40 + boat.getBoatNumber() * 1010, 230);


		drawBoldString(g, boat.getLeftRower(), 60 + boat.getBoatNumber() * 1060, 230);

		drawBoldString(g, boat.getRightRower(), 60 + boat.getBoatNumber() * 1060, 530);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans", Font.BOLD, 20));
		
	}
	
	private void drawBoldString(Graphics2D g, Rower rower, int x, int y) {
		String text = "" + (int) rower.getHeartRate();
				
		setColour(g, rower);
		g.setFont(new Font("Sans", Font.BOLD, 20));
		g.drawString(text, x, y);
		
	}
	
	private void drawLeftControlsString(Graphics2D g, int x, int y) {
		String text = "A, D";
				
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans", Font.BOLD, 20));
		g.drawString(text, x, y);
		
	}
	
	private void drawRightControlsString(Graphics2D g, int x, int y) {
		String text = "Left, Right"; 
				
		g.setColor(Color.WHITE);
		g.setFont(new Font("Sans", Font.BOLD, 20));
		g.drawString(text, x, y);
		
	}
	
	private void setColour(Graphics2D g, Rower rower) {
		if(rower.getState().equals(RowerState.BurntOut)) {
			g.setColor(Color.RED);
		} else if(rower.getHeartRate() > 180) {
			g.setColor(Color.ORANGE);
		} else if(rower.getHeartRate() > 140) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.GREEN);
		}
	}

}
