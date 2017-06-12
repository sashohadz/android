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

        Leanplum.setDeviceId("Sasho-12-06-2017-08");

        String appId = "app_IMuJTrB4NWUWPy6CPacc2ISPYf8fzSojC3W8WYBjXDs";
        String devKey = "dev_ENDhrsl8pWrY1hMraGHyj07pshRgX0vlpFAhMoOXd9w";
        String prodKey = "prod_dg4srbBdKqgdIqoM8d4pV2TIX3om1sMXVrAZBfQabcE";

        if (BuildConfig.DEBUG) {
            System.out.println("DEV MODE");
            Leanplum.setAppIdForDevelopmentMode(appId, devKey);
        } else {
            System.out.println("PROD MODE");
            Leanplum.setAppIdForProductionMode(appId, prodKey);
        }

        Leanplum.enableVerboseLoggingInDevelopmentMode();

        com.leanplum.customtemplates.MessageTemplates.register(getApplicationContext());

        LeanplumPushService.enableFirebase();
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
