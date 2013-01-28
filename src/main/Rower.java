package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Rower extends Animator {
	
	// FINAL VALUES
	
	private final double MAX_HEARTRATE = 220;
	private final double INITIAL_HEARTRATE = 50;
	private final double BURNOUT_RECOVER_HEARTRATE = 180;
	private final double RECOVERY_RATE = 40.0 / 60.0;
	private final double EXERTION_RATE = 80.0 / 60.0;
	private final static int ROWING_SPRITES = 6;
	
	private final int DRAW_UPDATE_FREQ = 4;
	
	private BufferedImage[] frames = new BufferedImage[6];
	private BufferedImage idle;
	
	// VARIABLES
	
	private int frame = 0;
	private double heartRate = INITIAL_HEARTRATE;
	private RowerState state = RowerState.Resting;
	private int boatNumber;
	private int position;
	
	
	// Constructors	
	public Rower(int boatNumber, int position) {
		super(ROWING_SPRITES);
		this.boatNumber = boatNumber;
		this.position = position;
	}
	
	public void setFrames(BufferedImage idle, BufferedImage[] frames) {
		this.idle = idle;
		this.frames = frames;
	}
	
	// Calculations
	
	public void update() {
		if(state.equals(RowerState.Resting)) {
			heartRate -= RECOVERY_RATE;
			if(heartRate < INITIAL_HEARTRATE) heartRate = INITIAL_HEARTRATE;	
		} else if(state.equals(RowerState.Rowing)) {			
			heartRate += EXERTION_RATE;
			if(heartRate > MAX_HEARTRATE) {
				setState(RowerState.BurntOut);
			}
		} else if(state.equals(RowerState.BurntOut)) {
			heartRate -= RECOVERY_RATE;
			if(heartRate < BURNOUT_RECOVER_HEARTRATE) {
				setState(RowerState.Resting);
			}
		}
		
		frame ++;
		frame = frame % 60;
		if(frame % DRAW_UPDATE_FREQ == 0) drawUpdate();
	}
	
	public void drawUpdate() {		
		if(state.equals(RowerState.Rowing)) {
			if(currentFrame >= numFrames - 1) {
				setState(RowerState.Resting);
			}
		}
		animate();
	}
	
	public void draw(Graphics2D g, double x, double y) {	
		if(state.equals(RowerState.Resting)) {
			g.drawImage(idle, (int) x, (int) y, null);
		} else if(state.equals(RowerState.Rowing)) {
			g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		} else {
			g.drawImage(idle, (int) x, (int) y, null);
		}
		
	}
	
	public void row() {
		if(state.equals(RowerState.Resting)) {
			setState(RowerState.Rowing);
		}
	}
	
	//Mutators

	public double getHeartRate() {
		return heartRate;
	}

	public void setHeartRate(double heartRate) {
		this.heartRate = heartRate;
	}

	public RowerState getState() {
		return state;
	}

	public void setState(RowerState state) {
		currentFrame = 0;
		this.state = state;
	}

	public int getBoatNumber() {
		return boatNumber;
	}

	public void setBoatNumber(int boatNumber) {
		this.boatNumber = boatNumber;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
