package com.teamtreehouse.oslist;

import android.app.Application;
import android.util.Log;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.annotations.Parser;
import com.leanplum.callbacks.VariablesChangedCallback;

/**
 * Created by sashohadzhiev on 4/11/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Leanplum.setApplicationContext(this);
        Parser.parseVariables(this);
        Parser.parseVariablesForClasses(MainActivity.class);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        Leanplum.setDeviceId("Sasho-11-04-2017-01");
        if (BuildConfig.DEBUG) {
            System.out.println("DEV MODE");
            Leanplum.setAppIdForDevelopmentMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "dev_5yE7VOobZuvUrgoTouFDM7CvZTvD3gDeoze0VNqetTo");
        } else {
        System.out.println("PROD MODE");
        Leanplum.setAppIdForProductionMode("app_nQGF1lrbStJopxK41pzNwueB7jBbWp4Hl0Qqq1BsYNo", "prod_a6cwir5070QOAaVmtkxRAgTD88J40rLWy9lSkvBfJk0");
        }

        Leanplum.enableVerboseLoggingInDevelopmentMode();

        Leanplum.start(this);

        Leanplum.addVariablesChangedAndNoDownloadsPendingHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                Log.e("   Leanplum ", "Variables Changed And No Downloads Pending");
            }
        });
    }
}
