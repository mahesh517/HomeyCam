package com.app.homeycam.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.homeycam.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {

    Context context;
    List<String> products;
    private ListPlayerAdapterListener listPlayerAdapterListener;

    public DeviceAdapter(Context context, List<String> products, ListPlayerAdapterListener listPlayerAdapterListener) {
        this.context = context;
        this.listPlayerAdapterListener = listPlayerAdapterListener;
        this.products = products;
    }


    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_adapter, viewGroup, false);

        return new DeviceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {


        holder.guest_name.setText(products.get(position));


    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public class DeviceHolder extends RecyclerView.ViewHolder {

        TextView guest_name;
        ImageView card_iv;

        FrameLayout delete_view;

        TextView default_tv;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);


            guest_name = itemView.findViewById(R.id.device_name);
        }
    }

    public interface ListPlayerAdapterListener {
        void onClickListener(int pos, String user_id);
    }


}
