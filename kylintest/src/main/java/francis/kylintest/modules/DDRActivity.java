package francis.kylintest.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SystemUtil;

public class DDRActivity extends Activity {
	private static final String TAG = "DDRActivity";
	private Context context = DDRActivity.this;
	private TextView ddr_size, storage_size, sdcard_size;
	private Bundle extras;
	private boolean fulltest = false;
    SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ddr);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.storage_test_on),
				Toast.LENGTH_SHORT).show();
		init();
		ddr_size.setText(SystemUtil.getTotalMemory() + "MB");
		storage_size.setText(SystemUtil.getROMSize(true) + "MB");
		sdcard_size.setText(SystemUtil.getSDSize(true) + "MB");
	}

	public void init() {
		ddr_size = (TextView) findViewById(R.id.ddr_size);
		storage_size = (TextView) findViewById(R.id.storage_size);
		sdcard_size = (TextView) findViewById(R.id.sdcard_size);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}

	// TODO
	public void ddrClick(View target) {
		switch (target.getId()) {
		case R.id.ddr_pass:
			editor.putString("storage", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.ddr_fail:
			editor.putString("storage", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.ddr_back:
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, UsbHostActivity.class, "fulltest",fulltest);
			} else {
				Result r = ((Result) getApplicationContext());
				ArrayList<Class> classes = new ArrayList<Class>();
				classes = r.getClasses();
				classes.remove(0);
				r.setClasses(classes);
				if (classes.size() > 0) {
					Common.gotoActivity(context, classes.get(0), "fulltest",fulltest);
				} else {
					Common.gotoActivity(context, MainActivity.class);
				}
			}
		} else {
			Common.gotoActivity(context, MainActivity.class);
		}
	}

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Common.gotoActivity(context, MainActivity.class);
	}

}
