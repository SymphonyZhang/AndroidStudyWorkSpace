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
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SDCardScanner;
import francis.kylintest.utils.SystemUtil;

public class SdCardTestActivity extends Activity {
	private static final String TAG = "SdCardTestActivity";
	private Context context = SdCardTestActivity.this;
	private TextView card_size;
	private boolean fulltest = false, isTFCard = false;
	private Bundle extras;
	private USBBroadCastReceiver mBroadcastReceiver;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdcard);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.sdcard_test_on),
				Toast.LENGTH_SHORT).show();

		init();
		
		
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		iFilter.addDataScheme("file");
		mBroadcastReceiver = new USBBroadCastReceiver();
		registerReceiver(mBroadcastReceiver, iFilter);
		
		
		
		List<String> paths = SDCardScanner.getExtSDCardPaths();
		for (int i = 0; i < paths.size(); i++) {
			if ("/mnt/external_sd".equals(paths.get(i))) {
				card_size.setText(SystemUtil.getTFSize(true) + " MB");
				isTFCard = true;
			}
		}
		if (!isTFCard) {
			Toast.makeText(getApplicationContext(),
					getResources().getText(R.string.tfcard_no),
					Toast.LENGTH_LONG).show();
		}
	}

	private class USBBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
				List<String> paths = SDCardScanner.getExtSDCardPaths();
				for (int i = 0; i < paths.size(); i++) {
					if ("/mnt/external_sd".equals(paths.get(i))) {
						card_size.setText(SystemUtil.getTFSize(true) + "MB");
						isTFCard = true;
					}
				}
			}

		}
	}

	public void sdcardClick(View target) {
		unregisterReceiver(mBroadcastReceiver);
		switch (target.getId()) {
		case R.id.sdcard_pass:
			editor.putString("tfcard", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.sdcard_fail:
			editor.putString("tfcard", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.sdcard_back:
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, IrdaActivity.class, "fulltest",
						true);
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
		card_size = (TextView) findViewById(R.id.card_size);
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
		unregisterReceiver(mBroadcastReceiver);
		Common.gotoActivity(context, MainActivity.class);
	}

}
