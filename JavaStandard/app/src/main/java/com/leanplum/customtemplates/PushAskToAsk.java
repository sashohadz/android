package com.leanplum.customtemplates;

import android.app.Activity;
import android.content.Context;

import com.leanplum.ActionArgs;
import com.leanplum.ActionContext;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.callbacks.ActionCallback;
import com.leanplum.callbacks.VariablesChangedCallback;

/**
 * Created by sashohadzhiev on 5/14/16.
 */
public class PushAskToAsk extends BaseMessageDialog {
    private static final String NAME = "Push Ask to Ask";
    private static final String ACTION_NAME = "Register For Push";

    public PushAskToAsk(Activity activity, PushAsktoAskOptions options) {
        super(activity, false, options, null);
        this.options = options;
    }

    public static void register(Context currentContext) {
        Leanplum.defineAction(NAME, Leanplum.ACTION_KIND_MESSAGE | Leanplum.ACTION_KIND_ACTION,
                PushAsktoAskOptions.toArgs(currentContext), new ActionCallback() {
                    @Override
                    public boolean onResponse(final ActionContext context) {
                        return true;
                    }
                });

        Leanplum.defineAction(ACTION_NAME, Leanplum.ACTION_KIND_ACTION,new ActionArgs(),new ActionCallback() {
            @Override
            public boolean onResponse(final ActionContext context) {
                return true;
            }
        });
    }
}