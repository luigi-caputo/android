package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import gpsoft.gravitors.SoundHandler;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class CoolButton extends GUI{

	public Bitmap button;
	private String text;
	boolean isPressed = false;
	private Paint light = new Paint();
	LightingColorFilter filter = new LightingColorFilter(Color.RED, 1);
	
	public CoolButton(int x, int y, String text, Point size, boolean center) {
		super(x, y);
		this.text = text;
		button = Bitmap.createBitmap(Loader.button);
		paint.setTextSize(button.getHeight()/3);
		if(paint.measureText(text) > size.x){
			button = Bitmap.createScaledBitmap(button, (int)(size.x+paint.measureText(text)), size.y, true);
		}else{
			button = Bitmap.createScaledBitmap(button, size.x, size.y, true);
		}
		if(!center)return;
		this.x=(Screen.WIDTH/2)-(button.getWidth()/2);
	}
	
	public void draw(Canvas canvas){
		if(!isPressed){
			canvas.drawBitmap(button, x, y, paint);
		}else{
			light.setColorFilter(filter);
			canvas.drawBitmap(button, x, y, light);
		}
		canvas.drawText(text, (x+button.getWidth()/2)-(paint.measureText(text)/2), (int)(y+button.getHeight()/1.5), paint);
	}
	
	public void eventHandler(MotionEvent e, CoolAction a){
		float xp = e.getX();
		float yp = e.getY();
		
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			if(xp > x && xp < x+button.getWidth() && yp > y && yp < y+button.getHeight()){
				isPressed = true;
			}
		}
		if(e.getAction() == MotionEvent.ACTION_UP){
			if(isPressed && xp > x && xp < x+button.getWidth() && yp > y && yp < y+button.getHeight()){
				Loader.sound.play(SoundHandler.CLICK);
				a.action();
			}
			isPressed = false;
		}
	}

}
