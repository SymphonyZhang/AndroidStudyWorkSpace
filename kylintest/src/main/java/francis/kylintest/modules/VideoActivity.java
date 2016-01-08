package francis.kylintest.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;
import francis.kylintest.utils.SystemUtil;

public class VideoActivity extends Activity {
	private static final String TAG = "VideoActivity";
	private Context context = VideoActivity.this;
	private VideoView vv;
	private File videoFile, txtFile;
	private MediaController mc;
	private boolean fulltest = false, haveNoScreen = false;
	private Bundle extras;
	private String videoPath, txtPath;
	private USBBroadCastReceiver mBroadcastReceiver;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
		iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		iFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		iFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		iFilter.addDataScheme("file");
		mBroadcastReceiver = new USBBroadCastReceiver();
		registerReceiver(mBroadcastReceiver, iFilter);
		extras = getIntent().getExtras();
		if (extras != null) {
			haveNoScreen = extras.getBoolean("haveNoScreen");
		}
		Toast.makeText(getApplicationContext(),
				getResources().getText(R.string.video_test_on),
				Toast.LENGTH_SHORT).show();
		vv = (VideoView) findViewById(R.id.vv);
		// videoPath = SystemUtil.getSDPath() + "/system/media/test.mp4";
		videoPath = "/system/media/test";
		videoFile = new File(videoPath);
		if (haveNoScreen) {
			String s = SystemUtil.getOutSDPath();
			int i = s.lastIndexOf("*");
			int k = s.lastIndexOf("\n");
			s = s.substring(i + 1, k);
			txtPath = s + "/videoLog.txt";

		} else {
			txtPath = SystemUtil.getSDPath() + "/tmp/timelog.txt";
		}
		txtFile = new File(txtPath);

		if (txtFile.exists()) {
			txtFile.delete();
		}
		try {

			txtFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mc = new MediaController(context);
		if (videoFile.exists()) {
			// vv.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.test));
			vv.setVideoPath(videoPath);
			vv.setMediaController(mc);
			vv.requestFocus();
			SystemUtil.writeToTxt(txtPath,
					getResources().getString(R.string.video_start));
			vv.start();
			vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer arg) {
					String time = SystemUtil.getTime();
					SystemUtil.writeToTxt(txtPath, time.toString());
					// vv.setVideoURI(Uri.parse("android.resource://"+
					// getPackageName() + "/" + R.raw.test));
					vv.setVideoPath(videoPath);
					vv.setMediaController(mc);
					vv.requestFocus();
					vv.start();
				}
			});
		} else {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.no_video),
					Toast.LENGTH_SHORT).show();
		}
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

	public void videoClick(View target) {
		unregisterReceiver(mBroadcastReceiver);
		switch (target.getId()) {
		case R.id.video_pass:
			editor.putString("video", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.video_fail:
			editor.putString("video", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.video_back:
			if (vv.isPlaying()) {
				vv.stopPlayback();
			}
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		if (vv.isPlaying()) {
			vv.stopPlayback();
		}
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, MainActivity.class);
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
		if (vv.isPlaying()) {
			vv.stopPlayback();
		}
		unregisterReceiver(mBroadcastReceiver);
		Common.gotoActivity(context, MainActivity.class);
	}

}
