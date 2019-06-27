package com.app.homeycam.CustomDailogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.app.homeycam.Adapters.NameAdapter;
import com.app.homeycam.R;

import java.util.List;

public class AddMemberDialog extends Dialog {

    Context context;

    TextView add_member, cancel;

    List<String> names;

    RecyclerView exist_users_view;

    MemberInterface memberInterface;
    NameAdapter nameAdapter;

    public AddMemberDialog(Context context, int appTheme, List<String> names, MemberInterface memberInterface) {
        super(context, appTheme);
        this.context = context;
        this.names = names;
        this.memberInterface = memberInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.dialog_traspe)));
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.memeber_add_dailog);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        super.onCreate(savedInstanceState);


        add_member = findViewById(R.id.add_member);
        exist_users_view = findViewById(R.id.name_view);

        exist_users_view.setLayoutManager(new LinearLayoutManager(context));
        exist_users_view.setHasFixedSize(true);


        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (memberInterface != null) {
                    memberInterface.onNameAdded(0, "");
                }
            }
        });


        nameAdapter = new NameAdapter(context, new NameAdapter.Nameinterface() {
            @Override
            public void onExistUserSelected(boolean status, String name) {
                if (memberInterface != null) {
                    memberInterface.onNameAdded(1, name);
                }
            }
        }, names);

        exist_users_view.setAdapter(nameAdapter);
        cancel = findViewById(R.id.btn_cancel);
    }


    public interface MemberInterface {
        void onNameAdded(int position, String name);
    }
}
