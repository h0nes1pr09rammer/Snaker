package com.lzq.snaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {

	private GestureDetector mGestureDetector;
	private static Action mAction = Action.UP;
	private static boolean isRunning = true;
	private Thread mAnimatorThread;
	private Paint mSnakerPaint;
	private Snaker mSnaker;
	private int[][] mSnakerArray;
	private OnSnakerListener mOnSnakerListener;
	private Handler mHandler;

	public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	private void init(Context context){
		mSnaker = new Snaker();
		mSnaker.random();
		mSnakerArray = mSnaker.init();
		impactPostInvalidate();
		startAnimationThread();
		mSnakerPaint = new Paint();
		mSnakerPaint.setColor(Color.BLACK);
		mHandler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				if (mOnSnakerListener != null){
					mOnSnakerListener.onEnd();
				}
			}
		};
		mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onDown(MotionEvent e) {
				//返回true使onFling能收到事件
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

				float scrollX = e1.getX() - e2.getX();
				float scrollY = e1.getY() - e2.getY();
				boolean isVertical = Math.abs(velocityY) > Math.abs(velocityX);
				if (!isVertical && scrollX > 0){
					Log.i("lzq","左滑");
					if (GameView.mAction == Action.RIGHT){
					}else{
						GameView.mAction = Action.LEFT;
					}
				}
				if (!isVertical && scrollX < 0){
					Log.i("lzq","右滑");
					if (GameView.mAction == Action.LEFT){
					}else{
						GameView.mAction = Action.RIGHT;
					}
				}
				if (isVertical && scrollY < 0){
					Log.i("lzq","下滑");
					if (GameView.mAction == Action.UP){
					}else{
						GameView.mAction = Action.DOWM;
					}
				}
				if (isVertical &&scrollY > 0){
					Log.i("lzq","上滑");
					if (GameView.mAction == Action.DOWM){
					}else{
						GameView.mAction = Action.UP;
					}
				}
				return true;
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		float pX = getWidth()/30;
		float pY = getHeight()/40;
		mSnakerArray = mSnaker.init();
		for (int i = 0; i < mSnakerArray.length; i++) {
			for (int j = 0; j < mSnakerArray[i].length; j++) {
				if (mSnakerArray[i][j]==1) {
					canvas.drawRect(pX*j, pY*i, pX*j+pX,pY*i+pY, mSnakerPaint);
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	private void impactPostInvalidate(){
		mSnaker.random();
	}
	private boolean downPostInvalidate(){
		if (mSnaker.down()) {
			postInvalidate();
			return true;
		}else{
			endGame();
			return false;
		}
	}
	private boolean rightPostInvalidate(){
		if (mSnaker.right()) {
			postInvalidate();
			return true;
		}else{
			endGame();
			return false;
		}
		
	}
	private boolean upPostInvalidate(){
		if (mSnaker.up()) {
			postInvalidate();
			return true;
		}else{
			endGame();
			return false;
		}
		
	}
	private boolean leftPostInvalidate(){
		if (mSnaker.left()) {
			postInvalidate();
			return true;
		}else{
			endGame();
			return false;
		}
		
	}
	private void startAnimationThread(){
		AnimatiorRunnable mAnimatiorRunnable = new AnimatiorRunnable();
		mAnimatorThread = new Thread(mAnimatiorRunnable);
		mAnimatorThread.start();

	}
	public void startGame(){
		isRunning = true;
		mAction = Action.UP;
		startAnimationThread();
	}
	public void endGame(){
		mAction = Action.INIT;
		isRunning = false;
		mSnaker.clearView();
		postInvalidate();
		mHandler.sendEmptyMessage(0x11);
	}
	private class AnimatiorRunnable implements Runnable{


		public AnimatiorRunnable() {
		}

		@Override
		public void run() {
			Log.i("lzq","线程开始:"+mAnimatorThread.getId());
			while (isRunning){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (GameView.mAction == Action.DOWM){
					downPostInvalidate();
				}
				if (GameView.mAction == Action.LEFT){
					leftPostInvalidate();
				}
				if (GameView.mAction == Action.RIGHT){
					rightPostInvalidate();
				}
				if (GameView.mAction == Action.UP){
					upPostInvalidate();
				}
			}
			Log.i("lzq","线程结束:"+mAnimatorThread.getId());
		}
	}
	private enum Action{
		INIT,LEFT, RIGHT, UP, DOWM
	}
	public void setOnSnakerListener(OnSnakerListener onSnakerListener){
		mOnSnakerListener = onSnakerListener;
	}
	public interface OnSnakerListener{
		void onEnd();
	}
}
