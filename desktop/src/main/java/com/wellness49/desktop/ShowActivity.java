package com.wellness49.desktop;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;

import com.wellness49.util.AppInfo;
import com.wellness49.util.Common;
import com.wellness49.util.SelectAdapter;
import com.wellness49.util.Util;

public class ShowActivity extends Activity {

	private static final String TAG = "AppsActivity";
	private Context context = ShowActivity.this;
	private GridView showgridview = null;
	ArrayList<AppInfo> aInfos = null;
	private boolean allApp = true;
	private SelectAdapter selectAdapter;
	private Bundle extras;
	private ArrayList<String> selectals = null;
	private ArrayList<String> typeapps = null;
	private String shortcuthead;
	private Class cls = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		// 获取从那个Activity跳过来 cls
		// 获取要操作的app种类 shortcuthead
		extras = getIntent().getExtras();
		cls = (Class) extras.getSerializable("activityName");
		shortcuthead = extras.getString("type");
		
		aInfos = new ArrayList<AppInfo>();
		typeapps = new ArrayList<String>();
		
		typeapps = Util.readTxt(shortcuthead);
		showgridview = (GridView) findViewById(R.id.showgridview);
		selectals = new ArrayList<String>();
		selectals.clear();
		aInfos = Util.queryAppInfo(context);
		selectAdapter = new SelectAdapter(context, aInfos);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < aInfos.size(); i++) {
					for (String s : typeapps) {
						if (s.equals(aInfos.get(i).getPackageName())) {
							aInfos.get(i).setSelect(true);
							selectAdapter.SelectState(i);
							selectals.add(shortcuthead
									+ aInfos.get(i).getPackageName());
						}
					}

				}
			}
		}).start();

		showgridview.setAdapter(selectAdapter);
		
		showgridview.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		showgridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				aInfos.get(position).setSelect(!aInfos.get(position).isSelect);
				selectAdapter.SelectState(position);

				if (aInfos.get(position).isSelect) {

					selectals.add(shortcuthead+ aInfos.get(position).getPackageName());

				} else {
					for (int i = 0; i < selectals.size(); i++) {
						if (selectals.get(i).equals(shortcuthead+ aInfos.get(position).getPackageName())) {

							selectals.remove(i);
						}
					}
				}
			}
		});
		showgridview.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		for (int i = 0; i < aInfos.size(); i++) {
			aInfos.get(i).setSelect(false);
		}
		aInfos.clear();
		Util.writeToTxt(selectals, shortcuthead);
		selectals.clear();
		Common.gotoActivity(context, cls,"category",shortcuthead);
	}
}
