package com.app.homeycam.CustomDailogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.app.homeycam.R;

public class ChangeDeviceNameDialog extends Dialog {

    Context context;

    TextView submit, cancle;

    EditText name_et;

    DeviceNameInterface deviceNameInterface;

    public ChangeDeviceNameDialog(@NonNull Context context, int themeResId, DeviceNameInterface deviceNameInterface) {
        super(context, themeResId);

        this.context = context;
        this.deviceNameInterface = deviceNameInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.devicename_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        super.onCreate(savedInstanceState);

        name_et = findViewById(R.id.new_device);
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancel);

        onClick();

    }

    private void onClick() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validName()) {
                    return;
                }

                if (deviceNameInterface != null) {
                    deviceNameInterface.onName(name_et.getText().toString());
                }


                dismiss();
            }
        });


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private boolean validName() {

        if (name_et.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

    public interface DeviceNameInterface {
        void onName(String name);
    }

}
