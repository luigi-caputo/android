package gpsoft.gravitors;

import gpsoft.gravitors.GUI.ScoreBoard;
import gpsoft.gravitors.entities.Platform;
import gpsoft.gravitors.terrain.Terrain;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

public class Loader {

	
	public static Bitmap background, ball, platform, hole, star, scoreboard, GameOver, button, intro, won;
	public static Terrain terrain;
	public static ArrayList<Platform> plates;
	public static Typeface font;
	public static ScoreBoard scoreBoard;
	public static SoundHandler sound;
	
	public static void load(Resources res, AssetManager as, Context context){
		
		sound = new SoundHandler(context);
		font = Typeface.createFromAsset(as, "Zantroke.otf");
		
		terrain = new Terrain();
		plates = new ArrayList<Platform>();
		background = BitmapFactory.decodeResource(res, R.drawable.backgroud);
		background = Bitmap.createScaledBitmap(background, Screen.WIDTH, Screen.HEIGHT, true);
		
		ball = BitmapFactory.decodeResource(res, R.drawable.ball);
		platform = BitmapFactory.decodeResource(res, R.drawable.platform);
		
		hole = BitmapFactory.decodeResource(res, R.drawable.hole);
		star = BitmapFactory.decodeResource(res, R.drawable.star);
		
		scoreboard = BitmapFactory.decodeResource(res, R.drawable.scoreboard);
		scoreBoard = new ScoreBoard(0,0);
		
		GameOver = BitmapFactory.decodeResource(res, R.drawable.gameover);
		GameOver = Bitmap.createScaledBitmap(GameOver, Screen.WIDTH/2, Screen.HEIGHT/3, true);
		
		intro = BitmapFactory.decodeResource(res, R.drawable.intro);
		intro = Bitmap.createScaledBitmap(intro, Screen.WIDTH/2, Screen.HEIGHT/3, true);
		
		won = BitmapFactory.decodeResource(res, R.drawable.won);
		won = Bitmap.createScaledBitmap(won, Screen.WIDTH/2, Screen.HEIGHT/3, true);
		
		button = BitmapFactory.decodeResource(res, R.drawable.button);
	}
	
}
