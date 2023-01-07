package gpsoft.gravitors.entities;

import android.graphics.Bitmap;
import android.graphics.Color;
import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import gpsoft.gravitors.SoundHandler;
import gpsoft.gravitors.physics.Gravity;
import gpsoft.gravitors.util.ColorCollision;
import gpsoft.gravitors.util.Vector2D;

public class Ball extends Entity{

	public boolean inHole = false;
	
	public Ball(float x, float y) {
		super(x, y);
		sprite = Bitmap.createBitmap(Loader.ball);
		physicsHandler = new Gravity(0.5f, Screen.WIDTH, Screen.HEIGHT, 
				sprite.getWidth(),sprite.getHeight());
		physicsHandler.setCurrentX(new Vector2D(x, y));
	}
	
    public void update(){
    	super.update();
    	if(inHole)return;
    	ColorCollision.UpdateColors((int)x, (int)y, sprite, Color.BLUE);
    	
		Vector2D vec = physicsHandler.getXY();
		
			x = vec.getX();
			y = vec.getY();
			
			if(x+sprite.getWidth()+10 >= Screen.WIDTH || x <= 0 ||
					y+sprite.getHeight()+10 >= Screen.HEIGHT ||
					y <= 0){
				Loader.sound.play(SoundHandler.BALL);
			}
			
	}
    
    public boolean isStuck(){
    	if(x >= Screen.WIDTH || x <= 0 ||
				y >= Screen.HEIGHT ||
				y <= 0){
			return true;
		}
    	return false;
    }
}
