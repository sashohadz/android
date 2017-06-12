package com.leanplum.customtemplates;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.leanplum.ActionArgs;
import com.leanplum.ActionContext;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.callbacks.ActionCallback;
import com.leanplum.callbacks.PostponableAction;

import java.util.Map;

import static com.leanplum.customtemplates.MessageTemplates.getApplicationName;

/**
 * Created by sashohadzhiev on 6/2/17.
 */

public class ThreeButtonConfirm {
    private static final String NAME = "ThreeButtonConfirm";

    public static void register(final Context currentContext) {
        Leanplum.defineAction(
                NAME,
                Leanplum.ACTION_KIND_MESSAGE | Leanplum.ACTION_KIND_ACTION,
                new ActionArgs().with(MessageTemplates.Args.TITLE, getApplicationName(currentContext))
                        .with(MessageTemplates.Args.MESSAGE, MessageTemplates.Values.CONFIRM_MESSAGE)
                        .with(MessageTemplates.Args.ACCEPT_TEXT, MessageTemplates.Values.YES_TEXT)
                        .with(MessageTemplates.Args.CANCEL_TEXT, MessageTemplates.Values.NO_TEXT)
                        .with(MessageTemplates.Args.MAYBE_TEXT, MessageTemplates.Values.MAYBE_TEXT)
                        .withAction("TestingDummy", null)
                        .withAction(MessageTemplates.Args.CANCEL_ACTION, null)
                        .withAction(MessageTemplates.Args.MAYBE_ACTION, null), new ActionCallback() {

                    @Override
                    public boolean onResponse(final ActionContext context) {
                        LeanplumActivityHelper.queueActionUponActive(new PostponableAction() {
                            @Override
                            public void run() {
                                final Activity activity = LeanplumActivityHelper.getCurrentActivity();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                        activity);
                                alertDialogBuilder
                                        .setTitle(context.stringNamed(MessageTemplates.Args.TITLE))
                                        .setMessage(context.stringNamed(MessageTemplates.Args.MESSAGE))
                                        .setCancelable(false)
                                        .setPositiveButton(context.stringNamed(MessageTemplates.Args.ACCEPT_TEXT), // ACCEPT_TEXT == "Accept text"
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        context.objectNamed(context.getMessageId());
                                                        // We can mimic the LP behavior for runTrackedActionNamed for
                                                        // a period of time - until an app update.
                                                        // The same old action name, but we now track the correct eventName.
                                                        context.trackMessageEvent("Accept action", 0.0, null, null);
                                                        context.runActionNamed("TestingDummy");
                                                    }
                                                })
                                        .setNegativeButton(context.stringNamed(MessageTemplates.Args.CANCEL_TEXT),
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        context.runActionNamed(MessageTemplates.Args.CANCEL_ACTION);
                                                    }
                                                })
                                        .setNeutralButton(context.stringNamed(MessageTemplates.Args.MAYBE_TEXT),
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        context.runActionNamed(MessageTemplates.Args.MAYBE_ACTION);
                                                    }
                                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                if (activity != null && !activity.isFinishing()) {
                                    alertDialog.show();
                                }
                            }
                        });
                        return true;
                    }
                });
    }
}
