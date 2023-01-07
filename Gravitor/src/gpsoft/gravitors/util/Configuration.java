package gpsoft.gravitors.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import android.os.Environment;

public class Configuration {

	private static Properties p = new Properties();
	private static String path = Environment.getExternalStorageDirectory().getPath()+"/s1grav.xml";
	private static File file = new File(path);
	
	public static String[] loadConfig(){
		String[] nameAndscore = new String[2];
		try {
			FileInputStream is = new FileInputStream(file);
			p.loadFromXML(is);
			nameAndscore[0] = p.getProperty("Name");
			nameAndscore[1] = p.getProperty("Score");
			is.close();
			return nameAndscore;
		} catch (FileNotFoundException e) {
			return null;
		}catch(IOException e2){
			return null;
		}
	}
	
	public static boolean saveConfig(String name, int score){
		
		try {
			String oldDataName = "", oldDataScore = "";
			if(file.exists()){
				oldDataName = loadConfig()[0];
				oldDataScore = loadConfig()[1];
			}else{
				file.createNewFile();
			}
			FileOutputStream os = new FileOutputStream(file);
			p.setProperty("Name", name + "°#" + oldDataName);
			p.setProperty("Score", Encoder.EncodeScore(score) + "°#" + oldDataScore);
			p.storeToXML(os, "High Scores");
			os.close();
			return true;
		}catch(Exception e){
			deleteFile();
			return false;
		}
	}
	
	public static void deleteFile(){
		if(file.exists()){
			file.delete();
		}
	}
	
}
