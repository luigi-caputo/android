package gpsoft.gravitors;

import gpsoft.gravitors.util.Configuration;
import gpsoft.gravitors.util.Decoder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Scores extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scores);
		Button clear = (Button)findViewById(R.id.btnClear);
		ListView list = (ListView)findViewById(R.id.lstvScores);
		final ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		
		list.setAdapter(ad);
		
		String[] config = Configuration.loadConfig();
		
		if(config != null){
			String[] names = config[0].split("°#");
			String[] scores = config[1].split("°#");
			
			for(int i = 0;i<names.length; i++){
				String line = "";
				line = names[i] + ": " + Decoder.DecodeScore(scores[i]);
				ad.add(line);
			}
		}
		
		clear.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				ad.clear();
				Configuration.deleteFile();
			}
			
		});
	}
}
