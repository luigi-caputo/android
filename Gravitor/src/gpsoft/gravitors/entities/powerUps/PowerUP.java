package gpsoft.gravitors.entities.powerUps;

import android.graphics.Canvas;
import android.graphics.Color;
import gpsoft.gravitors.GameTime;
import gpsoft.gravitors.Loader;
import gpsoft.gravitors.SoundHandler;
import gpsoft.gravitors.GUI.ScoreToolTip;
import gpsoft.gravitors.entities.Entity;

public class PowerUP extends Entity{

	protected boolean taken = false;
	protected int myScore = 0;
	
	
	public PowerUP(float x, float y) {
		super(x, y);
	}
	
	public void draw(Canvas canvas){
		if(score!=null && taken){
			score.draw(canvas);
			return;
		}
		super.draw(canvas);
	}
	
	public void update(){
		super.update();
		if(taken){
			score.update();
			return;
		}
		y = (float) Math.sin(GameTime.time % 1000.0/1000.0 * Math.PI * 2)+y;
		if(collide() && !taken){
			Loader.sound.play(SoundHandler.STAR);
			taken = true;
			score = new ScoreToolTip((int)ball.x, (int)ball.y, myScore, Color.MAGENTA);
			Loader.scoreBoard.addScore(myScore);
		}
	}

}
