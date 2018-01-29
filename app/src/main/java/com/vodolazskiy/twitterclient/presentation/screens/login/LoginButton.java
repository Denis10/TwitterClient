package com.vodolazskiy.twitterclient.presentation.screens.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.vodolazskiy.twitterclient.R;

public class LoginButton extends android.support.v7.widget.AppCompatButton {
    static final String TAG = LoginButton.class.getName();
    static final String ERROR_MSG_NO_ACTIVITY = "TwitterLoginButton requires an activity."
            + " Override getActivity to provide the activity for this button.";

    volatile TwitterAuthClient authClient;

    public LoginButton(Context context) {
        this(context, null);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.buttonStyle);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, null);
    }

    LoginButton(Context context, AttributeSet attrs, int defStyle,
                TwitterAuthClient authClient) {
        super(context, attrs, defStyle);
        this.authClient = authClient;
        setupButton();

        checkTwitterCoreAndEnable();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupButton() {
        final Resources res = getResources();
        super.setCompoundDrawablesWithIntrinsicBounds(
                res.getDrawable(R.drawable.tw__ic_logo_default), null, null, null);
        super.setCompoundDrawablePadding(
                res.getDimensionPixelSize(R.dimen.tw__login_btn_drawable_padding));
        super.setText(R.string.tw__login_btn_txt);
        super.setTextColor(res.getColor(R.color.tw__solid_white));
        super.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                res.getDimensionPixelSize(R.dimen.tw__login_btn_text_size));
        super.setTypeface(Typeface.DEFAULT_BOLD);
        super.setPadding(res.getDimensionPixelSize(R.dimen.tw__login_btn_left_padding), 0,
                res.getDimensionPixelSize(R.dimen.tw__login_btn_right_padding), 0);
        super.setBackgroundResource(R.drawable.tw__login_btn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.setAllCaps(false);
        }
    }

    private void checkTwitterCoreAndEnable() {
        //Default (Enabled) in edit mode
        if (isInEditMode()) return;

        try {
            TwitterCore.getInstance();
        } catch (IllegalStateException ex) {
            //Disable if TwitterCore hasn't started
            Twitter.getLogger().e(TAG, ex.getMessage());
            setEnabled(false);
        }
    }
}
