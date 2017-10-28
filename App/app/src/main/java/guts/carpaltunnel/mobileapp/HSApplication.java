package guts.carpaltunnel.mobileapp;

import android.app.Application;
import com.tenmiles.helpstack.HSHelpStack;
import com.tenmiles.helpstack.gears.HSEmailGear;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class HSApplication extends Application {
    public FormManager formManager = new FormManager();
    public static HSHelpStack helpStack;

    @Override
    public void onCreate() {
        super.onCreate();
        helpStack = HSHelpStack.getInstance(this);

        HSEmailGear emailGear = new HSEmailGear("slc.guts17@gmail.com", R.xml.articles);
        helpStack.setGear(emailGear);

    }

}
