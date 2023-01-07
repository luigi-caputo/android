package gpsoft.gravitors.util;

public class Vector2D {

	float x, y;
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public void normalize(){
        double magnitude = Math.sqrt(Math.pow(x, 2)*Math.pow(y, 2));
        x = (float) (x/magnitude);
        y = (float) (y/magnitude);
	}
	
}
