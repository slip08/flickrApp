package com.example.flikertask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.util.Log;

public class LoadXmlAsyncTask extends AsyncTask<Void, Void, ArrayList<ImageDescription>> {

	private final String getURL = "http://www.flickr.com/services/feeds/photos_public.gne?format=xml";
	private FlickerInfAdapter adapter;
	public LoadXmlAsyncTask(FlickerInfAdapter adapter) {
		this.adapter = adapter;
	}
	@Override
	protected ArrayList<ImageDescription> doInBackground(Void... arg0) {
		HttpClient client = new DefaultHttpClient(); 
        HttpGet get = new HttpGet(getURL);
        ArrayList<ImageDescription> listImageDescription = null;
        try {
        	HttpResponse responseGet;
			responseGet = client.execute(get);
			HttpEntity entity = responseGet.getEntity();
			InputStream in = entity.getContent();
			XmlParser parser = new XmlParser();
			listImageDescription = parser.parseFlicker(in);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			
			e.printStackTrace();
		}        
        
		return listImageDescription;
	}
	
	@Override
	protected void onPostExecute(ArrayList<ImageDescription> result) {
		super.onPostExecute(result);
		adapter.setData(result);
	}

}
