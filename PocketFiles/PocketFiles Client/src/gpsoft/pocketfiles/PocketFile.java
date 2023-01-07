package gpsoft.pocketfiles;

import gpsoft.pocketfiles.net.Client;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

//Copyright GicoPiro 2013

public class PocketFile extends Activity {

	ImageButton butExit;
	Button butConnect, butCancel;
	ProgressBar bar;
	EditText txtIp, txtPort;
	Activity activity;
	CheckBox settings;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pocket_file);
		activity = this;
		butExit = (ImageButton)findViewById(R.id.imgbutExit);
		butConnect = (Button)findViewById(R.id.butConnect);
		bar = (ProgressBar)findViewById(R.id.prbConnect);
		butCancel = (Button)findViewById(R.id.butCancel);
		txtIp = (EditText)findViewById(R.id.txtFile);
		txtPort = (EditText)findViewById(R.id.txtPort);
		settings = (CheckBox)findViewById(R.id.chkSettings);
		
		bar.setVisibility(View.INVISIBLE);
		
		txtIp.setText(Config.loadIP());
		txtPort.setText(Config.loadPort());
		
		buttonsController();
	}
	
	public void onStart(){
		butConnect.setEnabled(true);
		super.onStart();
	}
	
	public void onStop(){
		bar.setVisibility(View.INVISIBLE);
		super.onStop();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.pocket_file, menu);
		return true;
	}
	
	private void buttonsController(){
		
		settings.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				if(settings.isChecked()){
					Config.saveConfig(txtIp.getText().toString(), txtPort.getText().toString());
				}else{
					Config.unload();
				}
			}
			
		});
		
		butExit.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				System.exit(0);
			}
		});
		
		butConnect.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				butConnect.setEnabled(false);
				if(isANumber(txtPort.getText().toString(), false)
						&& txtIp.getText().toString().contains(".") && isANumber(txtIp.getText().toString(), true)){
					bar.setVisibility(View.VISIBLE);
					SharedVars.client=new Client(txtIp.getText().toString(),
							Integer.parseInt(txtPort.getText().toString()), activity, v.getContext());
				}else{
					butConnect.setEnabled(true);
					Toast t = Toast.makeText(v.getContext(), "Please type a correct ip address", Toast.LENGTH_LONG);
					t.show();
				}
			}
		});
		
		butCancel.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				butConnect.setEnabled(true);
				if(SharedVars.client!=null){
					SharedVars.client.stop();
				}
				bar.setVisibility(View.INVISIBLE);
			}
		});
	}
	
	private boolean isANumber(String text, boolean removeDots){
		if(removeDots){
			text = text.replace(".", "");
		}
		
		try{
			Long.parseLong(text);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

}
