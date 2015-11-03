package com.wellness49.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Francis on 2015/4/1.
 */
public class Common {
    public static final String FILEPATH = Util.getSDPath()+"/shortcut.txt";
	//public static final String FILEPATH = "/data/data/com.wellness49.desktop/shortcut.txt";
	//public static final String DEFAULT_FILEPATH = Util.getSDPath()+"/default_shortcut.txt";
	public static final String DEFAULT_FILEPATH = "/system/etc/default_shortcut.txt";
	public static final String COMMUNICATION_HEAD = "coummunication_shortcut_";
	public static final String MOVIE_HEAD = "movie_shortcut_";
	public static final String TV_HEAD = "TV_shortcut_";
	public static final String SPORT_HEAD = "sport_shortcut_";
	public static final String SHAPE_HEAD = "shape_shortcut_";
	public static final String SOCIALIZE_HEAD = "socialize_shortcut_";
	public static final String EMAIL_HEAD = "email_shortcut_";
	public static final String LEARNLANGUAGES_HEAD = "learnlanguages_shortcut_";
	public static final String MUSIC_HEAD = "music_shortcut_";
	public static final String KID_HEAD = "kid_shortcut_";
	public static final String CARTOON_HEAD = "cartoon_shortcut_";
	public static final String INTERNET_HEAD = "internet_shortcut_";
	public static final String EXTRA_HEAD = "extra_shortcut_";
	public static final String WELLNESS_HEAD = "wellness_shortcut_";
	
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
    
    public static void gotoActivity(Context ctx, Class<?> c, String[] name, String[] value) {
        // sendtoService(ctx, Constant.ACT_PLAY_SOUND, 0, String.valueOf(Constant.SOUND_FLINGED));
        Intent intent = new Intent(ctx, c);
        if (name != null)
            for (int i = 0; i < name.length; i++)
                intent.putExtra(name[i], value[i]);
        ctx.startActivity(intent);
        ((Activity) ctx).finish();
    }
    
    public static void gotoActivity(Context ctx,Class<?> c,String key,String value){
        Intent intent = new Intent(ctx,c);
        intent.putExtra(key,value);
        ctx.startActivity(intent);
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
