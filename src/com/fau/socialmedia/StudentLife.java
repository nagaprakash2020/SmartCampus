package com.fau.socialmedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.fau.socialmedia.rss.RSSFeed;
import com.fau.socialmedia.rss.RSSItem;
import com.fau.socialmedia.rss.RSSReader;
import com.fau.socialmedia.rss.RSSReaderException;

public class StudentLife extends ListActivity{

	public List<RSSItem> listitem;
    public ArrayList<HashMap<String,String>> field = new ArrayList<HashMap<String,String>>();
	
	private final String KEY_TITLE="title";
	private final String KEY_LINK="link";
	private final String KEY_DATE="date";
	ProgressDialog dialog;
	
    @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement);
        
        dialog=new ProgressDialog(StudentLife.this);
		dialog = ProgressDialog.show(StudentLife.this, "","Processing Request, Please Wait...", true);
		
        GetFeeds getFeeds=new GetFeeds();
        getFeeds.execute();
        
        

    }
    public void updateList() {
    	 
    	dialog.dismiss();
    	if(listitem.size()<=1)
        {
        	AlertDialog.Builder builder=new AlertDialog.Builder(this);
    		builder.setTitle("No Events to Display Now");
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
          
              String title=i.getTitle(); 
	          title=Html.fromHtml(title).toString();
	          map.put(KEY_TITLE	, title);
	          String date=i.getPubDate().toString();
	          map.put(KEY_DATE, date);
	          String link=i.getLink().toString();
	          map.put(KEY_LINK	, link);
	          field.add(map);
        }
        String[] keys={KEY_TITLE,KEY_DATE};
    	int[] ids={android.R.id.text1,android.R.id.text2};
    	
        TextView tView = new TextView(this);
        tView.setText("FAU Academic Events");
        
        getListView().addHeaderView(tView);
        SimpleAdapter adapter=new SimpleAdapter(this, field	, android.R.layout.simple_list_item_2	, keys, ids);
        setListAdapter(adapter);
        
        getListView().setTextFilterEnabled(true);
        ListView list = getListView();
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener()
        {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
        
        
        RSSItem a=listitem.get(position);
    	String url=a.getLink().toString();
        
    	Intent webView=new Intent(StudentLife.this,ShowWebView.class);
    	webView.setData(Uri.parse(url));
    	startActivity(webView);
        }
        });
        }
    	
	}
    
    private class GetFeeds extends AsyncTask<Object, Object, List<RSSItem>>
    {

		@Override
		protected List<RSSItem> doInBackground(Object... arg0) {

			RSSFeed feed=null;
			RSSReader reader = new RSSReader();
	        String uri = "http://events.fau.edu/MasterCalendar/RSSFeeds.aspx?data=6UXt45wBkTcFIBid%2b4Gb2ZRt9fDj2Olw5hvcc0Vii2S2hk92k9o5DCG7rm0ziR9006ndbnUNyrk%3d";
			try {
				feed = reader.load(uri);
				listitem = feed.getItems();
			} catch (RSSReaderException e) {
				e.printStackTrace();
			}
			return listitem;
		}
	
		@Override
		protected void onPostExecute(List<RSSItem> result) {
			
			listitem=result;
			updateList();
		}
    }

	
	
}