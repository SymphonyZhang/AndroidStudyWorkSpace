package francis.kylintest.modules;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;

public class IrdaActivity extends Activity {
	private static final String TAG = "IrdaTestActivity";
	private Context context = IrdaActivity.this;
	private boolean fulltest = false;
	private Bundle extras;
	private RelativeLayout icon_volumeup, icon_volumedown;
	private Drawable drawable;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_irda);
		icon_volumeup = (RelativeLayout)findViewById(R.id.icon_volumeup);
		icon_volumedown = (RelativeLayout)findViewById(R.id.icon_volumedown);
		preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			icon_volumeup.setBackgroundColor(getResources().getColor(R.color.orange));
			break;

		case KeyEvent.KEYCODE_VOLUME_DOWN:
			icon_volumedown.setBackgroundColor(getResources().getColor(R.color.orange));
			break;
		}
		return super.onKeyDown(keyCode, event);
	}



	public void irdaClick(View target) {
		AudioManager mAudioManager = (AudioManager) IrdaActivity.this
				.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);// 获取最大音量
		int mVolume;
		switch (target.getId()) {
		case R.id.irda_pass:
			editor.putString("irda", "1");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.irda_fail:
			editor.putString("irda", "0");
			editor.commit();
			fulltestGoActivity();
			break;
		case R.id.irda_back:
			Common.gotoActivity(context, MainActivity.class);
			break;
		}
	}

	public void fulltestGoActivity() {
		extras = getIntent().getExtras();
		if (extras != null) {
			fulltest = extras.getBoolean("fulltest");
			if (fulltest) {
				Common.gotoActivity(context, AudioActivity.class, "fulltest",
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
		Common.gotoActivity(context, MainActivity.class);
	}

}
