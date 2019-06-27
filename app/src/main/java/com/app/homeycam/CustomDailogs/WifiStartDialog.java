package com.app.homeycam.CustomDailogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.homeycam.R;

public class WifiStartDialog extends Dialog {

    Context context;

    TextView submit_tv;

    AlertInterface alertInterface;

    public WifiStartDialog(Context context, int appTheme, AlertInterface alertInterface) {
        super(context, appTheme);
        this.context = context;
        this.alertInterface = alertInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.second_view_alert);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        super.onCreate(savedInstanceState);


        submit_tv = findViewById(R.id.submit_txt);


        submit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertInterface != null) {
                    alertInterface.onSubmit(true);
                }
            }
        });
    }


    public interface AlertInterface {
        void onSubmit(boolean status);
    }
}
