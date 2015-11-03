package com.wellness49.desktop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class WellnessWebActivity extends Activity {

	private WebView webshow;
	private Bundle extras;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wellness_web);
		extras = getIntent().getExtras();
		String urlStr = extras.getString("urlStr");
		webshow = (WebView)findViewById(R.id.webshow);
		webshow.loadUrl(urlStr);
	}
}
