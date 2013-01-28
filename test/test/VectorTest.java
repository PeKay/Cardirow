package test;
import static org.junit.Assert.*;
import junit.framework.Assert;

import main.Vector;

import org.junit.Test;

public class VectorTest {

	@Test
	public void vectorTest() {
		
//		Vector v1 = new Vector(2, 3);
//		Vector v2 = new Vector(4, 5);
//		
//		//Add and Subtract
//		
//		Vector addResult = new Vector(6, 8);
//		Vector subtractResult = new Vector(-2, -2);
//		Vector subtractResult2 = new Vector(2, 2);
//		
//		v1.add(v2);
//		Assert.assertTrue(v1.equals(addResult));
//		
//		v1 = new Vector(2, 3);
//		v2.add(v1);
//		Assert.assertTrue(v2.equals(addResult));
//		
//		v2 = new Vector(4, 5);
//		v1.substract(v2);
//		Assert.assertEquals(v1, subtractResult);
//		
//		v1 = new Vector(2, 3);
//		v2.substract(v1);
//		Assert.assertEquals(v2, subtractResult2);
//		
//		//Rotation
//		
//		v1 = new Vector(17, 4);
//		v2 = new Vector(-32, -6);
//		
//		Vector v3 = new Vector(0, 0);
//		Vector v4 = new Vector(0, 1);
//		Vector v5 = new Vector(-1, 0);
//		Vector v6 = new Vector(-1, 1);
//		
//		Vector v7 = new Vector(1, 0);
//		Vector v8 = new Vector(0, -1);
//		
//		int rotation1 = 76;
//		int rotation2 = 190;
//		
//		Assert.assertEquals(rotation1, (int) v1.getRotation());
//		Assert.assertEquals(rotation2, (int) v2.getRotation());
//		
//		double rotation3 = (double) Double.NaN;
//		double rotation4 = 0;
//		double rotation5 = 180;
//		double rotation6 = 315;
//		
//		double rotation7 = 90;
//		double rotation8 = 180;
//		
//		Assert.assertEquals(rotation3, v3.getRotation());
//		Assert.assertEquals(rotation4, v4.getRotation());
//		Assert.assertEquals(rotation5, v5.getRotation());
//		Assert.assertEquals(rotation6, v6.getRotation());
//		
//		Assert.assertEquals(rotation7, v7.getRotation());
//		Assert.assertEquals(rotation8, v8.getRotation());
//		
//		Vector v9 = new Vector(-1, -1);
//		Vector v10 = new Vector(1, 1);
//		Vector v11 = new Vector(1, -1);
//		
//		double rotation9 = 225;
//		double rotation10 = 45;
//		double rotation11 = 135;
//		
//		Assert.assertEquals(rotation9, v9.getRotation());
//		Assert.assertEquals(rotation10, v10.getRotation());
//		Assert.assertEquals(rotation11, v11.getRotation());
	}
	
//	@Test
//	public void thetaTest() {
//		
//		Vector v1 = new Vector(0, 0);
//		Vector v2 = new Vector(0, 1);
//		Vector v3 = new Vector(1, 0);
//		Vector v4 = new Vector(1, 1);
//		
//		Vector v5 = new Vector(-1, 0);
//		Vector v6 = new Vector(0, -1);
//		Vector v7 = new Vector(1, -1);
//		Vector v8 = new Vector(-1, 1);
//		Vector v9 = new Vector(-1, -1);
//		
//		//Assert.assertEquals(v1.getRotation(), (double) Double.NaN);
//		Assert.assertEquals(v2.getRotation(), 90.0); //
//		Assert.assertEquals(v3.getRotation(), 0.0);//
//		Assert.assertEquals(v4.getRotation(), 45.0);//
//		Assert.assertEquals(v5.getRotation(), 180.0);//
//		Assert.assertEquals(v6.getRotation(), 270.0);//
//		Assert.assertEquals(v7.getRotation(), 315.0);
//		Assert.assertEquals(v8.getRotation(), 135.0);
//		Assert.assertEquals(v9.getRotation(), 225.0);
//	}
	
	@Test
	public void test() {
		Vector v = new Vector(0, 0);
		Vector v2;
		
		v.calculateVector(0, 0);
		Assert.assertTrue(new Vector(0, 0).approxEquals(v));
		
		v2 = new Vector(1, 0);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(0, 1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(-1, 0);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(-1, 1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(0, -1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(1, 1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(1, -1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		v2 = new Vector(-1, -1);
		v.calculateVector(1, v2.getRotation());
		Assert.assertTrue(v2.approxEquals(v));
		
		
		
		Vector row1 = new Vector(0, 0);
		row1.calculateVector(1, (Math.PI / 2) + (Math.PI / 4));
		
		Vector row2 = new Vector(0, 0);
		row2.calculateVector(1, (Math.PI / 2) - (Math.PI / 4));
		
		Vector overallRow = new Vector(0, 0);
		overallRow.add(row1);
		overallRow.add(row2);
		
		System.out.println(row1.toString());
		System.out.println(row2.toString());
		System.out.println(overallRow.toString());
	}

}
