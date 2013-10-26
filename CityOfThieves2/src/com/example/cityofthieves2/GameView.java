package com.example.cityofthieves2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView{
	private Bitmap bmp; 
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private int x = 0;
	private int xspeed = 1;
	
	public GameView(Context context) {
		super(context);
		gameLoopThread = new GameLoopThread(this);
		holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				gameLoopThread.setRunning(false);
				while(retry)
				{
					try{
						gameLoopThread.join();
						retry=false;
					}catch(InterruptedException e){
					}
				}
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
					gameLoopThread.setRunning(true);
					gameLoopThread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				//Empty
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}
	protected void onDraw(Canvas canvas)
	{
		if(x == getWidth() - bmp.getWidth())
		{
			xspeed = -1;
		}
		if(x == 0){
			xspeed = 1;
		}
		x += xspeed;
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmp,x,10,null);
	}

}
