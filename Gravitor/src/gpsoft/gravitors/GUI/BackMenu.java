package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import android.graphics.Point;
import android.view.MotionEvent;
import static gpsoft.gravitors.Screen.*;

public class BackMenu extends InGameMenu{

	CoolButton button1, button2;
	
	public BackMenu(int x, int y, Screen screen) {
		super(x, y, screen, Loader.intro);
		button1 = new CoolButton(this.x, this.y+logo.getHeight()+30, "Continue", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		button2 = new CoolButton(this.x, button1.y+button1.button.getHeight(), "Quit the game", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		buttons.add(button1);
		buttons.add(button2);
	}

	public void eventHandler(MotionEvent e){
		button1.eventHandler(e, new CoolAction(){
			
			public void action() {
				state = State.inGame;
			}
			
		});
		button2.eventHandler(e, new CoolAction(){

			public void action() {
				screen.reset();
				state = State.inMenu;
			}
			
		});
	}
	
}
