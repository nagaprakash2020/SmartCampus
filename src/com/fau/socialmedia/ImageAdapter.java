package com.fau.socialmedia;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

	Context context;
	public Integer[] imageIds=
		{
			R.drawable.chat,R.drawable.directory,
			R.drawable.calendar_icon,R.drawable.announcements,
			R.drawable.map_icon,R.drawable.map_count_icon,R.drawable.student_life_logo
			
		};
	
	public ImageAdapter(Context c)
	{
		this.context=c;
	}
	
	public int getCount() {
		return imageIds.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageIds[position];
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		ImageView imageview=new ImageView(context);
		imageview.setImageResource(imageIds[position]);
		imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageview.setLayoutParams(new GridView.LayoutParams(70,70));
		return imageview;
	}

}
