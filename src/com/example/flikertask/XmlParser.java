package com.example.flikertask;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.Log;

public class XmlParser {
	
	private final String DEBUG_TAG = "xml";
	private final String ENTRY_TAG = "entry";
	private final String TITLE_TAG = "title";
	private final String PUBLISHED_TAG = "published";
	private final String LINK_TAG = "link";
	private final String LINK_HREF_TAG = "href";
	private final String CONTENT_TAG = "content";
	
	public ArrayList<ImageDescription> parseFlicker(InputStream in) throws XmlPullParserException, IOException{
		
		ArrayList<ImageDescription> list = new ArrayList<ImageDescription>();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser xpp = factory.newPullParser();
		xpp.setInput(in,"UTF-8");
		
		while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
			if(xpp.getEventType() == XmlPullParser.START_TAG){
				String nameTag = xpp.getName();
				Log.w(DEBUG_TAG,nameTag);
				if(nameTag.equals(ENTRY_TAG)){
					
				list.add(parseEntry(xpp));;
				}
			}
			xpp.next();
		}
		return list;
	}
	
	private ImageDescription parseEntry(XmlPullParser xpp) throws XmlPullParserException, IOException{
		ImageDescription entryObject  = new ImageDescription();
		while(true){
			if(xpp.getEventType() == XmlPullParser.START_TAG){
				String name = xpp.getName();
				if(name.equals(TITLE_TAG)){
					xpp.next();
					entryObject.setDescription(xpp.getText());
				}else if(name.equals(PUBLISHED_TAG)){
					xpp.next();
					entryObject.setDatePublished(xpp.getText());
				}else if(name.equals(CONTENT_TAG)){
					xpp.next();
					Pattern p = Pattern.compile("((?<=href=\").+/(?=\" ))|((?<=src=\").+(?=\" width))");
					Matcher m = p.matcher(xpp.getText());
					 while (m.find()) { 
					     String url = m.group(0);
					     if(entryObject.getUrlWeb() == null){
					    	 entryObject.setUrlWeb(url);
					     }else if(entryObject.getPictureThumbnail() == null){
					    	 entryObject.setPictureThumbnail(url);
					     }
					     Log.w("test",url);

					 }
				}
			}else if(xpp.getEventType() == XmlPullParser.END_TAG){
				String name = xpp.getName();
				if(name.equals(ENTRY_TAG))
					break;
			}
			xpp.next();
		}
		return entryObject;
	}
	
}
