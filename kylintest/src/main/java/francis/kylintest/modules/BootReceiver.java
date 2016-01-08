package francis.kylintest.modules;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import francis.kylintest.utils.SystemUtil;

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			String s = SystemUtil.getOutSDPath();
			int i = s.lastIndexOf("*");
			int k = s.lastIndexOf("\n");
			s = s.substring(i + 1, k);
			String rebootPath1 = s+"/reboot.txt";
			String rebootPath2 = SystemUtil.getSDPath() + "/tmp/reboot.txt";
			File file1 = new File(rebootPath1);
			File file2 = new File(rebootPath2);
			if(file1.exists() || file2.exists()){
				Intent start = new Intent(context, DeviceActivity.class);
				start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(start);
			}
		}
	}

}