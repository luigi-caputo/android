package gpsoft.pocketfileserver.net;
import gpsoft.pocketfileserver.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket implements Runnable{

	private ServerSocket server;
	public Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private FileOutputStream fileStream;
	private boolean running = false;
	private Thread thread;
	final int port;
	final Main mainFrame;
	long len = 0; 
	int prog = 0;
	File file;
	byte[] b;
	String[] line;
	
	private boolean noMoreStringData = false;
	public boolean Restart;
	
	public MyServerSocket(int port, Main mainFrame){
		Restart = true;
		this.port = port;
		this.mainFrame= mainFrame;
		this.start();
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
			if(socket!=null&&inputStream!=null&&outputStream!=null){
				socket.close();
				inputStream.close();
				outputStream.close();
			}
			if(server != null){
				server.close();
			}
			if(fileStream!=null){
				fileStream.close();
			}
			thread.join();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void justClose(){
		try{
			if(socket!=null&&inputStream!=null&&outputStream!=null){
				socket.close();
				inputStream.close();
				outputStream.close();
			}
			if(server != null){
				server.close();
			}
			if(fileStream!=null){
				fileStream.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run() {
		if(Restart){
			running = true;
		}
		try {
			server = new ServerSocket(port);
			mainFrame.setTitle(Inet4Address.getLocalHost().toString());
			socket = server.accept();
			inputStream = new ObjectInputStream(socket.getInputStream());
		    outputStream = new ObjectOutputStream(socket.getOutputStream());
			server.close();
		} catch (IOException e1) {
		}
		
		while(running){
			try {
				
				if(!noMoreStringData){
					String data = inputStream.readUTF();
					if(data.equals("connected")){
						mainFrame.lblPort.setText("1 PHONE CONNECTED !");
					}
					if(data.contains("fi:=")){
						data = data.replace("fi:=", "");
						String[] infos = data.split("/");
						len = Integer.parseInt(infos[0]);
						
						long lenKB = len/1024;
						
						file = new File(mainFrame.downloadFolder + "/" + infos[1]);
						file.createNewFile();
						
						line = new String[]{infos[1], Long.toString(lenKB), "Downloading"};
						
						mainFrame.model.addRow(line);
						b = new byte[8192];
						noMoreStringData = true;
						outputStream.writeUTF("ok");
						outputStream.flush();
						fileStream = new FileOutputStream(file);
					}
				}else{
					
					while((prog=inputStream.read(b)) != -1){
						fileStream.write(b, 0, prog);
						len -= prog;
						if(len <= 0){
							break;
						}
					}
					
					fileStream.close();
					line[2] = "Finished";
					mainFrame.model.removeRow(mainFrame.model.getRowCount()-1);
					mainFrame.model.addRow(line);
					
					noMoreStringData = false;
				}
			} catch (IOException e) {
				if(Restart){
					mainFrame.lblPort.setText("Port (NO PHONE CONNECTED):");
					running = false;
					noMoreStringData = false;
					if(mainFrame.model.getRowCount()>0){
						if(mainFrame.model.getValueAt(mainFrame.model.getRowCount()-1, 2)=="Downloading"){
							line[2] = "Aborted";
							mainFrame.model.removeRow(mainFrame.model.getRowCount()-1);
							mainFrame.model.addRow(line);
						}
					}
					justClose();
					run();
				}
			}
		}
		
	}
	
}