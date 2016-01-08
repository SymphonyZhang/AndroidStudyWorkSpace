package francis.kylintest.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

public class EthernetActivity extends Activity {
	private static final String TAG = "EthernetActivity";
	private Context context = EthernetActivity.this;
	private TextView txt_ip, txt_ping;
	private boolean fulltest = false;
	private Bundle extras;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ethernet);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.ethernet_test_on),
				Toast.LENGTH_SHORT).show();
		init();

		ConnectivityManager mConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			txt_ip.setText(SystemUtil.getLocalIpAddress());
			getPingResult(txt_ip.getText().toString());
		} else {
			Toast.makeText(getApplicationContext(),
					getResources().getText(R.string.ethernet_connect),
					Toast.LENGTH_SHORT).show();
		}

	}

	private void init() {
		txt_ip = (TextView) findViewById(R.id.txt_ip);
		txt_ping = (TextView) findViewById(R.id.txt_ping);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}

	public void ethernetClick(View target) {
		switch (target.getId()) {
		case R.id.ethernet_pass:
			editor.putString("ethernet", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.ethernet_fail:
			editor.putString("ethernet", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.ethernet_back:
			txt_ip.setText("");
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, HdmiActivity.class, "fulltest",
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

	public void getPingResult(final String ipaddress) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// ping IP地址
				try {

					Process p = Runtime.getRuntime().exec(
							"ping -c 1 -w 5 " + ipaddress);
					int status = p.waitFor();
					if (status == 0) {

						txt_ping.setText(getResources()
								.getString(R.string.pass));
					} else {

						txt_ping.setText(getResources()
								.getString(R.string.fail));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		txt_ip.setText("");
		Common.gotoActivity(context, MainActivity.class);
	}

}
