package com.app.homeycam.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;


public interface ActivityHelper extends AppConstants {

    void hideKeyboard(View view);

    Typeface createTypeFace(String fontName);

    Typeface createTypeFace(Context context, String fontName);

    Drawable createRepeatableDrawable(int imageId);

    boolean isNetworkAvailable(Activity activity);

    void switchToActivity(Activity current,
                          Class<? extends Activity> otherActivityClass,
                          Bundle extras);

    void goToActivity(Activity current,
                      Class<? extends Activity> otherActivityClass,
                      Bundle extras);

    void initUI();

    void setListeners();

}
