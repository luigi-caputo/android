package gpsoft.gravitors.terrain;

import gpsoft.gravitors.Loader;
import gpsoft.gravitors.Screen;
import gpsoft.gravitors.entities.Entity;
import gpsoft.gravitors.entities.Hole;
import gpsoft.gravitors.entities.Platform;
import gpsoft.gravitors.entities.powerUps.Star;
import gpsoft.gravitors.util.RandomUtil;

import java.util.ArrayList;
import android.content.res.Configuration;
import android.content.res.Resources;

public class Spawner {

	public ArrayList<Entity> list;
	private Resources res;
	 int coorPlatformX,
	  coorPlatformY,
	  coorHoleY,
	  coorHoleX,
	  coorStarX,
	  coorStarY, xoffsetStart, xoffsetStop;
	
	public Spawner(Resources res){
		this.res = res;
	}
	
	private void refreshCoors(int num){
		 xoffsetStop += Screen.WIDTH/num;
		 coorPlatformX = RandomUtil.getCloseInterval(xoffsetStart, xoffsetStop-Loader.platform.getWidth());
		 coorPlatformY = RandomUtil.getCloseInterval(0, Screen.HEIGHT-Loader.platform.getHeight());
		
		 coorHoleX = RandomUtil.getCloseInterval((int)(Screen.WIDTH/1.4), Screen.WIDTH-Loader.hole.getWidth());
		 coorHoleY = RandomUtil.getCloseInterval((int)(Screen.HEIGHT/1.6), Screen.HEIGHT-Loader.hole.getHeight());
		
		 coorStarX = RandomUtil.getCloseInterval(0, Screen.WIDTH-Loader.star.getWidth());
		 coorStarY = RandomUtil.getCloseInterval(0, Screen.HEIGHT-Loader.star.getHeight());
		 xoffsetStart += Screen.WIDTH/num;
	}
	
	public void refreshEntityList(){
		Loader.plates = new ArrayList<Platform>();
		xoffsetStart = 0;
		xoffsetStop = 0;
		
		list = new ArrayList<Entity>();
		
		if((res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==
				Configuration.SCREENLAYOUT_SIZE_SMALL){
			for(int i = 0; i<2; i++){
				refreshCoors(2);
				list.add(new Platform(coorPlatformX, coorPlatformY));
				list.add(new Star(coorStarX, coorStarY));
			}
			list.add(new Hole(coorHoleX, coorHoleY));
			return;
		}
		
		if((res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==
				Configuration.SCREENLAYOUT_SIZE_NORMAL){
			for(int i = 0; i<4; i++){
				refreshCoors(4);
				list.add(new Platform(coorPlatformX, coorPlatformY));
				list.add(new Star(coorStarX, coorStarY));
			}
			list.add(new Hole(coorHoleX, coorHoleY));
			return;
		}
		
		if((res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==
				Configuration.SCREENLAYOUT_SIZE_LARGE){
			for(int i = 0; i<5; i++){
				refreshCoors(5);
				list.add(new Platform(coorPlatformX, coorPlatformY));
				list.add(new Star(coorStarX, coorStarY));
			}
			list.add(new Hole(coorHoleX, coorHoleY));
			return;
		}
		
		if((res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==
				Configuration.SCREENLAYOUT_SIZE_XLARGE){
			for(int i = 0; i<6; i++){
				refreshCoors(6);
				list.add(new Platform(coorPlatformX, coorPlatformY));
				list.add(new Star(coorStarX, coorStarY));
			}
			for(int i = 0; i<2; i++){
				refreshCoors(2);
				list.add(new Hole(coorHoleX, coorHoleY));
			}
			return;
		}
		
		if((res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)==
				Configuration.SCREENLAYOUT_SIZE_UNDEFINED){
			for(int i = 0; i<4; i++){
				refreshCoors(4);
				list.add(new Platform(coorPlatformX, coorPlatformY));
				list.add(new Star(coorStarX, coorStarY));
			}
			list.add(new Hole(coorHoleX, coorHoleX));
			return;
		}
		
	}
}
