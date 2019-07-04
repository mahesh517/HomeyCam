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

public class ChangePasswordDialog extends Dialog {


    Context context;
    PasswordInterface passwordInterface;
    TextView submit, cancle;
    EditText old_password, new_password;

    public ChangePasswordDialog(Context context, int themeResId, PasswordInterface passwordInterface) {
        super(context, themeResId);
        this.passwordInterface = passwordInterface;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.password_dialog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        super.onCreate(savedInstanceState);

        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancel);

        onClick();

    }

    private void onClick() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validPassword()) {
                    return;
                }
                if (passwordInterface != null) {
                    passwordInterface.onItemClick(true, old_password.getText().toString(), new_password.getText().toString());
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

    private boolean validPassword() {

        if (old_password.getText().toString().equalsIgnoreCase("")) {
            return false;
        } else if (new_password.getText().toString().equalsIgnoreCase("")) {
            return false;
        }

        return true;
    }

    public interface PasswordInterface {
        void onItemClick(boolean status, String old, String newPass);
    }
}
