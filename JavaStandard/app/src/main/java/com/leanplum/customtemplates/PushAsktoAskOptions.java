package com.leanplum.customtemplates;

import android.content.Context;
import android.graphics.Color;
import com.leanplum.ActionArgs;
import com.leanplum.ActionContext;
import static com.leanplum.customtemplates.MessageTemplates.getApplicationName;

/**
 * Created by sashohadzhiev on 5/14/16.
 */
public class PushAsktoAskOptions extends BaseMessageOptions {

    protected PushAsktoAskOptions(ActionContext context) {
        super(context);
    }

    public static ActionArgs toArgs(Context currentContext) {
        return BaseMessageOptions.toArgs(currentContext)
                .with(MessageTemplates.Args.TITLE_TEXT, getApplicationName(currentContext))
                .with(MessageTemplates.Args.MESSAGE_TEXT, MessageTemplates.Values.PUSH_ASK_TO_ASK_MESSAGE)
                .with(MessageTemplates.Args.CANCEL_BUTTON_TEXT, MessageTemplates.Values.MAYBE_TEXT)
                .withColor(MessageTemplates.Args.CANCEL_BUTTON_BACKGROUND_COLOR, Color.WHITE)
                .withColor(MessageTemplates.Args.CANCEL_BUTTON_TEXT_COLOR, Color.GRAY)
                .with(MessageTemplates.Args.LAYOUT_WIDTH, MessageTemplates.Values.PUSH_ASK_WIDTH)
                .with(MessageTemplates.Args.LAYOUT_HEIGHT, MessageTemplates.Values.PUSH_ASK_HEIGHT)
                .withAction(MessageTemplates.Args.CANCEL_ACTION, null);
    }
}