package gpsoft.gravitors.GUI;

import android.content.Intent;
import android.graphics.Point;
import android.view.MotionEvent;
import gpsoft.gravitors.About;
import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Scores;
import gpsoft.gravitors.Screen;
import static gpsoft.gravitors.Screen.*;

public class MainMenu extends InGameMenu{

	CoolButton button1, button2, button3, button4;
	
	public MainMenu(int x, int y, Screen screen) {
		super(x, y, screen, Loader.intro);
		button1 = new CoolButton(this.x, this.y+logo.getHeight()+30, "Play !", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		button2 = new CoolButton(this.x, button1.y+button1.button.getHeight(), "High Scores", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		button3 = new CoolButton(this.x, button2.y+button2.button.getHeight(), "About & Guide", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		button4 = new CoolButton(this.x, button3.y+button3.button.getHeight(), "Exit", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
	}

	public void eventHandler(MotionEvent e){
		button1.eventHandler(e, new CoolAction(){
			
			public void action() {
				screen.reset();
				state = State.inGame;
			}
			
		});
		button2.eventHandler(e, new CoolAction(){

			public void action() {
				Intent scores = new Intent(screen.game, Scores.class);
				screen.game.startActivity(scores);
			}
			
		});
		button3.eventHandler(e, new CoolAction(){

			public void action() {
				Intent about = new Intent(screen.game, About.class);
				screen.game.startActivity(about);
			}
			
		});
		button4.eventHandler(e, new CoolAction(){

			public void action() {
				screen.game.finish();
			}
			
		});
	}
	
}
