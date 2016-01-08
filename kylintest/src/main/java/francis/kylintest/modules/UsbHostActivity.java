package francis.kylintest.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SystemUtil;

public class UsbHostActivity extends Activity {
	private static final String TAG = "UsbHostActivity";
	private Context context = UsbHostActivity.this;
	private TextView usb_vid, usb_pid,usb_size;
	private Bundle extras;
	private boolean fulltest = false;
	private int VendorID = 0, ProductID = 0;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	// 创建Handler对象
	Handler handler = new Handler();
	// 新建一个线程对象
	Runnable scanUSBThread = new Runnable() {
		// 将要执行的操作卸载线程对象的run方法中
		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
		@Override
		public void run() {
			UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
			enumerateDevice(manager);
			/**
			 * 调用Handler的postDelayed()方法
			 * 这个方法的作用是：将要执行的线程对象放入到队列当中，待时间结束后，运行制定的线程对象
			 * 第一个参数是Runnable类型：将要执行的线程对象 第二个参数是long类型：延迟的时间，一毫秒为单位
			 */
			handler.postDelayed(scanUSBThread, 3000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usbhost);
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.usb_test_on),
				Toast.LENGTH_SHORT).show();
		init();
		handler.post(scanUSBThread);
		 
	}

	public void init() {
		usb_vid = (TextView)findViewById(R.id.usb_vid);
		usb_pid = (TextView)findViewById(R.id.usb_pid);
		usb_size = (TextView)findViewById(R.id.usb_size);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}

	public void usbClick(View target) {
		scanUSBThread = null;
		handler.removeCallbacks(scanUSBThread);
		switch (target.getId()) {
		case R.id.usb_pass:
			editor.putString("usbhost", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.usb_fail:
			editor.putString("usbhost", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.usb_back:
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, EthernetActivity.class,
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
     *
     *
     */

	// 枚举设备函数
	private void enumerateDevice(UsbManager mUsbManager) {
		if (mUsbManager == null) {
			Toast.makeText(getApplicationContext(), "创建UsbManager失败，请重新启动应用！",
					Toast.LENGTH_SHORT).show();
			return;
		} else {
			
			HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
			if (!(deviceList.isEmpty())) {

				// deviceList不为空
				Iterator<UsbDevice> deviceIterator = deviceList.values()
						.iterator();
				UsbDevice device = deviceIterator.next();
				
				// 保存设备VID和PID
				VendorID = device.getVendorId();
				ProductID = device.getProductId();
				usb_vid.setText(String.valueOf(VendorID));
				usb_pid.setText(String.valueOf(ProductID));
				String s = SystemUtil.getOutSDPath();
				int i = s.lastIndexOf("*");
				int k = s.lastIndexOf("\n");
				s = s.substring(i + 1, k);
				usb_size.setText(SystemUtil.getUSBSize(s, true)+"MB");
			} else {

				Context context = getApplicationContext();
				Toast.makeText(context,getResources().getString(R.string.usb_conn_tips), Toast.LENGTH_SHORT).show();
			}
		}
	}
/*
	public void getUsbDevice() {
		String str1 = "/proc/bus/input/devices";
		String str2 = "";
		StringBuilder sb = new StringBuilder("");
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 10240);
			while ((str2 = localBufferedReader.readLine()) != null) {
				sb.append(str2);
				sb.append("\n");
			}
			localBufferedReader.close();
			Log.i("SystemUtil", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		scanUSBThread = null;
		handler.removeCallbacks(scanUSBThread);
		Common.gotoActivity(context, MainActivity.class);

	}

}
