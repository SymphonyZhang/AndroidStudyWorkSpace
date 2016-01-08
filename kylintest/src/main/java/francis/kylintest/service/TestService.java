package francis.kylintest.service;

import java.io.File;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import francis.kylintest.modules.DeviceActivity;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.SystemUtil;

public class TestService extends Service {
	private String haveNoscreenPath;
	private File haveNoscreenFile;
	private USBBroadCastReceiver mBroadcastReceiver;
	private boolean haveNoScreen = true;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		iFilter.addDataScheme("file");
		mBroadcastReceiver = new USBBroadCastReceiver();
		registerReceiver(mBroadcastReceiver, iFilter);
		super.onCreate();
	}

	private class USBBroadCastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
				try {
					String s = SystemUtil.getOutSDPath();
					int i = s.lastIndexOf("*");
					int k = s.lastIndexOf("\n");
					s = s.substring(i + 1, k);
					String txtpath = s + "/device.txt";
					File file = new File(txtpath);
					if (file.exists()) {
						file.delete();
						file.createNewFile();
						haveNoscreenPath = s + "/noscreen.txt";
						haveNoscreenFile = new File(haveNoscreenPath);
						if (!haveNoscreenFile.exists()) {
							haveNoscreenFile.createNewFile();
						}
						Common.gotoActivity(getApplicationContext(),
								DeviceActivity.class, "haveNoScreen",
								haveNoScreen, Intent.FLAG_ACTIVITY_NEW_TASK);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}

}
