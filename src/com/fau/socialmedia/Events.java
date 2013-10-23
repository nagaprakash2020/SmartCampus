package com.fau.socialmedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fau.socialmedia.rss.*;

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

public class Events extends ListActivity {

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
        
        dialog=new ProgressDialog(Events.this);
		dialog = ProgressDialog.show(Events.this, "","Processing Request, Please Wait...", true);
		
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
        
    	Intent webView=new Intent(Events.this,ShowWebView.class);
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
	        String uri = "http://events.fau.edu/MasterCalendar/RSSFeeds.aspx?data=tA%2bhCNXmZerO%2bljV3wfOHQSTnDtB%2bxnWM5%2f%2bEXbn%2bxuoDPj4gi%2buU3rDIZp9Xg%2b3";
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
