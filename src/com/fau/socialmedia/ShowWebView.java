package com.fau.socialmedia;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class ShowWebView extends Activity {

	protected String murl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		
		Intent i=getIntent();
		 murl=i.getData().toString();
		 WebView webView=(WebView) findViewById(R.id.webView1);
		 webView.loadUrl(murl);
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_web_view, menu);
		
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId=item.getItemId();
		
		if(itemId==R.id.action_share)
		{
			sharePost();
		}
		return super.onOptionsItemSelected(item);
	}

	private void sharePost() {
		Intent shareIntent=new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, murl);
		startActivity(Intent.createChooser(shareIntent, getString(R.string.share_text)));
		
	}

}
