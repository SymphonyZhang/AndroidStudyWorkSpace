package com.wellness49.desktop;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;

import com.wellness49.util.Common;
import com.wellness49.util.Util;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private Context context = MainActivity.this;

    private ImageView communication_img, movie_img, tv_img, sports_img,
            shape_img, socialize_img, email_img, languages_img, music_img,
            kids_img, cartoons_img, internet_img, extras_img, setting_img,
            cryo_img, imaqua_img, productandservices_img,
            productpresentation_img, testimonial_img, videos_img, estore_img,
            ceo_img, toppartners_img, opportunitypresentation_img,
            register_img, backoffice_img, support_img, home_img, aboutus_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File readfile = new File(Common.DEFAULT_FILEPATH);
        File writefile = new File(Common.FILEPATH);
        //Util.defaultToData(readfile, writefile);
        init();
        doListener();
    }

    public void init(){
        communication_img = (ImageView) findViewById(R.id.communication_img);
        communication_img.requestFocus();
        movie_img = (ImageView)findViewById(R.id.movie_img);
        tv_img = (ImageView)findViewById(R.id.tv_img);
        sports_img = (ImageView)findViewById(R.id.sport_img);
        shape_img = (ImageView)findViewById(R.id.shape_img);
        socialize_img = (ImageView)findViewById(R.id.socialize_img);
        email_img = (ImageView)findViewById(R.id.email_img);
        languages_img = (ImageView)findViewById(R.id.languages_img);
        music_img = (ImageView)findViewById(R.id.music_img);
        kids_img = (ImageView)findViewById(R.id.kid_img);
        cartoons_img = (ImageView)findViewById(R.id.cartoons_img);
        internet_img = (ImageView)findViewById(R.id.internet_img);
        extras_img = (ImageView)findViewById(R.id.extras_img);
        setting_img = (ImageView)findViewById(R.id.setting_img);

        cryo_img = (ImageView)findViewById(R.id.cryo_img);
        imaqua_img = (ImageView)findViewById(R.id.imaqua_img);
        productandservices_img = (ImageView)findViewById(R.id.productandservices_img);
        productpresentation_img = (ImageView)findViewById(R.id.productpresentation_img);
        testimonial_img = (ImageView)findViewById(R.id.testimonial_img);
        videos_img = (ImageView)findViewById(R.id.videos_img);
        estore_img = (ImageView)findViewById(R.id.estore_img);
        ceo_img = (ImageView)findViewById(R.id.ceo_img);
        toppartners_img = (ImageView)findViewById(R.id.toppartners_img);
        opportunitypresentation_img = (ImageView)findViewById(R.id.opportunitypresentation_img);
        register_img = (ImageView)findViewById(R.id.register_img);
        backoffice_img = (ImageView)findViewById(R.id.backoffice_img);
        support_img = (ImageView)findViewById(R.id.support_img);
        home_img = (ImageView)findViewById(R.id.home_img);
        aboutus_img = (ImageView)findViewById(R.id.aboutus_img);
    }

    public void doListener(){
        communication_img.setOnFocusChangeListener(new MyFocusListener());
        movie_img.setOnFocusChangeListener(new MyFocusListener());
        tv_img.setOnFocusChangeListener(new MyFocusListener());
        sports_img.setOnFocusChangeListener(new MyFocusListener());
        shape_img.setOnFocusChangeListener(new MyFocusListener());
        socialize_img.setOnFocusChangeListener(new MyFocusListener());
        email_img.setOnFocusChangeListener(new MyFocusListener());
        languages_img.setOnFocusChangeListener(new MyFocusListener());
        music_img.setOnFocusChangeListener(new MyFocusListener());
        kids_img.setOnFocusChangeListener(new MyFocusListener());
        cartoons_img.setOnFocusChangeListener(new MyFocusListener());
        internet_img.setOnFocusChangeListener(new MyFocusListener());
        extras_img.setOnFocusChangeListener(new MyFocusListener());
        setting_img.setOnFocusChangeListener(new MyFocusListener());

        cryo_img.setOnFocusChangeListener(new MyFocusListener());
        imaqua_img.setOnFocusChangeListener(new MyFocusListener());
        productandservices_img.setOnFocusChangeListener(new MyFocusListener());
        productpresentation_img.setOnFocusChangeListener(new MyFocusListener());
        testimonial_img.setOnFocusChangeListener(new MyFocusListener());
        videos_img.setOnFocusChangeListener(new MyFocusListener());
        estore_img.setOnFocusChangeListener(new MyFocusListener());
        ceo_img.setOnFocusChangeListener(new MyFocusListener());
        toppartners_img.setOnFocusChangeListener(new MyFocusListener());
        opportunitypresentation_img.setOnFocusChangeListener(new MyFocusListener());
        register_img.setOnFocusChangeListener(new MyFocusListener());
        backoffice_img.setOnFocusChangeListener(new MyFocusListener());
        support_img.setOnFocusChangeListener(new MyFocusListener());
        home_img.setOnFocusChangeListener(new MyFocusListener());
        aboutus_img.setOnFocusChangeListener(new MyFocusListener());
    }

    class MyFocusListener implements OnFocusChangeListener{

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            // TODO Auto-generated method stub
            if (hasFocus) {
                v.setBackgroundColor(getResources()
                        .getColor(R.color.transparent_orange));

            } else {
                v.setBackgroundColor(getResources()
                        .getColor(R.color.transparent_background));
            }
        }

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void mainClick(View target) {
        switch (target.getId()) {
            case R.id.communication_img:
                // Common.gotoActivity(context, CommunicationActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.COMMUNICATION_HEAD);
                break;
            case R.id.movie_img:
                // Common.gotoActivity(context, MovieActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.MOVIE_HEAD);
                break;
            case R.id.tv_img:
                // Common.gotoActivity(context, TvActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.TV_HEAD);
                break;
            case R.id.sport_img:
                // Common.gotoActivity(context, SportActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.SPORT_HEAD);
                break;
            case R.id.shape_img:
                // Common.gotoActivity(context, ShapeActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.SHAPE_HEAD);
                break;
            case R.id.socialize_img:
                // Common.gotoActivity(context, SocializeActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.SOCIALIZE_HEAD);
                break;
            case R.id.email_img:
                // Common.gotoActivity(context, EmailActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.EMAIL_HEAD);
                break;
            case R.id.languages_img:
                // Common.gotoActivity(context, LanguagesActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.LEARNLANGUAGES_HEAD);
                break;
            case R.id.music_img:
                // Common.gotoActivity(context, MusicActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.MUSIC_HEAD);
                break;
            case R.id.kid_img:
                // Common.gotoActivity(context, KidActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.KID_HEAD);
                break;
            case R.id.cartoons_img:
                // Common.gotoActivity(context, CartoonActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.CARTOON_HEAD);
                break;
            case R.id.internet_img:
                // Common.gotoActivity(context, InternetActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.INTERNET_HEAD);
                break;
            case R.id.extras_img:
                // Common.gotoActivity(context, ExtraActivity.class, false);
                Common.gotoActivity(context, CategoryActivity.class, "category",
                        Common.EXTRA_HEAD);
                break;
            case R.id.setting_img:
                Util.doStartApplicationWithPackageName(context,
                        "com.mbx.settingsmbox");
                break;
            case R.id.cryo_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/cryo");
                break;
            case R.id.imaqua_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/imaqua");
                break;
            case R.id.productandservices_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/products-and-services");
                break;
            case R.id.productpresentation_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/presentation");
                break;
            case R.id.testimonial_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/testimonies");
                break;
            case R.id.videos_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/video");
                break;
            case R.id.estore_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/estore");
                break;
            case R.id.ceo_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/ceopres");
                break;
            case R.id.toppartners_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/toppartners");
                break;
            case R.id.opportunitypresentation_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/site/page/opportunity");
                break;
            case R.id.register_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/register");
                break;
            case R.id.backoffice_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/backoffice");
                break;
            case R.id.support_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/customerservice");
                break;
            case R.id.home_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com");
                break;
            case R.id.aboutus_img:
                Common.gotoActivity(context, WellnessWebActivity.class, "urlStr",
                        "http://wellness49.com/aboutus");
                break;
        }
    }
}
