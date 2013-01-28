package main;

public class Animator {
	
	protected int numFrames;
	protected int currentFrame;
	
	public Animator(int numFrames) {
		this.numFrames = numFrames;
		currentFrame = 0;
	}
	
	public void animate() {
		currentFrame++;
		currentFrame = currentFrame % numFrames;
	}
	
	// Mutators
	
	public int getNumFrames() {
		return numFrames;
	}

	public void setNumFrames(int numFrames) {
		this.numFrames = numFrames;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

}
