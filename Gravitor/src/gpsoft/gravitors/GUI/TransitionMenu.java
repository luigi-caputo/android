package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import android.graphics.Point;
import android.view.MotionEvent;

public class TransitionMenu extends InGameMenu{

	CoolButton button1;
	
	public TransitionMenu(int x, int y, Screen screen) {
		super(x, y, screen, Loader.won);
		button1 = new CoolButton(this.x, this.y+logo.getHeight()+30, "Next Level", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		buttons.add(button1);
	}
	
	public void eventHandler(MotionEvent e){
		button1.eventHandler(e, new CoolAction(){
			
			public void action() {
				screen.reset();
			}
			
		});
	}

}
