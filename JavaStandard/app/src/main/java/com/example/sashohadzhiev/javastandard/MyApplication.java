package com.example.sashohadzhiev.javastandard;

import android.app.Application;
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
    public static float shootSpeed = 1;  // How fast your ship shoots.

    @Variable
    public static String testingVarFourMay = "TESTINGVAR01";
    @Variable
    public static String testingVarFiveMay = "TESTINGVAR01";
    @Variable(name = "Show Ads")
    public static boolean showAds = false;


    @Override
    public void onCreate() {
        super.onCreate();


        Leanplum.setApplicationContext(this);
        Parser.parseVariables(this);
        Parser.parseVariablesForClasses(MainActivity.class);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        Leanplum.setDeviceId("Sasho-02-06-2017-02");
//        if (BuildConfig.DEBUG) {
//            System.out.println("DEV MODE");
//            Leanplum.setAppIdForDevelopmentMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "dev_5yE7VOobZuvUrgoTouFDM7CvZTvD3gDeoze0VNqetTo");
//        } else {
            System.out.println("PROD MODE");
            Leanplum.setAppIdForProductionMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "prod_a6cwir5070QOAaVmtkxRAgTD88J40rLWy9lSkvBfJk0");
//        }

        Leanplum.enableVerboseLoggingInDevelopmentMode();
        com.leanplum.customtemplates.MessageTemplates.register(getApplicationContext());

//        LeanplumPushService.enableFirebase();
        Leanplum.start(this);

        Leanplum.addVariablesChangedAndNoDownloadsPendingHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {

                String tempValue = welcomeLabel.value();
                int tempLength = tempValue.length();

                Log.e("   Leanplum ", "Variables Changed And No Downloads Pending");
                Log.e("   welcomeLabel VALUE: ", tempValue);
                System.out.println("   welcomeLabel VALUE: " + tempValue + " " + tempLength);
            }
        });
    }
}
