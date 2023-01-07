package gpsoft.pocketfileserver;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;

public class MouseMover implements Runnable{

	private Thread thread;
	private boolean running = false;
	boolean moved = false;
	private Robot robot;
	int oldx, oldy;
	
	public MouseMover(){
	try {
		robot = new Robot();
	} catch (AWTException e) {}
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
		thread.interrupt();
		try{
			thread.join();
		}catch(Exception e){
		}
	}
	
	public void run() {
		synchronized(thread){
			while(running){
				try {
					Thread.sleep(10000);
					int x = MouseInfo.getPointerInfo().getLocation().x;
					int y = MouseInfo.getPointerInfo().getLocation().y;
					
					if(oldy == y && oldx == x){
						if(!moved){
							robot.mouseMove(x+1, y+1);
							moved = true;
						}else{
							robot.mouseMove(x-1, y-1);
							moved = false;
						}
					}
					
					oldx = x;
					oldy = y;
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
}
