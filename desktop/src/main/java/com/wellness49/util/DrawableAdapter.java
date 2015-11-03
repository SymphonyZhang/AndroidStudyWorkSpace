package com.wellness49.util;

import java.util.ArrayList;

import com.wellness49.desktop.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawableAdapter extends BaseAdapter {
	
	private ArrayList<AppInfo> mGridViewAppInfo = null;
	private boolean isChice[];
	LayoutInflater inflater = null;
	private Context context;

	public DrawableAdapter(Context context,ArrayList<AppInfo> apps) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGridViewAppInfo = apps;
	}
	public DrawableAdapter(Context context,ArrayList<AppInfo> apps,boolean chice) {
		
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGridViewAppInfo = apps;
		isChice = new boolean[apps.size()];
		for(int i = 0;i< apps.size();i++){
			isChice[i] = false;
		}
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mGridViewAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mGridViewAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		ViewHolder holder = null;
		if(convertView == null || convertView.getTag() == null){
			view = inflater.inflate(R.layout.gridview_style, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		else{
			view = convertView;
			holder = (ViewHolder)convertView.getTag();
		}
		AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.appName.setText(appInfo.getAppName());
		return view;
	}
	
	class ViewHolder{
		ImageView appIcon;
		TextView appName;
		public ViewHolder(View view){
			this.appIcon = (ImageView) view.findViewById(R.id.itemImage);
			this.appName = (TextView) view.findViewById(R.id.itemText);
		}
	}
	
}
