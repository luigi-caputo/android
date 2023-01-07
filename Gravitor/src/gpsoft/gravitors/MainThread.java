package gpsoft.gravitors;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread implements Runnable{

	public static String frames = "";
	private Screen surfaceView;
	private SurfaceHolder surfaceHolder;
	private boolean running = false;
	private Thread thread;
	
	public MainThread(Screen surfaceView, SurfaceHolder surfaceHolder){
		this.surfaceView = surfaceView;
		this.surfaceHolder = surfaceHolder;
	}
	
	public void start(){
		if(running)return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		if(!running)return;
		running = false;
		try{
			thread.join();
		}catch(Exception e){
		}
	}
	
	public void performRendering(){
		Canvas canvas = null;
		try{
			canvas = surfaceHolder.lockCanvas();
			if(canvas!=null){
				synchronized(surfaceView){
					surfaceView.draw(canvas);
				}
			}
		}finally{
			if(canvas!=null){
				surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void update(){
		synchronized(surfaceView){
			if(Screen.state != Screen.State.paused){surfaceView.update();}
		}
	}
	
	public void run() {
		int fps = 0;
		int count = 0;
		long last = System.nanoTime();
		double unprocessedSeconds = 0;
		double secondsPerUpdate = 1/60.0;
		
		while(running){
			long now = System.nanoTime();
			long passed = now - last;
			last = now;
			if(passed<0)passed=0;
			if(passed>1000000000)passed = 1000000000;
			unprocessedSeconds += passed/1000000000.0;
			boolean updated = false;
			
			while(unprocessedSeconds > secondsPerUpdate){
				update();
				updated = true;
				count++;
				unprocessedSeconds -= secondsPerUpdate;
				
				if(count % 60 == 0){
					frames = Integer.toString(fps);
					fps = 0;
					last += 1000;
				}
			}
			
			if(updated){
				fps++;
				performRendering();
			}
		}
	}

	
	
}
