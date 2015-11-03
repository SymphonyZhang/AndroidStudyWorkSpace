package com.wellness49.desktop;

import java.util.ArrayList;

import com.wellness49.util.AppInfo;
import com.wellness49.util.Common;
import com.wellness49.util.DrawableAdapter;
import com.wellness49.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CategoryActivity extends Activity {

	private static final String TAG = "CategoryActivity";
	private Context context = CategoryActivity.this;
	private GridView gridview = null;
	private TextView title;
	private ArrayList<AppInfo> Apps = null;
	private ArrayList<String> als = null;
	private AppInfo addApp;
	private Bundle extras;
	private String category;
	private Class cls = CategoryActivity.class;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		title = (TextView)findViewById(R.id.category_title);
		extras = getIntent().getExtras();
		category = extras.getString("category");
		showTitle(category);
		als = Util.readTxt(category);

		if (als.size() > 0) {
			Apps = Util.queryAppInfo(context, als);
		} else {
			Apps = new ArrayList<AppInfo>();
		}
		gridview = (GridView) findViewById(R.id.gridview);
		Resources resources = context.getResources();
		addApp = new AppInfo();
		Drawable drawable = resources.getDrawable(R.mipmap.item_img_add);
		addApp.setAppIcon(drawable);
		addApp.setAppName("Add");
		Apps.add(addApp);
		updateUI(Apps);
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (Apps.get(position).getAppName().equals("Add")) {
					Intent selectIntent = new Intent(context,
							ShowActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("activityName", cls);
					bundle.putString("type", category);
					selectIntent.putExtras(bundle);
					context.startActivity(selectIntent);
					finish();
				} else {
					/*String packageName1 = Apps.get(position).packageName;
					Log.i("CategoryActivity","---------------package1"+packageName1);
					String packageName2 = Apps.get(position).getPackageName();
					Log.i("CategoryActivity","---------------package2"+packageName2);*/
					Intent launcherIntent = Apps.get(position).getIntent();
					startActivity(launcherIntent);
				}
			}
		});
	}

	public void updateUI(ArrayList<AppInfo> apps) {
		DrawableAdapter drawableAdapter = new DrawableAdapter(context, apps);
		gridview.setAdapter(drawableAdapter);
	}

	public void showTitle(String tag) {
		if (tag.equals(Common.COMMUNICATION_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_communication));
		}
		if (tag.equals(Common.MOVIE_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_movie));
		}
		if (tag.equals(Common.TV_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_tv));
		}
		if (tag.equals(Common.SPORT_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_sport));
		}
		if (tag.equals(Common.SHAPE_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_shape));
		}
		if (tag.equals(Common.SOCIALIZE_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_socialize));
		}
		if (tag.equals(Common.EMAIL_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_email));
		}
		if (tag.equals(Common.LEARNLANGUAGES_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_languages));
		}
		if (tag.equals(Common.MUSIC_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_music));
		}
		if (tag.equals(Common.KID_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_kid));
		}
		if (tag.equals(Common.CARTOON_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_cartoon));
		}
		if (tag.equals(Common.INTERNET_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_internet));
		}
		if (tag.equals(Common.EXTRA_HEAD)) {
			title.setText(getResources().getString(R.string.title_activity_extra));
		}
	}
}
