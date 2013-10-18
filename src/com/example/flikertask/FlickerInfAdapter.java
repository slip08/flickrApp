package com.example.flikertask;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup2;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FlickerInfAdapter extends BaseAdapter {
	
	private ArrayList<ImageDescription> data;
	private Bitmap[] bmps;;
	private Context context;
	
	public FlickerInfAdapter(Context context,ArrayList<ImageDescription> data) {
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
            
            if(convertView == null)
            {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    convertView = (RelativeLayout)inflater.inflate(R.layout.list_item, null, false);
                    
                    ViewHolder holder = new ViewHolder();
                    holder.Image = (ImageView)convertView.findViewById(R.id.imageView1);
                    holder.textViewdatePusblished = (TextView)convertView.findViewById(R.id.textViewDatePuslished);
                    holder.textViewdescription = (TextView) convertView.findViewById(R.id.textViewDescription);
                    convertView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder)convertView.getTag();
            String publishText = data.get(position).getDatePublished();
            String descriptionText = data.get(position).getDescription();
            holder.textViewdatePusblished.setText(publishText);
            holder.textViewdescription.setText(descriptionText);
            
            if(bmps[position] == null){
            	InfoImageLoad infImage = new InfoImageLoad();
                infImage.bmps = bmps;
                infImage.urlImage = data.get(position).getPictureThumbnail();
                infImage.position = position;
                infImage.imageView = holder.Image;
            	holder.Image.setImageResource(R.drawable.ic_launcher);
	            LoadImageAsyncTask imageLoad = new LoadImageAsyncTask();
	            imageLoad.execute(infImage);
            }else
            	holder.Image.setImageBitmap(bmps[position]);
            
            return convertView;
    }
	
	public static class InfoImageLoad{
		public String urlImage;
		public Bitmap[] bmps;
		public int position;
		public ImageView imageView;
	}
    
    public static class ViewHolder
    {
        public TextView textViewdatePusblished;
        public TextView textViewdescription;
        public ImageView Image;
    }
    
    public void setData(ArrayList<ImageDescription> data){
    	this.data = data;
    	bmps = new Bitmap[data.size()];
    	this.notifyDataSetChanged();
    }
    
    public ArrayList<ImageDescription> getData(){
    	return data;
    }

}
