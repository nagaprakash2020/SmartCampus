package com.fau.socialmedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fau.socialmedia.rss.RSSFeed;
import com.fau.socialmedia.rss.RSSItem;
import com.fau.socialmedia.rss.RSSReader;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Bloghandler extends Handler{

	ProgressDialog dialog;
	Context c;
	Intent i;
	RSSReader reader;
	RSSFeed feed;
	public List<RSSItem> listitem ;
	private ArrayList<HashMap<String,String>> field = new ArrayList<HashMap<String,String>>();
	ListView list;
	
	private final String KEY_TITLE="title";
	private final String KEY_LINK="link";
	private final String KEY_DATE="date";
	
	public Bloghandler(Context context)
	{
		c=context;
	}
	public void setValues(List<RSSItem> listitem,ProgressDialog dialog,ListView list)
	{
		this.listitem=listitem;
		Log.d("ListItem in constructor",this.listitem.toString());
		this.dialog=dialog;
		this.list=list;
	}
	
	@Override
	public void handleMessage(Message msg) {
		Log.d("Message",msg.toString());
		super.handleMessage(msg);
		switch(msg.what){
		
		case 0:
			dialog.dismiss();
			//Log.d("Feed",feed.toString()+"Feed in onCreate");
        	if(listitem.size()<=1)
        	{
        		AlertDialog.Builder builder=new AlertDialog.Builder(c);
        		builder.setTitle("Some Thing Went Wrong");
        		builder.setMessage("Sorry Check Back later");
        		builder.setPositiveButton(android.R.string.ok, null);
        		
        		AlertDialog dialog=builder.create();
        		dialog.show();
        	}
        	else
        	{
        		 for (RSSItem i: listitem) 
     	        {
        			 HashMap<String,String> map=new HashMap<String,String>();
     	          
     	          //This will remove html related text from title
        		  String title=i.getTitle(); 
     	          title=Html.fromHtml(title).toString();
     	          map.put(KEY_TITLE	, title);
     	          String date=i.getPubDate().toString();
     	          map.put(KEY_DATE, date);
     	          String link=i.getLink().toString();
     	          map.put(KEY_LINK	, link);
     	          
     	          field.add(map);
     	          Log.d("Field","field"+"might be empty");
     	        }
        		
       
        	
        	String[] keys={KEY_TITLE,KEY_DATE};
        	int[] ids={android.R.id.text1,android.R.id.text2};
        	
        	TextView tView = new TextView(c);
            tView.setText("FAU FILM FEEDS");
            list.addHeaderView(tView);
            
            }
            }
	}
}
