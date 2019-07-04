package com.app.homeycam.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.homeycam.ModelClass.GuestUserApi.User;
import com.app.homeycam.R;

import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.CardsViewHodler> {

    Context context;
    List<User> users;
    private ListPlayerAdapterListener listPlayerAdapterListener;

    public GuestAdapter(Context context, List<User> users, ListPlayerAdapterListener listPlayerAdapterListener) {
        this.context = context;
        this.listPlayerAdapterListener = listPlayerAdapterListener;
        this.users = users;
    }


    @NonNull
    @Override
    public CardsViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.guest_adapter, viewGroup, false);

        return new CardsViewHodler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsViewHodler holder, int position) {


        holder.guest_name.setText(users.get(position).getEmail());

        holder.delete_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listPlayerAdapterListener != null) {

                    listPlayerAdapterListener.onClickListener(position, users.get(position).getId());


                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return users.size();
    }

    public class CardsViewHodler extends RecyclerView.ViewHolder {

        TextView guest_name;
        ImageView card_iv;

        FrameLayout delete_view;

        TextView default_tv;

        public CardsViewHodler(@NonNull View itemView) {
            super(itemView);

            delete_view = itemView.findViewById(R.id.delete_layout);

            guest_name = itemView.findViewById(R.id.name);
        }
    }

    public interface ListPlayerAdapterListener {
        void onClickListener(int pos, String user_id);
    }


}
