package gpsoft.gravitors.util;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.SoundHandler;
import android.graphics.Bitmap;

public class ColorCollision {

	public static boolean[] colors = new boolean[4];
	private static Bitmap terrain;
	
	public static void UpdateColors(int x, int y, Bitmap b, int colorMask){
		
		terrain = Loader.terrain.terrainBitmap;
		
			 if(x>0 && x+b.getWidth()<terrain.getWidth() && y>0 && y+b.getHeight()<terrain.getHeight()){

						int correctionx = b.getWidth()/4;
						int correctiony = b.getHeight()/4;
						
						//Left Collision
						if(terrain.getPixel(x, y+(b.getHeight()/2)) == colorMask ||
								terrain.getPixel(x+correctionx, y+correctiony) == colorMask ||
								terrain.getPixel(x+correctionx, y+b.getHeight()-correctiony) == colorMask){
							Loader.sound.play(SoundHandler.BALL);
							colors[0] = true;
						}else{
							colors[0] = false;
						}
						
						//Right Collision
						if(terrain.getPixel(x+b.getWidth(), y+(b.getHeight()/2)) == colorMask ||
								terrain.getPixel(x+b.getWidth()-correctionx, y+correctiony) == colorMask ||
								terrain.getPixel(x+b.getWidth()-correctionx, y+b.getHeight()-correctiony) == colorMask){
							Loader.sound.play(SoundHandler.BALL);
							colors[1] = true;
						}else{
							colors[1] = false;
						}
						
		
						//Top Collision
						if(terrain.getPixel(x+(b.getWidth()/2), y) == colorMask ||
								terrain.getPixel(x+correctionx, y+correctiony) == colorMask ||
								terrain.getPixel(x+b.getWidth()-correctionx, y+correctiony) == colorMask){
							Loader.sound.play(SoundHandler.BALL);
							colors[2] = true;
						}else{
							colors[2] = false;
						}
						
						//Bottom Collision
						if(terrain.getPixel(x+(b.getWidth()/2), y+b.getHeight()) == colorMask ||
								terrain.getPixel(x+correctionx, y+b.getHeight()-correctiony) == colorMask ||
								terrain.getPixel(x+b.getWidth()-correctionx, y+b.getHeight()-correctiony) == colorMask){
							Loader.sound.play(SoundHandler.BALL);
							colors[3] = true;
						}else{
							colors[3] = false;
						}				
					
			}
		
	}
	
}
