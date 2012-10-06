/**
 * 
 */
package com.inkoniq.croptool.viewobjects;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.inkoniq.croptool.R;

/**
 * @author rajeshcp
 * @since 22 Sep 2012
 */
public class CroppingTool extends View {


	private static final int CORNOR_LEFT   = 0;
	private static final int CORNOR_TOP    = 1;
	private static final int CORNOR_RIGHT  = 2;
	private static final int CORNOR_BOTTOM = 3;
	
	
	
	private TransformControl mCurrentcontrol;


	private TransformControl mTopLeft;
	private TransformControl mTopRight;
	private TransformControl mBottomLeft;
	private TransformControl mBottomright;
	private TransformControl mImagecontrol;


	protected Rect mCropRect;
	protected Rect mOverLay;

	protected Paint mPaint;

	private int mViewWidth, mViewHeight;


	private ArrayList<TransformControl> mViewCollection;


	private Bitmap mTargetImage;


	private Point mtouchDownPoint;


	/**
	 * @param of type null
	 * @return mTargetImage of type Bitmap
	 * getter function for mTargetImage
	 * @since 27 Sep 2012
	 */
	public Bitmap getmTargetImage() {
		return mTargetImage;
	}


	/**
	 * @param mTargetImage of type Bitmap 
	 * @return of type null
	 * setter function for mTargetImage
	 * @since 27 Sep 2012
	 */
	public void setmTargetImage(Bitmap mTargetImage) {
		this.mTargetImage = mTargetImage;
	}

	/**
	 * @param context
	 */
	public CroppingTool(Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CroppingTool(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CroppingTool(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 * @since 27 Sep 2012
	 */
	@SuppressLint({ "DrawAllocation", "DrawAllocation" })
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
		mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
		mOverLay = new Rect(0, 0, mViewWidth, mViewHeight);
		mCropRect = new Rect(mViewWidth / 4, mViewHeight / 4, (int)((mViewWidth / 4) + (2 * (mViewWidth / 4))), (int)((mViewHeight / 4) + (2 * (mViewHeight / 4))));
		arrangeControls();
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View#onLayout(boolean, int, int, int, int)
	 * @since 27 Sep 2012
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);

	}

	/**
	 * @param of type null
	 * @return of type null
	 * function which will arrange the childrens
	 * @since 27 Sep 2012
	 */
	private void arrangeControls()
	{
		mImagecontrol.setGraphics(mTargetImage);
		//final int dividSize = (mImagecontrol.getGraphics().getWidth() < mImagecontrol.getGraphics().getHeight()) ? mImagecontrol.getGraphics().getWidth() : mImagecontrol.getGraphics().getHeight();
		//final float scale = (mViewWidth > mViewHeight) ? (float)mViewWidth / (float)dividSize : (float)mViewHeight / (float)dividSize;
		//mImagecontrol.scale(scale, scale);
		mTopLeft.setX(mCropRect.left - (int)(mTopLeft.getGraphics().getWidth() / 2));
		mTopLeft.setY(mCropRect.top - (int)(mTopLeft.getGraphics().getHeight() / 2));



		mTopRight.setX(mCropRect.right - (int)(mTopRight.getGraphics().getWidth() / 2));
		mTopRight.setY(mCropRect.top - (int)(mTopRight.getGraphics().getHeight() / 2));
		mTopRight.setRotation(90);

		mBottomLeft.setX(mCropRect.left - (int)(mBottomLeft.getGraphics().getWidth() / 2));
		mBottomLeft.setY(mCropRect.bottom - (int)(mBottomLeft.getGraphics().getHeight() / 2));
		mBottomLeft.setRotation(270);

		mBottomright.setX(mCropRect.right - (int)(mBottomright.getGraphics().getWidth() / 2));
		mBottomright.setY(mCropRect.bottom - (int)(mBottomright.getGraphics().getHeight() / 2));
		mBottomright.setRotation(180);
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 * @since 27 Sep 2012
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(mImagecontrol.getGraphics(), mImagecontrol.getTransform(), mPaint);
		drawLayer(canvas);
		for(final TransformControl control : mViewCollection)
		{
			canvas.drawBitmap(control.getGraphics(), control.getTransform(), mPaint);
		}
	}



	/**
	 * @param canvas of type Canvas
	 * @return of type null
	 * function which will draw transparent layer over canvas
	 * @since 27 Sep 2012
	 */
	private void drawLayer(final Canvas canvas)
	{
		mPaint.setAlpha(200);
		canvas.drawRect(mOverLay, mPaint);
		mPaint.setAlpha(255);
		Bitmap cropedBitmap = Bitmap.createBitmap(mTargetImage, mCropRect.left, mCropRect.top, mCropRect.width(), mCropRect.height());
		final Matrix mat = new Matrix();
		mat.setTranslate(mCropRect.left, mCropRect.top);
		canvas.drawBitmap(cropedBitmap, mat, mPaint);
	}


	/**
	 * @param of type null
	 * @return of type null
	 * function which will initialize all the items required for the view 
	 * @since 27 Sep 2012
	 */
	private void init()
	{
		mViewCollection = new ArrayList<TransformControl>();
		mPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint.ANTI_ALIAS_FLAG);
		Bitmap graphics = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.crop_tool_anchor);
		mPaint.setColor(Color.BLACK);
		mPaint.setAntiAlias(true);
		mImagecontrol   = createTransformControl(mTargetImage, false);
		mTopLeft        = createTransformControl(graphics, true);
		mBottomLeft     = createTransformControl(graphics, true);
		mTopRight       = createTransformControl(graphics, true);
		mBottomright    = createTransformControl(graphics, true);
	}

	/**
	 * @param graphics of type Bitmap
	 * @param control of type TransformControl
	 * @return of type null
	 * function which will create the transform control Object 
	 * @since 27 Sep 2012
	 */
	private TransformControl createTransformControl(final Bitmap grahics, final boolean addTocollection)
	{
		final TransformControl control = new TransformControl(grahics, getContext());
		if(addTocollection)
		{
			mViewCollection.add(control);
		}
		return control;
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * @since 28 Sep 2012
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		{
			mtouchDownPoint = new Point((int)event.getX(), (int)event.getY());
			final Rect hitRect = new Rect(mtouchDownPoint.x, mtouchDownPoint.y, mtouchDownPoint.x + 1, mtouchDownPoint.y + 1);
			for(final TransformControl control : mViewCollection)
			{
				if(Rect.intersects(new Rect(control.getX(), control.getY(), control.getX() + control.getGraphics().getWidth(), control.getY() + control.getGraphics().getHeight()), hitRect))
				{
					mCurrentcontrol = control;
					break;
				}else
				{
					mCurrentcontrol = null;
				}
			}

			if(Rect.intersects(mCropRect, hitRect))
			{
				mCurrentcontrol = mImagecontrol;
			}

			break;
		}

		case MotionEvent.ACTION_MOVE:
		{
			if(mCurrentcontrol != null)
			{
				final int xDiff = (int) event.getX() - mtouchDownPoint.x;
				final int yDiff = (int) event.getY() - mtouchDownPoint.y;
				if(mCurrentcontrol != mImagecontrol)
				{
					final int factor = (Math.abs(xDiff) > Math.abs(yDiff)) ? xDiff : yDiff;
					updateCropRectBounds((factor == yDiff && ((mCurrentcontrol == mBottomLeft) || (mCurrentcontrol == mTopRight))) ? factor * -1 : factor, (factor == xDiff && ((mCurrentcontrol == mBottomLeft) || (mCurrentcontrol == mTopRight))) ? factor * -1 : factor);
				}else
				{
					moveCropRect(xDiff, yDiff);
				}
				mtouchDownPoint.x = (int)event.getX();
				mtouchDownPoint.y = (int)event.getY();
			}
			break;
		}

		case MotionEvent.ACTION_UP:
		{
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		{
			break;
		}
		}
		arrangeControls();
		invalidate();
		return true;
	}


	/**
	 * @param xDiff of type int
	 * @param yDiff of type int 
	 * @return valid of type boolean
	 * function which will check the bounds of the cropp rect 
	 * @since 30 Sep 2012
	 */
	private void updateCropRectBounds(final int xDiff, final int yDiff)
	{
		if(mCurrentcontrol == mTopLeft && checkBounds(xDiff, CORNOR_LEFT) && checkBounds(yDiff, CORNOR_TOP))
		{
			mCropRect.left = Math.max(0, (mCropRect.left + xDiff));
			mCropRect.top = Math.max(0, mCropRect.top + yDiff);
		}else if(mCurrentcontrol == mTopRight && checkBounds(xDiff, CORNOR_RIGHT) && checkBounds(yDiff, CORNOR_TOP))
		{
			mCropRect.top = Math.max(0, mCropRect.top + yDiff);
			mCropRect.right = Math.min(mViewWidth, mCropRect.right + xDiff);
		}else if(mCurrentcontrol == mBottomLeft && checkBounds(xDiff, CORNOR_LEFT) && checkBounds(yDiff, CORNOR_BOTTOM))
		{
			mCropRect.bottom = Math.min(mViewHeight, mCropRect.bottom + yDiff);
			mCropRect.left = Math.max(0, (mCropRect.left + xDiff));
		}else if(mCurrentcontrol == mBottomright && checkBounds(yDiff, CORNOR_BOTTOM) && checkBounds(xDiff, CORNOR_RIGHT))
		{
			mCropRect.bottom = Math.min(mViewHeight, mCropRect.bottom + yDiff);
			mCropRect.right = Math.min(mViewWidth, mCropRect.right + xDiff);
		}
	}
	
	
	/**
	 * @param xDiff of type int 
	 * @param yDiff of type int 
	 * @return of type null
	 * function which will move the cropRect 
	 * @since 1 Oct 2012
	 */
	private void moveCropRect(final int xDiff, final int yDiff)
	{
		if(checkBounds(xDiff, CORNOR_LEFT) && checkBounds(yDiff, CORNOR_TOP) && checkBounds(yDiff, CORNOR_BOTTOM) && checkBounds(xDiff, CORNOR_RIGHT))
		{
			mCropRect.left = Math.max(0, (mCropRect.left + xDiff));
			mCropRect.top = Math.max(0, mCropRect.top + yDiff);
			mCropRect.right = Math.min(mViewWidth, mCropRect.right + xDiff);
			mCropRect.bottom = Math.min(mViewHeight, mCropRect.bottom + yDiff);
		}
	}
	

	/**
	 * @param factor of type int
	 * @param type of type int 
	 * @return valid of type boolean
	 * function which will validate the crp rect
	 * @since 1 Oct 2012
	 */
	private boolean checkBounds(final int factor, final int type)
	{
		boolean isValid = false;
		switch(type)
		{
			case CORNOR_LEFT :
			{
				isValid = ((mCropRect.left + factor) > 0) ? true : false;
				break;
			}
			case CORNOR_TOP :
			{
				isValid = ((mCropRect.top + factor) > 0) ? true : false;
				break;
			}
			case CORNOR_RIGHT :
			{
				isValid = ((mCropRect.right + factor) < mViewWidth) ? true : false;
				break;
			}
			case CORNOR_BOTTOM :
			{
				isValid = ((mCropRect.bottom + factor) < mViewHeight) ? true : false;
				break;
			}
		}
		return isValid;
	}


}
