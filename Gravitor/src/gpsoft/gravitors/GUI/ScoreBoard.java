package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class ScoreBoard extends GUI{

	private int totalScore = 0;
	private String text = "Score: "+totalScore;
	
	public ScoreBoard(int x, int y) {
		super(x, y);
		this.x += (Screen.WIDTH/2)-(Loader.scoreboard.getWidth()/2);
		paint.setTextSize(Loader.scoreboard.getHeight()/2);
		paint.setColor(Color.BLACK);
	}
	
	public void addScore(int delta){
		totalScore+=delta;
		text = "Score: "+totalScore;
	}
	
	public int getScore(){
		return totalScore;
	}
	
	public void reset(){
		totalScore=0;
		text = "Score: "+totalScore;
	}
	
	public void draw(Canvas canvas){
        if(paint.measureText(text) >= Loader.scoreboard.getWidth()){
        	Loader.scoreboard = Bitmap.createScaledBitmap(Loader.scoreboard, 
        			(int)paint.measureText(text)*2, Loader.scoreboard.getHeight(), true);
        	x = (Screen.WIDTH/2)-(Loader.scoreboard.getWidth()/2);
		}
        
		canvas.drawBitmap(Loader.scoreboard, x, y, null);
		canvas.drawText(text, 
				x+(Loader.scoreboard.getWidth()/2)-(paint.measureText(text)/2)
				, (int)(y+Loader.scoreboard.getHeight()/1.5), paint);
	}

}
