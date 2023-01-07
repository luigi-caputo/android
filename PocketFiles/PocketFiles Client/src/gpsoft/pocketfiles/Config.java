package gpsoft.pocketfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import android.os.Environment;

public class Config {

	private static Properties p = new Properties();
	private static FileOutputStream os;
	private static FileInputStream in;
	static String sdDir = Environment.getExternalStorageDirectory().getPath();
	private static File conf = new File(sdDir+"/config.xml");
	
	public static void saveConfig(String ip, String port){
		p.setProperty("port", port);
		p.setProperty("ip", ip);
		try {
			conf.createNewFile();
			os = new FileOutputStream(conf);
			p.storeToXML(os, "User settings");
		} catch (Exception e) {
		}
	}
	
	public static String loadIP(){
		try {
			in = new FileInputStream(conf);
			p.loadFromXML(in);
			return p.getProperty("ip");
		} catch (Exception e) {
		}
		return "";
	}
	
	public static String loadPort(){
		try {
			in = new FileInputStream(conf);
			p.loadFromXML(in);
			return p.getProperty("port");
		} catch (Exception e) {
		}
		return "";
	}
	
	public static void unload(){
		if(conf.exists()){
			conf.delete();
		}
	}
}
