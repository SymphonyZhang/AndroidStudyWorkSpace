package com.wellness49.util;

import java.util.ArrayList;

import com.wellness49.desktop.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectAdapter extends BaseAdapter {

	private ArrayList<AppInfo> mGridViewAppInfo = null;
	private boolean isChice[];
	LayoutInflater inflater = null;
	private Context context;

	public SelectAdapter(Context context, ArrayList<AppInfo> apps) {

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGridViewAppInfo = apps;
		isChice = new boolean[apps.size()];
		for (int i = 0; i < apps.size(); i++) {
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
		if (convertView == null || convertView.getTag() == null) {
			view = inflater.inflate(R.layout.gridview_style, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) convertView.getTag();
		}
		AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.appName.setText(appInfo.getAppName());
		holder.sel.setImageDrawable(getView(position));
		return view;
	}

	class ViewHolder {
		ImageView appIcon;
		TextView appName;
		ImageView sel;

		public ViewHolder(View view) {
			this.sel = (ImageView) view.findViewById(R.id.sel);
			this.appIcon = (ImageView) view.findViewById(R.id.itemImage);
			this.appName = (TextView) view.findViewById(R.id.itemText);
		}
	}

	// 主要就是下面的代码了
	private LayerDrawable getView(int post) {

		Bitmap bitmap = ((BitmapDrawable) context.getResources().getDrawable(
				R.mipmap.item_img_unsel)).getBitmap();

		Bitmap select = null;
		LayerDrawable la = null;
		if (isChice[post] == true) {
			select = BitmapFactory.decodeResource(context.getResources(),
					R.mipmap.item_img_sel);
		}
		if (select != null) {
			Drawable[] array = new Drawable[2];
			array[0] = new BitmapDrawable(bitmap);
			array[1] = new BitmapDrawable(select);
			la = new LayerDrawable(array);
			la.setLayerInset(0, 0, 0, 0, 0); // 第几张图离各边的间距
			la.setLayerInset(1, 0, 0, 0, 0);
		} else {
			Drawable[] array = new Drawable[1];
			array[0] = new BitmapDrawable(bitmap);
			la = new LayerDrawable(array);
			la.setLayerInset(0, 0, 0, 0, 0);
		}
		return la; // 返回叠加后的图
	}

	public void SelectState(int position) {
		isChice[position] = isChice[position] == true ? false : true;
		this.notifyDataSetChanged();
	}
}
