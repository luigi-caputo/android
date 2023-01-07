package gpsoft.gravitors.entities;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.SoundHandler;
import gpsoft.gravitors.GUI.ScoreToolTip;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;

public class Platform extends Entity{

	public boolean isActive = false;
	
	public Platform(float x, float y) {
		super(x, y);
		sprite = Loader.platform;
		Loader.plates.add(this);
	}
	
	public void draw(Canvas canvas){
		super.draw(canvas);
		if(score!=null && isActive){
			score.draw(canvas);
		}
	}
	
	public void update(){
		super.update();
		if(!isActive && collide()){
			Loader.sound.play(SoundHandler.PLATFORM);
			isActive = true;
			score = new ScoreToolTip((int)ball.x, (int)ball.y, 5, Color.YELLOW);
			Loader.scoreBoard.addScore(5);
			LightingColorFilter filter = new LightingColorFilter(Color.RED, 1);
			p.setColorFilter(filter);
		}
		if(isActive){
			score.update();
		}
	}

}
