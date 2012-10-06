/**
 * 
 */
package com.inkoniq.croptool.viewobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @author Rajesh
 * @since 22 Sep 2012
 */
public class TransformControl {

	/***************holds the bitmap instance*************/
	protected Bitmap graphics;

	/**********Holds the x position of the object*********/
	protected int x = 0;

	/**********Holds the x position of the object*********/
	protected int y = 0;

	/**********Holds the rotation of the Object***********/
	protected float rotation = 0;

	/******Decide the transformation of the Object*******/
	protected Matrix transform;

	protected Context context;
	
	
	protected float mScaleX = 1;
	protected float mScaleY = 1;

	/**
	 * @param of type null
	 * @return graphics of type Bitmap
	 * getter function for graphics
	 * @since 22 Sep 2012
	 */
	public Bitmap getGraphics() {
		return graphics;
	}


	/**
	 * @param of type null
	 * @return y of type int
	 * getter function for y
	 * @since 22 Sep 2012
	 */
	public int getY() {
		return y;
	}


	/**
	 * @param y of type int
	 * @return of type null
	 * setter function for y
	 * @since 22 Sep 2012
	 */
	public void setY(int y) {
		this.y = y;
		updateTranform();
	}

	/**
	 * @param graphics of type Bitmap
	 * @return of type null
	 * setter function for graphics
	 * @since 22 Sep 2012
	 */
	public void setGraphics(Bitmap graphics) {
		this.graphics = graphics;
	}


	/**
	 * @param of type null
	 * @return x of type int
	 * getter function for x
	 * @since 22 Sep 2012
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x of type int
	 * @return of type null
	 * setter function for x
	 * @since 22 Sep 2012
	 */
	public void setX(int x) {
		this.x = x;
		updateTranform();
	}


	/**
	 * @param of type null
	 * @return rotation of type float
	 * getter function for rotation
	 * @since 22 Sep 2012
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * @param rotation of type float
	 * @return of type null
	 * setter function for rotation
	 * @since 22 Sep 2012
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
		updateTranform();
	}

	/**
	 * @param of type null
	 * @return transform of type Matrix
	 * getter function for transform
	 * @since 22 Sep 2012
	 */
	public Matrix getTransform() {
		return transform;
	}

	/**
	 * Constructor function 
	 * @param graphics of type Bitmap
	 * @since 22 Sep 2012
	 */
	public TransformControl(Bitmap graphics, Context context) {
		this.graphics = graphics;
		this.context = context;
		transform = new Matrix();
	}

	/**
	 * @param of type null
	 * @return of type null
	 * function which will update the transformation matrix
	 * @since 22 Sep 2012
	 */
	protected void updateTranform()
	{
		transform.reset();
		transform.setTranslate(x, y);
		transform.postRotate(rotation, (x + (graphics.getWidth() / 2)), (y + (graphics.getHeight() / 2)));
		//transform.setScale(mScaleX, mScaleY);
	}

	/**
	 * @param sx of type float 
	 * @param sy of type float 
	 * function which will set the scale for the view object 
	 * @since 27 Sep 2012
	 */
	public void scale(final float sx, final float sy)
	{
		mScaleX = sx;
		mScaleY = sy;
		updateTranform();
	}


	/**
	 * @param of type null
	 * @return of type null
	 * function which will reset the object 
	 * @since 15 may 2012
	 */
	public void destroy()
	{
		transform = null;
		graphics = null;
		x = y = 0;
	}

}
