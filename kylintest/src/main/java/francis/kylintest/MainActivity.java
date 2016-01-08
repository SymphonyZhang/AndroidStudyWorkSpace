package francis.kylintest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import francis.kylintest.modules.AudioActivity;
import francis.kylintest.modules.BlueToothActivity;
import francis.kylintest.modules.DDRActivity;
import francis.kylintest.modules.DeviceActivity;
import francis.kylintest.modules.EthernetActivity;
import francis.kylintest.modules.HdmiActivity;
import francis.kylintest.modules.IrdaActivity;
import francis.kylintest.modules.SdCardTestActivity;
import francis.kylintest.modules.UsbHostActivity;
import francis.kylintest.modules.VideoActivity;
import francis.kylintest.modules.WIFITestActivity;
import francis.kylintest.service.TestService;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SystemUtil;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Context context = MainActivity.this;
    // fulltest : true时代表一键全测，false时代表勾选测试
    private boolean fulltest = false;
    //private boolean fulltest = false, haveNoScreen = true;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10, cb11,
            cb12;
    private RelativeLayout btn_wifi, btn_ddr, btn_usbhost, btn_ethernet,
            btn_hdmi, btn_blueTooth, btn_sdcard, btn_irda, btn_audio,
            btn_video, btn_device;
    //private USBBroadCastReceiver mBroadcastReceiver;
	/*private String tmpPath,haveNoscreenPath;
	private File tmpFile,haveNoscreenFile;*/
    private String tmpPath;
    private File tmpFile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent startIntent = new Intent(this,TestService.class);
        startService(startIntent);
		/*IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
		iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		iFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		iFilter.addDataScheme("file");
		mBroadcastReceiver = new USBBroadCastReceiver();
		registerReceiver(mBroadcastReceiver, iFilter);*/
    }

	/*private class USBBroadCastReceiver extends BroadcastReceiver {

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
					Log.i(TAG, txtpath);
					File file = new File(txtpath);
					haveNoscreenPath = s+"/noscreen.txt";
					haveNoscreenFile = new File(haveNoscreenPath);
					if(!haveNoscreenFile.exists()){
						haveNoscreenFile.createNewFile();
					}
					if (file.exists()) {
						file.delete();
						file.createNewFile();
						unregisterReceiver(mBroadcastReceiver);
						Common.gotoActivity(context, DeviceActivity.class,
								"haveNoScreen", haveNoScreen);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	}*/

    public void init() {
        tmpPath = SystemUtil.getSDPath()+"/tmp";
        tmpFile = new File(tmpPath);
        if(!tmpFile.exists()){
            tmpFile.mkdirs();
        }
        preferences = getSharedPreferences("state", MODE_PRIVATE);
        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        cb5 = (CheckBox) findViewById(R.id.cb5);
        cb6 = (CheckBox) findViewById(R.id.cb6);
        cb7 = (CheckBox) findViewById(R.id.cb7);
        cb8 = (CheckBox) findViewById(R.id.cb8);
        cb9 = (CheckBox) findViewById(R.id.cb9);
        cb10 = (CheckBox) findViewById(R.id.cb10);
        cb11 = (CheckBox) findViewById(R.id.cb11);
        cb12 = (CheckBox) findViewById(R.id.cb12);
        btn_wifi = (RelativeLayout) findViewById(R.id.btn_wifi);
        btn_ddr = (RelativeLayout) findViewById(R.id.btn_ddr);
        btn_usbhost = (RelativeLayout) findViewById(R.id.btn_usbhost);
        btn_ethernet = (RelativeLayout) findViewById(R.id.btn_ethernet);
        btn_hdmi = (RelativeLayout) findViewById(R.id.btn_hdmi);
        btn_blueTooth = (RelativeLayout) findViewById(R.id.btn_blueTooth);
        btn_sdcard = (RelativeLayout) findViewById(R.id.btn_sdcard);
        btn_irda = (RelativeLayout) findViewById(R.id.btn_irda);
        btn_audio = (RelativeLayout) findViewById(R.id.btn_audio);
        btn_video = (RelativeLayout) findViewById(R.id.btn_video);
        btn_device = (RelativeLayout) findViewById(R.id.btn_device);
        showResult();
    }

    public void showResult() {
        /**
         * 0:wifi
         * 1:Storage
         * 2:USBHOST
         * 3:Ethernet
         * 4:HDMI
         * 5:BlueTooth
         * 6:TFCard
         * 7:IRDA
         * 8:AUDIO
         * 9:VIDEO
         * 10:StressTest
         */
        if("1".equals(preferences.getString("wifi", null))){
            btn_wifi.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("wifi", null))){
            btn_wifi.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("storage", null))){
            btn_ddr.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("storage", null))){
            btn_ddr.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("usbhost", null))){
            btn_usbhost.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("usbhost", null))){
            btn_usbhost.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("ethernet", null))){
            btn_ethernet.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("ethernet", null))){
            btn_ethernet.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("hdmi", null))){
            btn_hdmi.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("hdmi", null))){
            btn_hdmi.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("bluetooth", null))){
            btn_blueTooth.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("bluetooth", null))){
            btn_blueTooth.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("tfcard", null))){
            btn_sdcard.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("tfcard", null))){
            btn_sdcard.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("irda", null))){
            btn_irda.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("irda", null))){
            btn_irda.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("audio", null))){
            btn_audio.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("audio", null))){
            btn_audio.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("video", null))){
            btn_video.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("video", null))){
            btn_video.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

        if("1".equals(preferences.getString("stress", null))){
            btn_device.setBackgroundColor(getResources().getColor(
                    R.color.green));
        }
        if("0".equals(preferences.getString("stress", null))){
            btn_device.setBackgroundColor(getResources().getColor(
                    R.color.red));
        }

    }

    public void mainClick(View target) {

        switch (target.getId()) {
            // wifi test
            case R.id.btn_wifi:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, WIFITestActivity.class);
                break;
            // bluetooth test
            case R.id.btn_blueTooth:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, BlueToothActivity.class);
                break;
            // sdcard test
            case R.id.btn_sdcard:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, SdCardTestActivity.class);
                break;
            // ddr&rom test
            case R.id.btn_ddr:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, DDRActivity.class);
                break;
            // ethernet test
            case R.id.btn_ethernet:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, EthernetActivity.class);
                break;
            // usbhost test
            case R.id.btn_usbhost:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, UsbHostActivity.class);
                break;
            // audio test
            case R.id.btn_audio:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, AudioActivity.class);
                break;
            // video test
            case R.id.btn_video:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, VideoActivity.class);
                break;
            // hdmi test
            case R.id.btn_hdmi:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, HdmiActivity.class);
                break;
            // irda test
            case R.id.btn_irda:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, IrdaActivity.class);
                break;
            // device test
            case R.id.btn_device:
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, DeviceActivity.class);
                break;
            // 一键全测
            case R.id.alltest:
                fulltest = true;
                //unregisterReceiver(mBroadcastReceiver);
                Common.gotoActivity(context, WIFITestActivity.class, "fulltest",
                        fulltest);
                break;
            // 勾选测试
            case R.id.checktest:
                Result result = ((Result) getApplicationContext());
                ArrayList<Class> classes = new ArrayList<Class>();

                if (cb1.isChecked()) {
                    classes.add(WIFITestActivity.class);
                }
                if (cb2.isChecked()) {
                    classes.add(DDRActivity.class);
                }
                if (cb3.isChecked()) {
                    classes.add(UsbHostActivity.class);
                }
                if (cb4.isChecked()) {
                    classes.add(EthernetActivity.class);
                }
                if (cb6.isChecked()) {
                    classes.add(HdmiActivity.class);
                }
                if (cb7.isChecked()) {
                    classes.add(BlueToothActivity.class);
                }
                if (cb8.isChecked()) {
                    classes.add(SdCardTestActivity.class);
                }
                if (cb9.isChecked()) {
                    classes.add(IrdaActivity.class);
                }
                if (cb10.isChecked()) {
                    classes.add(AudioActivity.class);
                }
                if (cb12.isChecked()) {
                    classes.add(DeviceActivity.class);
                }
                if (cb11.isChecked()) {
                    classes.add(VideoActivity.class);
                }
                result.setClasses(classes);
                if (classes.size() > 0) {
                    //unregisterReceiver(mBroadcastReceiver);
                    Common.gotoActivity(context, classes.get(0), "fulltest",
                            fulltest);
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getText(R.string.takeCheckbox),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.quick:
                System.exit(0);
                break;
        }
    }

    /**
     * 监听Back键按下事件 注意： super.onBackPressed()会自动调用finish()方法，关闭当前Activity
     * 若要屏蔽Back键盘，注释该行代码即可
     */
    @Override
    // TODO 弹出框确认是否退出应用
    public void onBackPressed() {
        super.onBackPressed();
        //unregisterReceiver(mBroadcastReceiver);
        System.exit(0);
    }
}
