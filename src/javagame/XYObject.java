package javagame;

import org.newdawn.slick.geom.*;

/**
 * XYObject - Represents an object that travels in XY space
 * @author Salvatore Bellassai
 * @version 4-13-15
 */
public class XYObject {
	private float objectX, objectY;
	private int direction, xOffset, yOffset, radius;
	private double speed;
	private Circle circle;
	public XYObject(float x, float y, int _direction, double _speed, int _xOffset,
			int _yOffset, int _radius){
		objectX = x;
		objectY = y;
		direction = _direction;
		speed = _speed;
		xOffset = _xOffset;
		yOffset = _yOffset;
		radius = _radius;
		circle = new Circle(objectX + xOffset,objectY + yOffset,radius);
	}//end constructor
	/**
	 * set object x value
	 * @param x new x value for object
	 */
	public void setX(float x){
		this.objectX = x;
	}//end setX
	/**
	 * Return the x coordinate for the object
	 * @return x coordinate
	 */
	public float getX(){
		return this.objectX;
	}//end getX
	/**
	 * Set object Y value
	 * @param y new Y value for object
	 */
	public void setY(float y){
		this.objectY = y;
	}//end setY
	/**
	 * Return the Y coordinate for the object
	 * @return y coordinate
	 */
	public float getY(){
		return this.objectY;
	}//end getY
	/**
	 * Update object values
	 * @param delta
	 */
	public void update(int delta){
		this.objectX += (delta/speed)*Math.sin(Math.toRadians(direction));// delta/x represents speed
		this.objectY -= (delta/speed)*Math.cos(Math.toRadians(direction));// delta/x represents speed
		circle = new Circle(objectX + xOffset,objectY + yOffset,radius);
	}//end update
	/**
	 * Test if object intersects given circle
	 * @param targetCircle circle to test for collision
	 * @return boolean for if intersects
	 */
	public boolean intersects(Circle targetCircle){
		boolean result = false;
		if(targetCircle.intersects(circle))
			result = true;
		return result;
	}//end intersects method
	/**
	 * Test if object intersects given XYObject
	 * @param target XYObject to test for collision
	 * @return boolean for if intersects
	 */
	public boolean intersects(XYObject target){
		boolean result = false;
		if(target.getCircle().intersects(circle))
			result = true;
		return result;
	}
	/**
	 * Returns the objects circle
	 * @return objects circle
	 */
	public Circle getCircle(){
		return circle;
	}//end getCircle
	/**
	 * Return direction objectis traveling in
	 * @return direction object is traveling in represented as an int value for circle degrees
	 */
	public int getDirection(){
		return this.direction;
	}//end getDirection
}//end Bullet class
