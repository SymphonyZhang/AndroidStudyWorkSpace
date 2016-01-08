package francis.kylintest.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import francis.kylintest.MainActivity;
import francis.kylintest.R;
import francis.kylintest.utils.Common;
import francis.kylintest.utils.Result;


public class HdmiActivity extends Activity {
	private static final String TAG = "HdmiActivity";
    private Context context = HdmiActivity.this;
    private ImageView hdmi_img;
    SharedPreferences preferences;
	SharedPreferences.Editor editor;
    private boolean fulltest = false;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hdmi);
        preferences = getSharedPreferences("state", MODE_PRIVATE);
		editor = preferences.edit();
        hdmi_img = (ImageView)findViewById(R.id.hdmi_img);
        hdmi_img.setImageResource(R.mipmap.hdmi);
        
    }

    public void hdmiClick(View target){
        switch (target.getId()){
            case R.id.hdmi_pass:
            	editor.putString("hdmi", "1");
            	editor.commit();
                fulltestGoActivity();
                break;
            case R.id.hdmi_fail:
            	editor.putString("hdmi", "0");
            	editor.commit();
                fulltestGoActivity();
                break;
            case R.id.hdmi_back:
            	Common.gotoActivity(context, MainActivity.class);
            	break;
        }
    }

    public void fulltestGoActivity(){
        extras = getIntent().getExtras();
        if(extras != null){
            fulltest = extras.getBoolean("fulltest");
            if(fulltest){
                Common.gotoActivity(context,BlueToothActivity.class,"fulltest",true);
            }else {
                Result r = ((Result) getApplicationContext());
                ArrayList<Class> classes = new ArrayList<Class>();
                classes = r.getClasses();
                classes.remove(0);
                r.setClasses(classes);
                if(classes.size() > 0){
                    Common.gotoActivity(context,classes.get(0),"fulltest",fulltest);
                }else {
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
        Common.gotoActivity(context, MainActivity.class);
    }

}
