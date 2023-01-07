package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import gpsoft.gravitors.util.Configuration;
import static gpsoft.gravitors.Screen.*;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;

public class GameOverMenu extends InGameMenu{
	
	CoolButton button1, button2;
	int score;
	
	public GameOverMenu(int x, int y, Screen screen) {
		super(x, y, screen, Loader.GameOver);
		button1 = new CoolButton(this.x, this.y+logo.getHeight()+30, "Regenerate level", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		button2 = new CoolButton(this.x, button1.y+button1.button.getHeight(), "Save score & exit", 
				new Point(Screen.WIDTH/4, Screen.HEIGHT/6), true);
		buttons.add(button1);
		buttons.add(button2);
	}
	
	public void eventHandler(MotionEvent e){
		button1.eventHandler(e, new CoolAction(){
			
			public void action() {
				screen.reset();
			}
			
		});
		button2.eventHandler(e, new CoolAction(){

			public void action() {
				score = Loader.scoreBoard.getScore();
				dialog();
				screen.reset();
				state = State.inMenu;
			}
			
		});
	}
	
	private void dialog(){
		AlertDialog.Builder alert = new AlertDialog.Builder(screen.game);
		alert.setTitle("Type your name..");
		
		final EditText text = new EditText(screen.game);
		text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		alert.setView(text);
		
		alert.setPositiveButton("Save", new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {
				if(!Configuration.saveConfig(text.getText().toString(), score)){
					Configuration.saveConfig(text.getText().toString(), score);
				}
			}
			
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		
		alert.show();
	}

}
