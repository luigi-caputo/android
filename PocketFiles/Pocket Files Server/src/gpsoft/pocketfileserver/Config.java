package gpsoft.pocketfileserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Config {

	private static Properties p = new Properties();
	private static FileOutputStream os;
	private static FileInputStream in;
	private static File conf = new File("config.xml");
	
	public static void saveConfig(String path, String port){
		p.setProperty("port", port);
		p.setProperty("path", path);
		try {
			conf.createNewFile();
			os = new FileOutputStream(conf);
			p.storeToXML(os, "Settings");
		} catch (Exception e) {
		}
	}
	
	public static String loadPort(){
		try {
			in = new FileInputStream(conf);
			p.loadFromXML(in);
			return p.getProperty("port");
		} catch (Exception e) {
		}
		return "6556";
	}
	
	public static String loadPath(){
		try {
			in = new FileInputStream(conf);
			p.loadFromXML(in);
			return p.getProperty("path");
		} catch (Exception e) {
		}
		return "";
	}
	
	public static void unLoad(){
		if(conf.exists()){
			conf.delete();
		}
	}
}
