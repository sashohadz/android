package com.example.sashohadzhiev.javastandard;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumPushService;
import com.leanplum.Var;
import com.leanplum.annotations.Parser;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.VariablesChangedCallback;


/**
 * Created by sashohadzhiev on 3/28/16.
 */
public class MyApplication extends Application {

    private static Var<String> welcomeLabel = Var.define("welcomeLabel", "");
    @Variable(group = "timeBasedTrialSecond")
    public static String secondVar = "";
    @Variable
    public static float shootSpeed = 1;
    @Variable(name = "Show Ads")
    public static boolean showAds = false;


    @Override
    public void onCreate() {
        super.onCreate();

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());

        Leanplum.setApplicationContext(this);
        Parser.parseVariables(this);
        Parser.parseVariablesForClasses(MainActivity.class);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        Leanplum.setDeviceId("Sasho-29-06-2017-03");

        String appId = "app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo";
        String devKey = "dev_5yE7VOobZuvUrgoTouFDM7CvZTvD3gDeoze0VNqetTo";
        String prodKey = "prod_a6cwir5070QOAaVmtkxRAgTD88J40rLWy9lSkvBfJk0";

//        if (BuildConfig.DEBUG) {
//            System.out.println("DEV MODE");
//            Leanplum.setAppIdForDevelopmentMode(appId, devKey);
//        } else {
            System.out.println("PROD MODE");
            Leanplum.setAppIdForProductionMode(appId, prodKey);
//        }

        Leanplum.enableVerboseLoggingInDevelopmentMode();

        LeanplumPushService.enableFirebase();

        Leanplum.start(this);

        Leanplum.addVariablesChangedAndNoDownloadsPendingHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                Log.e("   Leanplum ", "Variables Changed And No Downloads Pending");
            }
        });
    }
}
