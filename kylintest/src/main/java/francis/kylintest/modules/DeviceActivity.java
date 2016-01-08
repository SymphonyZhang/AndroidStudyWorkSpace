package francis.kylintest.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.service.TestService;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SystemUtil;


public class DeviceActivity extends Activity {
	private static final String TAG = "DeviceTestActivity";
	private Context context = DeviceActivity.this;
	private Bundle extras;
	private boolean fulltest = false, haveNoScreen = false;
	private LocalBrodcastReceiver mlocalBrodcastReceiver;
	private int count = 1, rebootflag = 0, rebootCount = 0;
	private String logPath, rebootPath, rebootNumberPath, haveNoScreenPath;
	private File logFile, rebootFile, rebootNumberFile, haveNoScrrenFile;
	private boolean flag = true, error = false, numberhave = false,
			reboot_flag = false;
	private TextView device_test, reNumber;
	private int[] array;
	private USBBroadCastReceiver mBroadcastReceiver;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device);
		
		Intent startIntent = new Intent(this,TestService.class);
		startService(startIntent);
		
		Random random = new Random();
		array = new int[20];
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
		iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		iFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		iFilter.addDataScheme("file");
		mBroadcastReceiver = new USBBroadCastReceiver();
		registerReceiver(mBroadcastReceiver, iFilter);
		String noscreenS = SystemUtil.getOutSDPath();
		int noscreenI = noscreenS.lastIndexOf("*");
		int noscreenK = noscreenS.lastIndexOf("\n");
		noscreenS = noscreenS.substring(noscreenI + 1, noscreenK);
		haveNoScreenPath = noscreenS + "/noscreen.txt";
		haveNoScrrenFile = new File(haveNoScreenPath);
		if (haveNoScrrenFile.exists()) {
			haveNoScreen = true;
		}
		extras = getIntent().getExtras();
		device_test = (TextView) findViewById(R.id.device_test);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
		reNumber = (TextView) findViewById(R.id.reNumber);
		if (extras != null) {
			haveNoScreen = extras.getBoolean("haveNoScreen");
		}
		if (haveNoScreen) {
			String s = SystemUtil.getOutSDPath();
			int i = s.lastIndexOf("*");
			int k = s.lastIndexOf("\n");
			s = s.substring(i + 1, k);
			logPath = s + "/device.txt";
			rebootPath = s + "/reboot.txt";
			rebootNumberPath = s + "/rebootNumber.txt";

		} else {
			logPath = SystemUtil.getSDPath() + "/tmp/Devicelog.txt";
			rebootPath = SystemUtil.getSDPath() + "/tmp/reboot.txt";
			rebootNumberPath = SystemUtil.getSDPath() + "/tmp/rebootNumber.txt";
		}
		// 记录第几次后要重启
		rebootFile = new File(rebootPath);
		// 记录准备第几次重启
		rebootNumberFile = new File(rebootNumberPath);
		logFile = new File(logPath);
		if (!rebootFile.exists()) {
			if (logFile.exists()) {
				logFile.delete();
			}
			if (rebootNumberFile.exists()) {
				rebootNumberFile.delete();
			}
			try {
				rebootFile.createNewFile();
				for (int i = 0; i < 20; i++) {
					numberhave = false;
					int j = random.nextInt(99) + 1;

					if (!comple(j)) {
						array[i] = j;
					} else {
						i--;
					}
				}
				Arrays.sort(array);
				for (int i : array) {
					SystemUtil.writeToTxt(rebootPath, i + "");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!rebootNumberFile.exists()) {
			try {
				rebootNumberFile.createNewFile();
				SystemUtil.writeToTxt(rebootNumberPath, "1");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mlocalBrodcastReceiver = new LocalBrodcastReceiver();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction("com.android.MEMTSET_OK");
		registerReceiver(mlocalBrodcastReceiver, mFilter);
		rebootflag = Integer.parseInt(SystemUtil.readFromTxt(rebootNumberFile,
				1));
		reNumber.setText(String.valueOf(rebootflag - 1));
		if (rebootflag > 1) {
			device_test.setText(SystemUtil.readTxt(logFile).trim());
			count = SystemUtil.read(logFile);
			if (rebootflag < 21) {
				rebootCount = Integer.parseInt(SystemUtil.rebootcut(
						SystemUtil.readFromTxt(rebootFile, rebootflag), 1, 0));
			}
		} else if (rebootflag == 1) {
			rebootCount = Integer.parseInt(SystemUtil.readFromTxt(rebootFile,
					rebootflag));
		}
		if (count == 1) {
			SystemUtil.writeToTxt(logPath,
					getResources().getString(R.string.device_start));
			device_test.setText(SystemUtil.readTxt(logFile).trim());
		}
		Toast.makeText(
				getApplicationContext(),
				getResources().getString(R.string.device_tip1) + count
						+ getResources().getString(R.string.device_tip2),
				Toast.LENGTH_SHORT).show();
		SystemProperties.set("ctl.start", "sky_memtest");

		// TODO
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (flag) {
					/**
					 * 启动LED灯的代码写这
					 */
					if (!error) {
						// 成功时的代码
					} else {
						// 失败是的代码
					}

				}
			}
		}).start();

	}

	public boolean comple(int j) {
		boolean b = false;
		for (int i = 0; i < array.length; i++) {
			if (j == array[i]) {
				b = true;
				break;
			}
		}
		return b;
	}

	private class USBBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_MEDIA_REMOVED)) {
				if (haveNoScreen) {
					unregisterReceiver(mBroadcastReceiver);
					Common.gotoActivity(context, MainActivity.class);
				}
			}
		}
	}

	class LocalBrodcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if ("com.android.MEMTSET_OK".equals(action)) {

				if ("true".equals(intent.getExtras().getString("mem_ok"))) {

					Log.i(TAG, "第" + count + "次压力测试成功");
					device_test
							.setText(SystemUtil.readTxt(logFile).trim()
									+ "\n"
									+ getResources().getString(R.string.number)
									+ count
									+ getResources().getString(
											R.string.device_success));

					SystemUtil.writeToTxt(
							logPath,
							getResources().getString(R.string.number)
									+ count
									+ getResources().getString(
											R.string.device_success));
					SystemProperties.set("ctl.stop", "sky_memtest");
					if (rebootflag < 21 && count == rebootCount) {
						rebootGo();
					}

					count++;

					if (count <= 100) {
						Toast.makeText(
								getApplicationContext(),
								getResources().getString(R.string.device_tip1)
										+ count
										+ getResources().getString(
												R.string.device_tip2),
								Toast.LENGTH_SHORT).show();
						SystemProperties.set("ctl.start", "sky_memtest");
					} else {
						flag = false;
						SystemUtil.writeToTxt(logPath, getResources()
								.getString(R.string.device_stop));
						device_test.setText(SystemUtil.readTxt(logFile).trim());
						Toast.makeText(getApplicationContext(),
								getResources().getString(R.string.device_stop),
								Toast.LENGTH_SHORT).show();
						if (rebootFile.exists()) {
							rebootFile.delete();
						}
						if (rebootNumberFile.exists()) {
							rebootNumberFile.delete();
						}
						if (haveNoScrrenFile.exists()) {
							haveNoScrrenFile.delete();
						}
						//if (haveNoScreen) {
							unregisterReceiver(mlocalBrodcastReceiver);
							editor.putString("stress", "1");
							editor.commit();
							if(haveNoScreen){
							Common.gotoActivity(context, VideoActivity.class,
									"haveNoScreen", haveNoScreen);
							}else{
								Common.gotoActivity(context, VideoActivity.class);
							}
						//}
					}
				} else {
					error = true;
					SystemProperties.set("ctl.stop", "sky_memtest");
					Log.i(TAG, "第" + count + "次压力测试失败");
					device_test.setText(SystemUtil.readTxt(logFile).trim()
							+ "\n" + getResources().getString(R.string.number)
							+ count
							+ getResources().getString(R.string.device_fail));
					Toast.makeText(
							getApplicationContext(),
							getResources().getString(R.string.number)
									+ count
									+ getResources().getString(
											R.string.device_fail),
							Toast.LENGTH_SHORT).show();
					SystemUtil.writeToTxt(
							logPath,
							getResources().getString(R.string.number)
									+ count
									+ getResources().getString(
											R.string.device_fail));
					SystemUtil.writeToTxt(logPath,
							getResources().getString(R.string.device_stop));
					device_test.setText(SystemUtil.readTxt(logFile).trim());
					if (rebootFile.exists()) {
						rebootFile.delete();
					}
					if (rebootNumberFile.exists()) {
						rebootNumberFile.delete();
					}
					if (haveNoScrrenFile.exists()) {
						haveNoScrrenFile.delete();
					}
				}
			}
		}
	}

	public void rebootGo() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					/*
					 * SystemUtil.removeLineFromFile(rebootNumberPath,
					 * SystemUtil.readFromTxt(rebootNumberFile, 1));
					 */
					rebootNumberFile.delete();
					rebootNumberFile.createNewFile();
					SystemUtil.writeToTxt(rebootNumberPath, (rebootflag + 1)
							+ "");
					if (Integer.parseInt(SystemUtil.readRebootNumber(
							rebootNumberFile).trim()) == rebootflag + 1) {
						((PowerManager) getSystemService("power")).reboot("重启");
						System.out.println("execute cmd--> reboot\n重启");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void deviceClick(View target) {
		if (rebootFile.exists()) {
			rebootFile.delete();
		}
		if (rebootNumberFile.exists()) {
			rebootNumberFile.delete();
		}
		if (haveNoScrrenFile.exists()) {
			haveNoScrrenFile.delete();
		}
		flag = false;
		switch (target.getId()) {
		case R.id.device_pass:
			editor.putString("stress", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.device_fail:
			editor.putString("stress", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.device_back:
			flag = false;
			unregisterReceiver(mBroadcastReceiver);
			unregisterReceiver(mlocalBrodcastReceiver);
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		unregisterReceiver(mBroadcastReceiver);
		unregisterReceiver(mlocalBrodcastReceiver);
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, VideoActivity.class, "fulltest",
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

	/**
	 * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
	 * 若要屏蔽Back键盘，注释该行代码即可
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		flag = false;
		if (rebootFile.exists()) {
			rebootFile.delete();
		}
		if (rebootNumberFile.exists()) {
			rebootNumberFile.delete();
		}
		if (haveNoScrrenFile.exists()) {
			haveNoScrrenFile.delete();
		}
		unregisterReceiver(mBroadcastReceiver);
		unregisterReceiver(mlocalBrodcastReceiver);
		Common.gotoActivity(context, MainActivity.class);
	}

}
