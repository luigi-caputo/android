package gpsoft.pocketfiles;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class FileManager extends Activity{
	
	public static Button browse, start;
	public ListView list;
	public static ProgressBar prog;
	private boolean canStopActivity = false;
	private String filePath = "";
	public static ArrayAdapter<String> myarrayAdapter;
	private static Context context;
	
	public static Handler h = new Handler(){
		
		 public void handleMessage(Message msg) {
			 if(msg.getData().getString("data") == "send"){
				 sendFile();
			 }
			 if(msg.getData().getString("data") == "error"){
				 start.setEnabled(true);
				  browse.setEnabled(true);
				  Toast.makeText(context,
						  "Connection closed or an error occurred", Toast.LENGTH_LONG).show();
			 }
			 if(msg.getData().getString("data") == "ok"){
				 start.setEnabled(true);
				  browse.setEnabled(true);
			 }
	      }
		
	};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this.getApplicationContext();
		setContentView(R.layout.file_manager);
		browse = (Button)findViewById(R.id.butBrowse);
		start = (Button)findViewById(R.id.butStart);
		list = (ListView)findViewById(R.id.lstvFiles);
		prog = (ProgressBar)findViewById(R.id.progState);
		
		myarrayAdapter = 
    			new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
    	list.setAdapter(myarrayAdapter);
    	list.setTextFilterEnabled(true);
    	
		browse.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				openFile(0);
			}
			
		});
		
		start.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				start.setEnabled(false);
				browse.setEnabled(false);
				if(!myarrayAdapter.isEmpty()){
					sendFile();
				}else{
					start.setEnabled(true);
					browse.setEnabled(true);
					Toast.makeText(context, "NO FILE !", Toast.LENGTH_LONG).show();
				}
			}
			
		});
	}
	
	private static void sendFile(){
		  try {
   		   File file = new File(myarrayAdapter.getItem(0));
   		   prog.setProgress(0);
   		   prog.setMax((int) file.length());
	    		
				SharedVars.client.file = file;
				SharedVars.client.os.writeUTF("fi:="+file.length()+"/"+getFileName(myarrayAdapter.getItem(0)));
				SharedVars.client.os.flush();
				myarrayAdapter.remove(myarrayAdapter.getItem(0));
			    
      } catch (Exception e) {
    	  start.setEnabled(true);
		  browse.setEnabled(true);
    	  Toast.makeText(context, "NO CONNECTION !", Toast.LENGTH_LONG).show();
	  }
	}
	
	public void onStart(){
		super.onStart();
	}
	
	private static String getFileName(String path){
		String[] pieces = path.split("/");
		return pieces[pieces.length-1];
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==Activity.RESULT_OK || data!=null){
	        Uri uri = data.getData();
	        if (uri != null && "content".equals(uri.getScheme())) {
	            Cursor cursor = this.getContentResolver().query(uri, new String[] {MediaStore.MediaColumns.DATA}, null, null, null);
	            cursor.moveToFirst();
	            filePath = cursor.getString(0);
	            cursor.close();
	        }
	        else {
	            filePath = uri.getPath();
	        }
		    if(filePath!=""){
		    	myarrayAdapter.add(filePath);
		    }
		}
	}
	
	private void openFile(int CODE) {
		canStopActivity = false;
	    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
	    i.setType("*/*").addCategory(Intent.CATEGORY_OPENABLE);
	    startActivityForResult(i, CODE);
	}
	
	private final void goBack(){
		this.finish();
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder popupBuilder = new AlertDialog.Builder(this);
		popupBuilder.setTitle("Exit");
		popupBuilder.setMessage("Do you really want to close the connection ?");
		popupBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				canStopActivity = true;
				goBack();
			}
		});
		popupBuilder.setNegativeButton("No", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		popupBuilder.show();
		return;
	}
	
	public void onStop(){
		if(canStopActivity){
			SharedVars.client.stop();
		}
		super.onStop();
	}
}
