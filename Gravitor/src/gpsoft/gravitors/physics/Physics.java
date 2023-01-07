package gpsoft.gravitors.physics;

import gpsoft.gravitors.util.Vector2D;

public class Physics {

	protected float x, y;
	
	public void setCurrentX(Vector2D v){
		x = v.getX();
		y = v.getY();
	}
	
	public Vector2D getXY(){
		return new Vector2D(x, y);
	}
	
}
