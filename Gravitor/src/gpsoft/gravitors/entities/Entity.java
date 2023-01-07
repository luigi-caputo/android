package gpsoft.gravitors.entities;

import gpsoft.gravitors.GUI.ScoreToolTip;
import gpsoft.gravitors.physics.Gravity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Entity {

	public float x, y;
	public Bitmap sprite;
	protected Gravity physicsHandler;
	protected static Ball ball;
	public RectF rect;
	protected Paint p;
	protected ScoreToolTip score;
	
	public Entity(float x, float y){
		this.x = x;
		this.y = y;
		rect = new RectF();
		p = new Paint();
		
		if(this instanceof Ball){
			ball = (Ball) this;
		}
	}
	
	public void draw(Canvas canvas){
		canvas.drawBitmap(sprite, x, y, p);
	}
	
	protected void defineRect(){
		rect.set(x, y, x+sprite.getWidth(), y+sprite.getHeight());
	}
	
    public void update(){
    	defineRect();
	}
    
    protected boolean collide(){
    	if(rect.intersect(ball.rect)){
    		return true;
    	}
    	return false;
    }

}
