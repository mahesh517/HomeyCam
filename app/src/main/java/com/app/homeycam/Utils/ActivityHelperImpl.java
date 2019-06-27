package com.app.homeycam.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;



public class ActivityHelperImpl implements ActivityHelper {

    Context activity;

    public ActivityHelperImpl(Context activity) {

        this.activity = activity;
    }

    @Override
    public void hideKeyboard(View view) {

        InputUtils.hideKeyboard(activity, view);
    }

    @Override
    public Typeface createTypeFace(String fontName) {

        return UiUtils.createTypeFace(activity, fontName);
    }

    @Override
    public Typeface createTypeFace(Context context, String fontName) {
        return UiUtils.createTypeFace(context, fontName);
    }

    @Override
    public Drawable createRepeatableDrawable(int imageId) {
        return null;
    }

    @Override
    public boolean isNetworkAvailable(Activity activity) {

        return NetworkUtils.isNetworkAvailable(activity);
    }

    @Override
    public void switchToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {

        UiUtils.switchToActivity(current, otherActivityClass, extras);

    }

    @Override
    public void goToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {

        UiUtils.goToActivity(current, otherActivityClass, extras);
    }

    @Override
    public void initUI() {

    }

    @Override
    public void setListeners() {

    }

}
