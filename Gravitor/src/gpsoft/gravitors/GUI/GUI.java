package gpsoft.gravitors.GUI;

import gpsoft.gravitors.Loader;
import android.graphics.Paint;

public class GUI {

	protected int x, y;
	protected Paint paint;
	
	public GUI(int x, int y){
		this.x = x;
		this.y = y;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTypeface(Loader.font);
	}
	
}
