package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class Boat {
	
	private final double VECTOR_MAGNITUDE = 0.08;
	private final double ROWER_ANGLE = (3.0 * Math.PI) / 8.0;
	private final double MAX_ANGLE_CHANGE = (Math.PI / 90.0);
	
	private final double BOAT_WIDTH = 52.0;
	private final double BOAT_HEIGHT = 40.0;
	
	
	private int gameWidth;
	private int gameHeight;
	private int boatNumber;
	private double x = 0;
	private double y = 0;
	private Vector movementVector = new Vector();
	private double rotation = Math.PI / 2.0;
	private Rower leftRower;
	private Rower rightRower;
	private Map map;
	
	boolean hasWon = false;
	
	public Boat(int boatNumber, int x, int y, int gW, int gH, Map map) {
		this.boatNumber = boatNumber;
		this.x = x;
		this.y = y;
		gameWidth = gW;
		gameHeight = gH;
		leftRower = new Rower(boatNumber, 0);
		rightRower = new Rower(boatNumber, 1);
		this.map = map;
	}
	
	public Boat(int boatNumber, int x, int y, int gW, int gH, Map map, double rotation) {
		this.boatNumber = boatNumber;
		this.x = x;
		this.y = y;
		gameWidth = gW;
		gameHeight = gH;
		this.rotation = rotation;
		leftRower = new Rower(boatNumber, 0);
		rightRower = new Rower(boatNumber, 1);
		this.map = map;
	}
	
	public void update() {
		leftRower.update();
		rightRower.update();
		if(leftRower.getState().equals(RowerState.Rowing)) {
			movementVector.add(calculateLeftVector());
		}
		if(rightRower.getState().equals(RowerState.Rowing)) {
			movementVector.add(calculateRightVector());
		}
		
		if(!Double.isNaN(movementVector.getRotation()) && movementVector.getMagnitude() > 0.005) {
			double targetRotation = movementVector.getRotation();
			
			if(rotation > 0 && targetRotation > 0 || rotation < 0 && targetRotation < 0) {
				if(rotation - targetRotation > MAX_ANGLE_CHANGE && rotation - targetRotation > 0) {
					rotation -= MAX_ANGLE_CHANGE;
				} else if(targetRotation - rotation > MAX_ANGLE_CHANGE && targetRotation - rotation > 0) {
					rotation += MAX_ANGLE_CHANGE;
				} else {
					rotation = targetRotation;
				}
			} else{
				if(rotation > 0 && rotation < Math.PI / 2) {
					rotation -= MAX_ANGLE_CHANGE;
					if(rotation < 0) {
						if(rotation - targetRotation < 0) {
							rotation = targetRotation;
						}
					}
				} else if(rotation > 0 && rotation > Math.PI / 2) {
					rotation += MAX_ANGLE_CHANGE;
					if(rotation > Math.PI) {
						rotation = (Math.PI * -2) + rotation;
					}
					if(rotation < 0) {
						if(rotation - targetRotation > 0) {
							rotation = targetRotation;
						}
					}
					
				} else if(rotation < 0 && rotation > -Math.PI / 2) {
					rotation += MAX_ANGLE_CHANGE;
					if(rotation > 0) {
						if(rotation - targetRotation > 0) {
							rotation = targetRotation;
						}
					}
				} else if(rotation < 0 && rotation < -Math.PI / 2) {
					rotation -= MAX_ANGLE_CHANGE;
					if(rotation < -Math.PI) {
						rotation = (Math.PI * 2) + rotation;
					}
					if(rotation > 0) {
						if(rotation - targetRotation < 0) {
							rotation = targetRotation;
						}
					}
				}
			}
			
		}
		
		//movementVector.dumbDecelerate(DECELERATION_MAGNITUDE);
		double decelerationMagnitude = 0;
		double scaleValue1 = 3.0;
		double scaleValue2 = 2;
		decelerationMagnitude = (movementVector.getMagnitude() + scaleValue2) / (scaleValue1 * 60);
		movementVector.decelerate(getDecelerationVector(decelerationMagnitude));
		
		if(movementVector.getMagnitude() <= 0.04)  {
			movementVector.setX(0);
			movementVector.setY(0);
		}
		
		setX(getX() - movementVector.getX());
		setY(getY() - movementVector.getY());
		
		checkWinCondition();
	}
	
	public void draw(Graphics2D g) {
		Point midpoint = getMidPoint(x, y, BOAT_WIDTH, BOAT_HEIGHT, rotation);
		//g.rotate(rotation - Math.PI / 2.0, x, y);
		g.rotate(rotation - Math.PI / 2.0, midpoint.getX(), midpoint.getY());
		//g.setColor(Color.WHITE);
		//g.drawRect((int)x, (int)y, (int) BOAT_WIDTH, (int) BOAT_HEIGHT);
		
		//g.setColor(Color.blue);
		//g.drawRect((int)x, (int)y, 2, 2);
		//g.setColor(Color.RED);
		//g.drawRect((int)midpoint.getX(), (int)midpoint.getY(), 2, 2);
		//g.rotate(rotation, midpoint.getX(), midpoint.getY());
		leftRower.draw(g, x, y);
		rightRower.draw(g, x, y);
	}
	
	private Point getMidPoint(double x, double y, double width, double height, double angle_rad) {
		double wp = width/2.0;
		double hp = height/2.0;
		
		return new Point((int) (x + wp), (int)( y + hp));
	}
	
//	private Point getRotatedMidPoint(double x, double y, double width, double height, double angle_rad) {
////		double cosa = Math.cos(angle_rad);
////		double sina = Math.sin(angle_rad);
////		double wp = width/2.0;
////		double hp = height/2.0;
////	    return new Point((int) (x + wp * cosa - hp * sina),(int) (y + wp * sina + hp * cosa));
//		
//		double wp = width;
//		double hp = height;
//		double dist = Math.sqrt( (Math.pow(wp,2)) + (Math.pow(hp,2)) );
//	    return new Point((int) (x + Math.cos(angle_rad) * dist),(int) (y + Math.sin(angle_rad) * dist));		
//	}
	
	public void leftRow() {
		leftRower.row();
	}
	
	public void rightRow() {
		rightRower.row();
	}
	
	private Vector calculateLeftVector() {
		Vector vector = new Vector();
		double vectorAngle = rotation + ROWER_ANGLE;
		if(vectorAngle > Math.PI) vectorAngle = (Math.PI * -2.0) + vectorAngle;
		if(vectorAngle < -Math.PI) vectorAngle = (Math.PI * 2.0) + vectorAngle;
		vector.calculateVector(VECTOR_MAGNITUDE, vectorAngle);
		return vector;
	}
	
	private Vector calculateRightVector() {
		Vector vector = new Vector();
		double vectorAngle = rotation - ROWER_ANGLE;
		if(vectorAngle > Math.PI) vectorAngle = (Math.PI * -2.0) + vectorAngle;
		if(vectorAngle < -Math.PI) vectorAngle = (Math.PI * 2.0) + vectorAngle;
		vector.calculateVector(VECTOR_MAGNITUDE, vectorAngle);
		return vector;
	}
	
	private Vector getDecelerationVector(double value) {
		Vector vector = new Vector();
		vector.calculateVector(value, movementVector.getRotation());
		return vector;
	}
	
	// Mutators
	
	public int getBoatNumber() {
		return boatNumber;
	}

	public void setBoatNumber(int boatNumber) {
		this.boatNumber = boatNumber;
	}

	public Vector getMovementVector() {
		return movementVector;
	}

	public void setMovementVector(Vector movementVector) {
		this.movementVector = movementVector;
	}

	public Rower getLeftRower() {
		return leftRower;
	}

	public void setLeftRower(Rower leftRower) {
		this.leftRower = leftRower;
	}

	public Rower getRightRower() {
		return rightRower;
	}

	public void setRightRower(Rower rightRower) {
		this.rightRower = rightRower;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		if(!isXCollision((int)x)) this.x = x;
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		if(!isYCollision((int)y)) this.y = y;
	}
	
	public boolean isXCollision(int x) {
		Point midpoint = getMidPoint(x, y, BOAT_WIDTH, BOAT_HEIGHT, rotation);
		int centreX = (int) midpoint.getX();
		int centreY = (int) midpoint.getY();
		
		//Color c = new Color(map.grass.getRGB(centreX, centreY));
		if (centreX > 150 && centreX < 1050 && centreY > 0 && centreY < map.grass.getHeight()) {
			Color c = new Color(map.grass.getRGB(centreX-150, centreY));
			//System.out.println("(" + (centreX-150) + ", " + centreY +")" + c.getGreen());
			if (c.getGreen() == 128) {
				return true;
			}
		}
		
		if(midpoint.getX() < 150.0) return true;
		if(midpoint.getX() > gameWidth - 150.0) return true;
		return false;
	}
	
	public boolean isYCollision(int y) {
		Point midpoint = getMidPoint(x, y, BOAT_WIDTH, BOAT_HEIGHT, rotation);
		int centreX = (int) midpoint.getX();
		int centreY = (int) midpoint.getY();
		
		if (centreX > 150 && centreX < 1050 && centreY > 0 && centreY < map.grass.getHeight()) {
			Color c = new Color(map.grass.getRGB(centreX-150, centreY));
			if (c.getGreen() == 128) {
				return true;
			}
		}
		
		if(midpoint.getY() < 0.0) return true;
		if(midpoint.getY() > gameHeight) return true;
		return false;
	}
	
	private void checkWinCondition() {
		if(x < map.winX || y < map.winY) win();
	}
	
	private void win() {
		//yay for u
		hasWon = true;
	}
	
	public boolean hasWon() {
		return hasWon;
	}
}