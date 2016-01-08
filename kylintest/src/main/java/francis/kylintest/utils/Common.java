package francis.kylintest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Francis on 2015/4/1.
 */
public class Common {

    private static final String TAG = "Common";

    public static void gotoActivity(Context ctx,Class<?> c){
        gotoActivity(ctx,c,true);
    }
    public static void gotoActivity(Context ctx,Class<?> c,boolean close){
        Intent intent = new Intent(ctx,c);
        ctx.startActivity(intent);
        if(close){
            ((Activity) ctx).finish();
        }
    }
    public static void gotoActivity(Context ctx,Class<?> c,String s,boolean b){
        Intent intent = new Intent(ctx,c);
        intent.putExtra(s,b);
        ctx.startActivity(intent);
        ((Activity) ctx).finish();
    }
    //service启动Activity
    public static void gotoActivity(Context ctx,Class<?> c,String s,boolean b,int flag){
        Intent intent = new Intent(ctx,c);
        intent.putExtra(s,b);
        intent.setFlags(flag);
        ctx.startActivity(intent);
        ((Activity) ctx).finish();
    }
}
