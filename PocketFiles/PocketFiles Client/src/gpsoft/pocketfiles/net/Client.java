package gpsoft.pocketfiles.net;

import gpsoft.pocketfiles.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class Client implements Runnable{

	public Socket socket;
	public ObjectOutputStream os;
	public ObjectInputStream is;
	public File file;
	private FileInputStream Istream;
	private Thread thread;
	private final String ip;
	private final int port;
	private final Activity mainAc;
	private final Context con;
	private boolean running = false;
	int prog = 0;
	
	public Client(String ip, int port, Activity mainAc, Context con){
		this.ip = ip;
		this.port = port;
		this.mainAc = mainAc;
		this.con = con;
		start();
	}
	
	public void start(){
	   if(running)return;
 	   running = true;
	   thread = new Thread(this);
	   thread.start();
	}
	
	public void stop(){
		if(!running)return;
	    running = false;
		try{
			if(socket!=null&&os!=null&&is!=null){
				socket.close();
				os.close();
				is.close();
			}
			if(socket!=null){
				socket.close();
			}
			thread.join();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		try {
			socket = new Socket(ip, port);
			if(socket.isConnected()){
				os = new ObjectOutputStream(socket.getOutputStream());
				is = new ObjectInputStream(socket.getInputStream());
				os.writeUTF("connected");
				os.flush();
				Intent intent = new Intent(con, FileManager.class);
				mainAc.startActivity(intent);
			}
		} catch (Exception e) {
			Log.e("ERROR", e.toString());
		}
		
		if(socket!=null){
			while(running){
				try {
					String data = is.readUTF();
					if(data.equals("ok")){
						byte[] buffer = new byte[8192];
						Istream = new FileInputStream(file);
						
						while((prog=Istream.read(buffer))!=-1){
							os.write(buffer, 0, prog);
							os.flush();
							FileManager.prog.incrementProgressBy(prog);
						}
						
						Istream.close();
						
						Message m = new Message();
						Bundle b = new Bundle();
						if(!FileManager.myarrayAdapter.isEmpty()){
							b.putString("data", "send");
							m.setData(b);
							FileManager.h.sendMessage(m);
						}else{
							b.putString("data", "ok");
							m.setData(b);
							FileManager.h.sendMessage(m);
						}
					}
				} catch (IOException e) {
					Log.e("ERROR", e.toString());
					
					Message m = new Message();
					Bundle b = new Bundle();
					b.putString("data", "error");
					m.setData(b);
					FileManager.h.sendMessage(m);
					
					this.stop();
				}
			}
		}
	}

}
