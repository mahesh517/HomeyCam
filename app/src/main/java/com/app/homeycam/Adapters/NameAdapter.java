package com.app.homeycam.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.homeycam.R;

import java.util.List;

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.MyView> {

    Context context;
    Nameinterface nameinterface;
    List<String> name_list;

    public NameAdapter(Context context, Nameinterface nameinterface, List<String> name_list) {
        this.context = context;
        this.nameinterface = nameinterface;
        this.name_list = name_list;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.name_adapter, viewGroup, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView myView, int i) {

        myView.name.setText(name_list.get(i));

        myView.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameinterface != null) {
                    nameinterface.onExistUserSelected(true, name_list.get(i));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return name_list.size();
    }


    public class MyView extends RecyclerView.ViewHolder {

        TextView name;

        public MyView(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.user_name);
        }
    }

    public interface Nameinterface {
        void onExistUserSelected(boolean status, String name);
    }

}
