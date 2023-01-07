package gpsoft.gravitors.GUI;

import android.graphics.Canvas;

public class ScoreToolTip extends GUI{

	int alpha = 255;
	String text;
	
	public ScoreToolTip(int x, int y, int score, int color) {
		super(x, y);
		paint.setTextSize(50);
		paint.setColor(color);
		this.text = "+" + Integer.toString(score);
	}

	public void draw(Canvas canvas){
		if(alpha <= 0) return;
		paint.setAlpha(alpha);
		canvas.drawText(text, x, y, paint);
	}
	
	public void update(){
		if(alpha <= 0) return;
		alpha-=10;
		y--;
	}
	
}
