package com.app.homeycam.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class InputUtils {

	public static void hideKeyboard(Context activity, View view) {

		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

		if (imm != null) {

			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

    public static void showKeyboard(Context context){

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

	public static Drawable createRepeatableDrawable(Activity activity, int imageId) {

		return null;
	}
}
