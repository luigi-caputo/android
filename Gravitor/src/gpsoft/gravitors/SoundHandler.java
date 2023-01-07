package gpsoft.gravitors;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.KeyEvent;

public class SoundHandler{

	private SoundPool sp;
	public static int CLICK;
	public static int STAR;
	public static int PLATFORM;
	public static int BALL;
	public static int HOLE;
	public static int PENCIL;
	private AudioManager am;
	int volume;
	
	public SoundHandler(Context context){
		sp = new SoundPool(7, AudioManager.STREAM_MUSIC, 0);
		am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		
		CLICK = sp.load(context, R.raw.select, 1);
		STAR = sp.load(context, R.raw.star, 1);
		PLATFORM = sp.load(context, R.raw.platform, 1);
		BALL = sp.load(context, R.raw.ball, 1);
		HOLE = sp.load(context, R.raw.hole, 1);
		PENCIL = sp.load(context, R.raw.pencil, 1);
	}
	
	public void play(int ID){
		volume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		sp.play(ID, volume, volume, 1, 0, 1);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
	    case KeyEvent.KEYCODE_VOLUME_UP:
	        am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	        return true;
	    case KeyEvent.KEYCODE_VOLUME_DOWN:
	        am.adjustStreamVolume(AudioManager.STREAM_MUSIC,
	                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	        return true;
	    default:
	        return false;
	    }
	}
	
}
