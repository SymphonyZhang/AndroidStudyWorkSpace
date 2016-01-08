package francis.kylintest.modules;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;

public class BlueToothActivity extends Activity {
	private static final String TAG = "BlueToothActivity";
	private Context context = BlueToothActivity.this;
	private ListView listView;
	private List<String> deviceList;
	private BluetoothAdapter adapter = null;
	private BluetoothReceiver receiver;
	private Bundle extras;
	private boolean fulltest = false;
	private TextView blueTooth_scantip;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.bluetooth_test_on),
				Toast.LENGTH_SHORT).show();
		init();

		// 创建一个IntentFilter对象，将其action制定为BluetoothDevice.ACTION_FOUND
		// IntentFilter它是一个过滤器,只有符合过滤器的Intent才会被我们的BluetoothReceiver所接收
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		receiver = new BluetoothReceiver();
		// 注册广播接收器 注册完后每次发送广播后，BluetoothReceiver就可以接收到这个广播了
		registerReceiver(receiver, filter);

		// 得到BluetoothAdapter对象
		adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null) {
			// 判断当前蓝牙设备是否可用
			if (!adapter.isEnabled()) {
				// 强制打开蓝牙设备
				adapter.enable();
			}
			Toast.makeText(getApplicationContext(),
					getResources().getText(R.string.scan_tips),
					Toast.LENGTH_SHORT).show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
						// 扫描周围的可见的蓝牙设备一次要消耗12秒，废电池电量
						// 扫描到了后结果我们怎么接收呢,扫描周围的蓝牙设备每扫描到一个蓝牙设备就会发送一个广播,我们就需要BroadcastReceiver来接收这个广播,这个函数是异步的调用,并不是扫描12之后才返回结果的,只要一调用这个函数马上返回,不会等12秒
						adapter.startDiscovery();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} else {
			Toast.makeText(getApplicationContext(),
					getResources().getText(R.string.no_local_bluetooth),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public void init() {
		blueTooth_scantip = (TextView) findViewById(R.id.bluetooth_scantip);
		listView = (ListView) findViewById(R.id.listView);
		deviceList = new ArrayList<String>();
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}

	// 通过广播接收查找到的蓝牙设备信息
	private class BluetoothReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				deviceList.add(device.getName());
			}
			showDevices();
		}
	}

	private void showDevices() {
		blueTooth_scantip.setText(getResources()
				.getString(R.string.scan_result));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item1, deviceList);
		listView.setAdapter(adapter);
		if (listView.getCount() > 0) {
		}
	}

	// TODO
	public void blueToothClick(View target) {
		if (adapter != null) {
			adapter.cancelDiscovery();
			adapter.disable();
		}
		switch (target.getId()) {
		case R.id.blueTooth_pass:
			editor.putString("bluetooth", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.blueTooth_fail:
			editor.putString("bluetooth", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.blueTooth_back:
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {

			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, SdCardTestActivity.class,
						"fulltest", true);
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

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (adapter != null) {
			adapter.cancelDiscovery();
			adapter.disable();
		}
		Common.gotoActivity(context, MainActivity.class);
	}

}
