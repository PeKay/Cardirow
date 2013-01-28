package main;

import java.lang.Math;

public class Vector 
{
	
	// Variables
	
	private double x = 0.0;
	private double y = 0.0;
	
	// Constructor
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		
	}
	
	// Calculations
	
	public void add(Vector v2) 	{
		double horizontalProduct = this.getX() + v2.getX();
		double verticalProduct = this.getY() + v2.getY();
				
		this.setX(horizontalProduct);
		this.setY(verticalProduct);
	}
	
	public void substract(Vector v2) 	{
		double horizontalProduct = this.getX() - v2.getX();
		double verticalProduct = this.getY() - v2.getY();
		
		this.setX(horizontalProduct);
		this.setY(verticalProduct);
	}
	
	public void decelerate(Vector v2) {
				
		if(x == 0) {
			//do nothing
		} else if(x > 0) {
			if (x - v2.getX() > 0) {
				x = x - v2.getX();
			} else {
				x = 0;
			}
		} else {
			if (x - v2.getX() < 0) {
				x = x - v2.getX();
			} else {
				x = 0;
			}
		}
		
		if (y == 0) {
			//do nothing
		} else if(y > 0) {
			if (y - v2.getY() > 0) {
				y = y - v2.getY();
			} else {
				y = 0;
			}
		} else {
			if (y - v2.getY() < 0) {
				y = y - v2.getY();
			} else {
				y = 0;
			}
		}
	}
	
	public void dumbDecelerate(double amount) {	
		if(x == 0.0) {
			//do nothing
		} else if(x > 0.0) {
			if (x - amount > 0.0) {
				x = x - amount;
			} else {
				x = 0.0;
			}
		} else {
			if (x + amount < 0.0) {
				x = x + amount;
			} else {
				x = 0.0;
			}
		}
		
		if (y == 0.0) {
			//do nothing
		} else if(y > 0.0) {
			if (y - amount > 0.0) {
				y = y - amount;
			} else {
				y = 0.0;
			}
		} else {
			if (y + amount < 0.0) {
				y = y + amount;
			} else {
				y = 0.0;
			}
		}
	}
	
	private double getTheta()	{
		return Math.atan2(getY(), getX());
	}
	
	public double getMagnitude() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double getRotation()	{
		if(x == 0.0 && y == 0.0) {
			return (double) Double.NaN;
		}
		
		double result = getTheta();
				
		return result;

	}
	
	@Override
	public boolean equals(Object v2) {
		if(!v2.getClass().equals(Vector.class)) return false;
		
		return (this.getX() == ((Vector) v2).getX() &&
				this.getY() == ((Vector) v2).getY());
	}
	
	public boolean approxEquals(Object v2) {
		if(!v2.getClass().equals(Vector.class)) return false;
		
		return (Math.round(this.getX()) == Math.round(((Vector) v2).getX()) &&
				Math.round(this.getY()) == Math.round(((Vector) v2).getY()));
	}
	
	public void calculateVector(double magnitude, double angle) {
		x = round(magnitude * Math.cos(angle), 3);
		y = round(magnitude * Math.sin(angle), 3);
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	// Mutator methods
	
		public double getX()	{
			return x;
		}
		
		public void setX(double x) {
			this.x = x;
		}
		
		public double getY() {
			return y;
		}
		
		public void setY(double y) {
			this.y = y;
		}
}
