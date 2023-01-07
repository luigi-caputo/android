package gpsoft.gravitors.terrain;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import gpsoft.gravitors.SoundHandler;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class Terrain {

	protected Paint p;
	ArrayList<Point> points = new ArrayList<Point>();
	Canvas canvas;
	public Bitmap terrainBitmap;
	boolean noline = false;
	
	public Terrain(){
		p = new Paint();
		terrainBitmap = 
				Bitmap.createBitmap(Screen.WIDTH, Screen.HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(terrainBitmap);
	}
	
	public void draw(Canvas drawCanvas){
		
		p.setColor(Color.BLUE);
		p.setStrokeWidth(Screen.WIDTH/65);
		canvas.drawBitmap(Loader.background, 0, 0, p);
		
		if(points.size() > 1){
			for(int i = 0; i<points.size()-1; i++){
				if(noline){
					points.clear();
					noline=false;
					continue;
				}
				Point point = points.get(i);
				Point point2 = points.get(i+1);
				canvas.drawLine(point.x, point.y, point2.x, point2.y, p);
			}
		}	
		
		drawCanvas.drawBitmap(terrainBitmap, 0, 0, p);
	}
	
	public void update(){
		if(points.size() >= Screen.HEIGHT/40){
			points.remove(0);
			points.remove(1);
		}
	}
	
	public void deleteLine(){
		noline = true;
	}
	
	public void eventHandler(MotionEvent e){
			if(e.getAction() == MotionEvent.ACTION_MOVE){
				if(points.size()==0 && e.getAction() != MotionEvent.ACTION_DOWN){
					Loader.sound.play(SoundHandler.PENCIL);
				}
				Point point = new Point((int)e.getX(), (int)e.getY());
				points.add(point);
			}
			if(e.getAction() == MotionEvent.ACTION_DOWN && points.size()!=0){
				deleteLine();
			}
	}
}
