package francis.kylintest.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;

public class WIFITestActivity extends Activity {
	private static final String TAG = "WIFITestActivity";
	private Context context = WIFITestActivity.this;
	private TextView mainText;
	private WifiManager mainWifi;
	private WifiReceiver receiverWifi;
	private List<ScanResult> wifiList;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	private StringBuffer sb = new StringBuffer();
	private boolean amount = false, strength = false;
	private Bundle extras;
	private boolean fulltest = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.wifi_test_on),
				Toast.LENGTH_SHORT).show();
		extras = getIntent().getExtras();
		init();
		registerReceiver(receiverWifi, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (!mainWifi.isWifiEnabled()) {
						mainWifi.setWifiEnabled(true);
					}
					Thread.sleep(3000);
					mainWifi.startScan();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		mainText.setText("\n" + getResources().getText(R.string.scaning)
				+ "\n");

	}

	public void wifiClick(View target) {
		switch (target.getId()) {
		case R.id.wifi_refresh:
			mainWifi.startScan();
			mainText.setText("\n" + getResources().getText(R.string.scaning)
					+ "\n");
			break;
		case R.id.wifi_pass:
			editor.putString("wifi", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.wifi_fail:
			editor.putString("wifi", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.wifi_back:
			if (mainWifi.isWifiEnabled()) {
				mainWifi.setWifiEnabled(false);
			}
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		if (mainWifi.isWifiEnabled()) {
			mainWifi.setWifiEnabled(false);
		}
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, DDRActivity.class, "fulltest",
						fulltest);
			} else {
				Result r = ((Result) getApplicationContext());
				ArrayList<Class> classes = new ArrayList<Class>();
				classes = r.getClasses();
				classes.remove(0);
				r.setClasses(classes);
				if (classes.size() > 0) {
					Common.gotoActivity(context, classes.get(0), "fulltest",
							fulltest);
				} else {
					Common.gotoActivity(context, MainActivity.class);
				}
			}
		} else {
			Common.gotoActivity(context, MainActivity.class);
		}
	}

	public void init() {
		mainText = (TextView) findViewById(R.id.allNetWork);
		mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mainWifi.isWifiEnabled()) {
			mainWifi.setWifiEnabled(false);
		}
		Common.gotoActivity(context, MainActivity.class);
	}

	/*
	 * 
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { menu.add(0, 0,
	 * 0, "Refresh"); return super.onCreateOptionsMenu(menu); }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) {
	 * mainWifi.startScan(); mainText.setText("Start Scan"); return
	 * super.onOptionsItemSelected(item); }
	 */

	protected void onPause() {
		unregisterReceiver(receiverWifi);
		super.onPause();
	}

	protected void onResume() {
		registerReceiver(receiverWifi, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		super.onResume();
	}

	class WifiReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			sb = new StringBuffer();
			wifiList = mainWifi.getScanResults();
			if (wifiList.size() >= 5) {
				amount = true;
			}
			if (wifiList.size() == 0) {
				mainText.setText(getResources()
						.getText(R.string.scan_no_result));
			}
			for (int i = 0; i < wifiList.size(); i++) {
				sb.append(new Integer(i + 1).toString()
						+ getResources().getText(R.string.wifi_name));
				sb.append((wifiList.get(i).SSID).toString() + "    "
						+ getResources().getText(R.string.wifi_leve));
				sb.append(getLevel(mainWifi.calculateSignalLevel(
						(wifiList.get(i)).level, 4)));
				sb.append("\n\n");
				if (mainWifi.calculateSignalLevel((wifiList.get(i)).level, 4) == 3) {
					strength = true;
				}
			}
			mainText.setText(sb);
		}
	}

	public String getLevel(int level) {
		String lv = "";
		switch (level) {
		case 0:
			lv = getResources().getText(R.string.leve_one).toString();
			break;
		case 1:
			lv = getResources().getText(R.string.leve_two).toString();
			break;
		case 2:
			lv = getResources().getText(R.string.leve_three).toString();
			break;
		case 3:
			lv = getResources().getText(R.string.leve_full).toString();
			break;
		default:
			break;
		}
		return lv;
	}

}
