package gpsoft.gravitors.GUI;

import java.util.ArrayList;

import gpsoft.gravitors.Screen;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class InGameMenu extends GUI{

	protected Bitmap logo;
	protected ArrayList<CoolButton> buttons;
	protected Screen screen;
	
	public InGameMenu(int x, int y, Screen screen, Bitmap logo) {
		super(x, y);
		buttons = new ArrayList<CoolButton>();
		this.logo = logo;
		this.x+=(Screen.WIDTH/2)-(logo.getWidth()/2);
		this.y+=(Screen.HEIGHT/2)-(logo.getHeight());
		this.screen = screen;
	}
	
	public void draw(Canvas canvas){
		for(int i = 0; i<buttons.size(); i++){
			buttons.get(i).draw(canvas);
		}
		canvas.drawBitmap(logo, x, y, paint);
	}
	
}
