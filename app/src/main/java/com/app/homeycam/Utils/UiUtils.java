package com.app.homeycam.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;



public class UiUtils {

    public static void switchToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {
        Intent intent = new Intent(current, otherActivityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        current.startActivity(intent);
        current.finish();
    }

    public static void goToActivity(Activity current, Class<? extends Activity> otherActivityClass, Bundle extras) {
        Intent intent = new Intent(current, otherActivityClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        current.startActivity(intent);
    }


    public static Typeface createTypeFace(Context context, String fontName) {
        Typeface tf = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/" + fontName);
        return tf;
    }



    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getApplicationContext()
                .getResources().getDisplayMetrics();
        int px = Math.round(dp
                * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }
}
