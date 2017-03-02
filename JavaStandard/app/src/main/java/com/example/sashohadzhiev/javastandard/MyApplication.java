package com.example.sashohadzhiev.javastandard;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumPushService;
import com.leanplum.LeanplumResources;
import com.leanplum.Var;
import com.leanplum.annotations.Parser;
import com.leanplum.annotations.Variable;


import com.leanplum.callbacks.VariablesChangedCallback;


/**
 * Created by sashohadzhiev on 3/28/16.
 */
public class MyApplication extends Application {

    private static Var<String> welcomeLabel = Var.define("welcomeLabel", "");
    @Variable(group="timeBasedTrialSecond") public static String secondVar = "";
    @Variable(name="Show Ads") public static boolean showAds = false;

    static Var<String> itestsec = Var.defineResource("itestSecond", R.drawable.itest);

    @Override
    public void onCreate() {
        super.onCreate();

        Leanplum.setApplicationContext(this);
        Parser.parseVariables(this);
        Parser.parseVariablesForClasses(MainActivity.class);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        Leanplum.setDeviceId("Sasho-01-03-2017-03");
        if (BuildConfig.DEBUG) {
             Leanplum.setAppIdForDevelopmentMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "dev_5yE7VOobZuvUrgoTouFDM7CvZTvD3gDeoze0VNqetTo");
        } else {
            Leanplum.setAppIdForProductionMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "prod_a6cwir5070QOAaVmtkxRAgTD88J40rLWy9lSkvBfJk0");
        }

        LeanplumPushService.setGcmSenderId(LeanplumPushService.LEANPLUM_SENDER_ID);
        Leanplum.enableVerboseLoggingInDevelopmentMode();

        Leanplum.start(this);

        Leanplum.addVariablesChangedAndNoDownloadsPendingHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                Boolean tempBool = showAds;
                System.out.println("   showAds VALUE call: " + tempBool);
                String tempValue = welcomeLabel.value();
                int tempLength = tempValue.length();

                Log.e("   Leanplum ", "Variables Changed And No Downloads Pending");
                Log.e("   welcomeLabel VALUE: ", tempValue);
                System.out.println("   welcomeLabel VALUE: " + tempValue + " " + tempLength);
            }
        });
    }

    @Override
    public Resources getResources() {
        return new LeanplumResources(super.getResources());
    }
}