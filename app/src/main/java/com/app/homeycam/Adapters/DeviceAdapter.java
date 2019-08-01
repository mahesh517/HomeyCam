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

import com.app.homeycam.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceHolder> {

    Context context;
    List<String> products;
    private DeviceAdapterInterface deviceAdapterInterface;

    public DeviceAdapter(Context context, List<String> products, DeviceAdapterInterface deviceAdapterInterface) {
        this.context = context;
        this.deviceAdapterInterface = deviceAdapterInterface;
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

        holder.change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (deviceAdapterInterface != null) {
                    deviceAdapterInterface.onClickListener(1, position);
                }
            }
        });

        holder.change_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deviceAdapterInterface != null) {
                    deviceAdapterInterface.onClickListener(2, position);
                }
            }
        });
        holder.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deviceAdapterInterface != null) {
                    deviceAdapterInterface.onClickListener(3, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return products.size();
    }

    public class DeviceHolder extends RecyclerView.ViewHolder {

        TextView guest_name, change_name, change_wifi, reset;
        ImageView card_iv;

        FrameLayout delete_view;

        TextView default_tv;

        public DeviceHolder(@NonNull View itemView) {
            super(itemView);


            guest_name = itemView.findViewById(R.id.device_name);
            change_name = itemView.findViewById(R.id.change_name);
            change_wifi = itemView.findViewById(R.id.change_wifi);
            reset = itemView.findViewById(R.id.reset);
        }
    }

    public interface DeviceAdapterInterface {
        void onClickListener(int pos, int user_id);
    }


}
