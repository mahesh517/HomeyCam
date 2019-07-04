package com.app.homeycam.CustomDailogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.homeycam.R;


public class NewNameDialog extends Dialog {

    private Context context;
    CancelInterface cancelInterface;

    EditText email;
    TextView cancel, update, header;

    int type;

    public NewNameDialog(@NonNull Context context, int themeResId, int type, CancelInterface cancelInterface) {
        super(context, themeResId);
        this.context = context;
        this.cancelInterface = cancelInterface;

        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        setContentView(R.layout.add_newname);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        cancel = findViewById(R.id.cancel);
        update = findViewById(R.id.submit);
        email = findViewById(R.id.email);
        header = findViewById(R.id.header);


        if (type == 2) {

            header.setText("Please type in E-mail you want to invite");
            email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            email.setHint("E-mail");
        }

        onClickevetns();


    }

    private void onClickevetns() {

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validEmail(email)) {
                    return;
                }
                if (type == 2) {
                    if (!isValidEmail(email.getText().toString())) {
                        return;
                    }
                }

                if (cancelInterface != null) {
                    cancelInterface.buttonselected(true, email.getText().toString());
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cancelInterface != null) {
                    cancelInterface.buttonselected(false, "");
                }
            }
        });
    }


    public interface CancelInterface {
        void buttonselected(boolean status, String msg);
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validEmail(EditText email_edit) {

        String email = email_edit.getText().toString();

        if (email.equalsIgnoreCase("")) {

            showShortMessage(context.getString(R.string.error_valid_message));

//            email_edit.setError(getString(R.string.error_email));
//            email_edit.requestFocus();

            return false;
        } else {
            email_edit.setError(null);
        }

        return true;
    }

    public void showShortMessage(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
