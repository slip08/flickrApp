package com.example.flikertask;


import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	FlickerInfAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		adapter =  (FlickerInfAdapter)getLastNonConfigurationInstance();
		if(adapter ==null){
			adapter = new FlickerInfAdapter(this, new ArrayList<ImageDescription>());
			LoadXmlAsyncTask task = new LoadXmlAsyncTask(adapter);
			task.execute();
		}
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, 
					long id) {
				String url = adapter.getData().get(position).getUrlWeb();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == R.id.menu_refresh){
			LoadXmlAsyncTask task = new LoadXmlAsyncTask(adapter);
			task.execute();
		}
		return true;
	}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		return adapter;
	}

}
