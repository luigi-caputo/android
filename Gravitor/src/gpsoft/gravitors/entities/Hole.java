package gpsoft.gravitors.entities;

import android.graphics.Bitmap;
import gpsoft.gravitors.Loader;
import gpsoft.gravitors.SoundHandler;

public class Hole extends Entity{

	public Hole(float x, float y) {
		super(x, y);
		sprite = Loader.hole;
	}
	
	public void update(){
		super.update();
		int finalSize = sprite.getWidth()/6;
		
		if(collide() && ball.sprite.getWidth() != finalSize){
			ball.inHole = true;
			//ball.sprite = Bitmap.createScaledBitmap(ball.sprite,
			//ball.sprite.getWidth()-finalSize, ball.sprite.getHeight()-finalSize, true);
			
			ball.sprite = Bitmap.createScaledBitmap(ball.sprite,finalSize,finalSize, true);
			
			ball.x = (x+(sprite.getWidth()/2))-ball.sprite.getWidth()/2;
			ball.y = (y+(sprite.getHeight()/2))-ball.sprite.getHeight()/2;
			
			//if(ball.sprite.getWidth() < finalSize){
				Loader.sound.play(SoundHandler.HOLE);
			//}
		}
	}

}
