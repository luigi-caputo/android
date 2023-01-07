package gpsoft.gravitors;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import static gpsoft.gravitors.Screen.*;

public class Gravitor extends Activity {

	/*
	* Copyright (c) 2013, GicoPiro (GPSoft)
	* All rights reserved.
	*/
	
	private static Screen screen;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		if(state == State.inMenu){
			int width = getWindowManager().getDefaultDisplay().getWidth();
			int height = getWindowManager().getDefaultDisplay().getHeight();
			
			Screen.WIDTH = width;
			Screen.HEIGHT = height;
			
			Loader.load(getResources(), getAssets(), this);
			screen = new Screen(this);
		}else{
			if(screen != null){
				((ViewGroup)screen.getParent()).removeView(screen);
			}else{
				finish();
				System.exit(1);
			}
		}
		
		setContentView(screen);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.gravitor, menu);
		return true;
	}
	
	public void onDestroy(){
		if(state != State.paused && state != State.GameOver && state != State.Transition){
			state = State.inMenu;
		}
		super.onDestroy();
	}
	
	public void onStop(){
		super.onStop();
	}

	public void onStart(){
		super.onStart();
	}

	public void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	public void onPause(){
		if(state == State.inGame){
			state = State.paused;
		}
		super.onPause();
	}
	
	public void onBackPressed(){
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Loader.sound.onKeyDown(keyCode, event);
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(state == State.inGame){
				state = State.paused;
			}
		}
	    return false;
	}
	
}
