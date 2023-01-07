package gpsoft.gravitors;

import gpsoft.gravitors.GUI.BackMenu;
import gpsoft.gravitors.GUI.GameOverMenu;
import gpsoft.gravitors.GUI.MainMenu;
import gpsoft.gravitors.GUI.TransitionMenu;
import gpsoft.gravitors.entities.Ball;
import gpsoft.gravitors.entities.Entity;
import gpsoft.gravitors.entities.Platform;
import gpsoft.gravitors.terrain.Spawner;
import gpsoft.gravitors.terrain.Terrain;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Screen extends SurfaceView implements SurfaceHolder, SurfaceHolder.Callback{
	
	public Activity game;
	
	public static enum State{
		GameOver, Transition, inMenu, inGame, paused;
	}
	public static State state = State.inMenu;
	
	boolean[] active;
    public static int WIDTH;
	public static int HEIGHT;
	public MainThread thread;
	public Terrain terrain;
	public Ball ball;
	public Spawner s;
	Paint p = new Paint();
	private GameOverMenu menu;
	private BackMenu back;
	private TransitionMenu trans;
	private MainMenu main;
	public SoundHandler sound;
	int timer = 20;
	boolean showFps = false;
	
	public Screen(Context context) {
		super(context);
		game = (Activity)context;
		getHolder().addCallback(this);
		setFocusable(true);
		sound = new SoundHandler(context);
		thread = new MainThread(this, getHolder());
		terrain = Loader.terrain;
		
		ball = new Ball(0,0);
		s = new Spawner(getResources());
		menu = new GameOverMenu(0,0,this);
		back = new BackMenu(0,0,this);
	    main = new MainMenu(0,-HEIGHT/4,this);
		s.refreshEntityList();
		active = new boolean[Loader.plates.size()];
		trans = new TransitionMenu(0,0,this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.stop();
	}

	public void addCallback(Callback callback) {
	}
	
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		invalidate();
	}
	
	public void reset(){
		if(state == State.GameOver || state == State.paused){
			Loader.scoreBoard.reset();
		}
		terrain.deleteLine();
		ball = new Ball(0,0);
	    s.refreshEntityList();
	    active = new boolean[Loader.plates.size()];
	    state = State.inGame;
	}
	
	private boolean allActive(boolean[] array){
		for(boolean b : array){
			if(!b)return false;
		}
		return true;
	}
	
	public void draw(Canvas canvas){
		p.setTextSize(50);
		p.setColor(Color.RED);
		
		terrain.draw(canvas);
		
		if(state != State.inMenu){
			for(int i = 0;i<s.list.size(); i++){
				if(i >= s.list.size())continue;
				s.list.get(i).draw(canvas);
			}
		}
		
		ball.draw(canvas);
		
		if(showFps){canvas.drawText(MainThread.frames+" fps", 50, 50, p);}
		
		if(state != State.inMenu){
			Loader.scoreBoard.draw(canvas);
			
			if(state == State.paused){
				back.draw(canvas);
			}
			
			if(state == State.GameOver){
				menu.draw(canvas);
			}
			
			if(state == State.Transition){
				trans.draw(canvas);
			}
		}else{
			main.draw(canvas);
		}
	}
	
	
	public void update(){
		GameTime.tick();
		terrain.update();
		ball.update();
		
		if(state != State.inMenu){
			for(int i = 0;i<s.list.size(); i++){
				if(i >= s.list.size())continue;
				Entity e = s.list.get(i);
				e.update();
			}
			
			if(ball.inHole){
				timer--;
				if(timer == 0){
					timer = 20;
					for(int i = 0;i<Loader.plates.size(); i++){
						Platform plat = Loader.plates.get(i);
						if(plat.isActive){
							active[i] = true;
						}
					}
					
					if(!allActive(active)){state = State.GameOver;}
					
					if(state != State.GameOver){state = State.Transition;}
				}
			}else{
				if(ball.isStuck())ball = new Ball(0,0);
			}
		}
	}
	
	public boolean onTouchEvent(MotionEvent e){
		super.onTouchEvent(e);
		
		if(state == State.paused){
			back.eventHandler(e);
		}
		
		if(state == State.GameOver){
			menu.eventHandler(e);
		}
		
		if(state == State.Transition){
			trans.eventHandler(e);
		}
		
		if(state == State.inGame){
			terrain.eventHandler(e);
		}
		
		if(state == State.inMenu){
			main.eventHandler(e);
		}
		
		return true;
	}
	
	public Surface getSurface() {
		return null;
	}

	public Rect getSurfaceFrame() {
		return null;
	}

	public boolean isCreating() {
		return false;
	}

	public Canvas lockCanvas() {
		return null;
	}

	public Canvas lockCanvas(Rect dirty) {
		return null;
	}

	public void removeCallback(Callback callback) {
	}

	public void setFixedSize(int width, int height) {
	}

	public void setFormat(int format) {
	}

	public void setSizeFromLayout() {
	}

	public void setType(int type) {
	}

	public void unlockCanvasAndPost(Canvas canvas) {
	}

}
