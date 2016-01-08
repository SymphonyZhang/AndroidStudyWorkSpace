package francis.kylintest.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;


public class AudioActivity extends Activity {
	private static final String TAG = "AudioActivity";
    private Context context = AudioActivity.this;
    private MediaPlayer mp = null;
    private boolean fulltest = false;
    private Bundle extras;
    private Drawable drawable;
    private Button audio_left,audio_right;
    SharedPreferences preferences;
	SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        Toast.makeText(getApplicationContext(), getResources().getText(R.string.audio_test_on), Toast.LENGTH_SHORT).show();
        preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
        audio_left = (Button)findViewById(R.id.audio_left);
        audio_right = (Button)findViewById(R.id.audio_right);
        drawable = audio_left.getBackground();
    }

    public void audioClick(View target) {
        switch (target.getId()) {
            case R.id.audio_pass:
            	editor.putString("audio", "1");
            	editor.commit();
                fulltestGoActivity();
                break;
            case R.id.audio_fail:
            	editor.putString("audio", "0");
            	editor.commit();
                fulltestGoActivity();
                break;
            case R.id.audio_back:
            	if(mp != null){
            		mp.release();
            	}
                Common.gotoActivity(context, MainActivity.class);
            	break;
            case R.id.audio_left:
            	if(mp != null){
            		mp.release();
            	}
            	audio_right.setBackgroundDrawable(drawable);
                audio_left.setBackgroundColor(getResources().getColor(R.color.orange));
            	mp = MediaPlayer.create(context, R.raw.soundone);
            	mp.setVolume(1.0F, 0.0F);
            	mp.setLooping(true);
            	mp.start();
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.left_volume), Toast.LENGTH_SHORT).show();
                break;
            case R.id.audio_right:
            	if(mp != null){
            		mp.release();
            	}
            	audio_left.setBackgroundDrawable(drawable);
            	audio_right.setBackgroundColor(getResources().getColor(R.color.orange));
            	mp = MediaPlayer.create(context, R.raw.soundtwo);
            	mp.setVolume(0.0F, 1.0F);
            	mp.setLooping(true);
            	mp.start();
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.right_volume), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void fulltestGoActivity() {
    	
        extras = getIntent().getExtras();
        if(mp != null){
    		mp.release();
    	}
        if (extras != null) {
            fulltest = extras.getBoolean("fulltest");
            if (fulltest) {
                Common.gotoActivity(context, DeviceActivity.class, "fulltest", true);
            } else {
                Result r = ((Result) getApplicationContext());
                ArrayList<Class> classes = new ArrayList<Class>();
                classes = r.getClasses();
                classes.remove(0);
                r.setClasses(classes);
                if (classes.size() > 0) {
                    Common.gotoActivity(context, classes.get(0), "fulltest", fulltest);
                } else {
                    Common.gotoActivity(context, MainActivity.class);
                }
            }
        }else{
            Common.gotoActivity(context, MainActivity.class);
        }
    }

    /**
     * 监听Back键按下事件
     * 注意：
     * super.onBackPressed()会自动调用finish()方法，关闭当前Activity
     * 若要屏蔽Back键盘，注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mp != null){
    		mp.release();
    	}
        Common.gotoActivity(context, MainActivity.class);
    }

}
