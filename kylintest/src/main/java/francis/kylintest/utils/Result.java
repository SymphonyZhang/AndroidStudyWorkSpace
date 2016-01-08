package francis.kylintest.utils;

import java.util.ArrayList;

import android.app.Application;

/**
 * Created by Francis on 2015/4/4.
 */
public class Result extends Application {
    //Checkboxtest
    private ArrayList<Class> classes;
    //fulltest
    private boolean fulltest;  
    
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public boolean isFulltest() {
        return fulltest;
    }

    public void setFulltest(boolean fulltest) {
        this.fulltest = fulltest;
    }

    public ArrayList<Class> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<Class> classes) {
        this.classes = classes;
    }

    

	
}
