package gpsoft.gravitors.physics;

import gpsoft.gravitors.util.ColorCollision;
import gpsoft.gravitors.util.Vector2D;

public class Gravity extends Physics{
	
	private float force, diff, diffx = 6;
	private double height, width;
	
	double ballHeight, ballWidth;
	boolean boucingy = false, boucingx = false;
	
	public Gravity(float force, double width, double height, 
			double ballWidth, double ballHeight){
		this.force = force;
		this.width = width;
		this.height = height;
		this.ballHeight = ballHeight;
		this.ballWidth = ballWidth;
	}
	
	public Vector2D getXY(){
		
			if(!boucingy){
				y += diff;
				diff += force;
			}
			if(!boucingx){
				x+=diffx;
			}
			bouce();
		
		return new Vector2D(x, y);
	}
	
	public void bouce(){
		if(diff <= 0){
			boucingy = false;
		}
		if(diffx <= 0){
			boucingx = false;
		}
		if(y+ballHeight >= height || ColorCollision.colors[3] || boucingy){
			diff -= force;
			y-=diff;
			boucingy = true;
		}
		if(y<=0 || ColorCollision.colors[2]){
			y+=force;
			boucingy = false;
		}
		if(x+ballWidth>=width || ColorCollision.colors[1] || boucingx){
			x-=diffx;
			boucingx = true;
		}
		if(x<=0 || ColorCollision.colors[0]){
			x+=diffx;
			boucingx = false;
		}
		
	}
}
