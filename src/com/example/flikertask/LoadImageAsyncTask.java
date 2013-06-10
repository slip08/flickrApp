package com.example.flikertask;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.flikertask.FlickerInfAdapter.InfoImageLoad;
import com.example.flikertask.FlickerInfAdapter.ViewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadImageAsyncTask extends AsyncTask<InfoImageLoad, Void, Bitmap> {

	InfoImageLoad inf;
	@Override
	protected Bitmap doInBackground(InfoImageLoad... params) {
		URL url;
		Bitmap bmp = null;
		inf = params[0];
		try {
			url = new URL(inf.urlImage);
			bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bmp;
	}
	
	@Override
	protected void onPostExecute(Bitmap bmp) {
		inf.imageView.setImageBitmap(bmp);
		inf.bmps[inf.position] = bmp;
		super.onPostExecute(bmp);
	}

	

	


}
