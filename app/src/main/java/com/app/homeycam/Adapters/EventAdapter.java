package com.app.homeycam.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.homeycam.R;
import com.app.homeycam.ServiceApi.APIServiceFactory;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private JSONArray jsonArray;
    private Context context;

    private SimpleDateFormat UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    private SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.ENGLISH);
    private SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
    private SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);

    String selected_type;

    EventsInterface eventsInterface;


    public EventAdapter(Context context, JSONArray jsonArray, EventsInterface eventsInterface) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.eventsInterface = eventsInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventadpater, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        try {
            JSONObject events = jsonArray.getJSONObject(position);


            String event_name = events.getString("event_name");
            String event_time = null;

            myViewHolder.event_name_tv.setText(event_name);

            if (events.has("createdAt")) {
                event_time = events.getString("createdAt");
            }
            String device_type = events.getString("event_type");
            JSONObject homeimage_id = null;

            if (events.has("homeimage_id")) {
                homeimage_id = events.getJSONObject("homeimage_id");
            }
            String read_status = events.getString("read_status");


            if (read_status.equalsIgnoreCase("unread")) {
                myViewHolder.read_iv.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.read_iv.setVisibility(View.GONE);
            }

            if (event_time != null && !event_time.equalsIgnoreCase("")) {
                myViewHolder.event_time_tv.setText(changeUtcDateForamat(event_time));
            }


            if (device_type.equalsIgnoreCase("device")) {
                if (event_name.contains("Connected")) {
                    myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.wifi_connect));
                } else if (event_name.contains("Disconnected")) {
                    myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.wifi_disconnect));
                }
            } else if (device_type.equalsIgnoreCase("motion")) {
                String image = events.getString("file_name");
                myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.motion_icon));

//                if (image != null) {
//                    String img_url = APIServiceFactory.IMAGE_URL + image;
//
//                    Log.e("image", img_url);
////                    Glide.with(context).load(img_url).into(myViewHolder.user_iv);
//
//                    myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.motion_icon));
//                } else {
//                    myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.motion_icon));
//                }
            } else {

                if (homeimage_id != null) {
                    JSONArray image_array = null;
                    if (homeimage_id.has("image_details")) {
                        image_array = homeimage_id.getJSONArray("image_details");
                    }
                    if (image_array != null) {
                        JSONObject image_object = image_array.getJSONObject(0);
                        String img_name = image_object.getString("file_name");
                        String img_url = APIServiceFactory.IMAGE_URL + img_name;
                        Glide.with(context).load(img_url).into(myViewHolder.user_iv);
                    }

                } else {

                    String image = events.getString("file_name");
                    if (image != null) {
                        String img_url = APIServiceFactory.IMAGE_URL + image;
                        Glide.with(context).load(img_url).into(myViewHolder.user_iv);
                    } else {
                        myViewHolder.user_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.user_face));
                    }


                }
            }


        } catch (JSONException e) {

            Log.e("JSONException", e.getMessage());

            try {

                Log.e("json", jsonArray.getJSONObject(position).toString());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventsInterface != null) {
                    try {
                        eventsInterface.onEventSelect(jsonArray.getJSONObject(position));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {

        return jsonArray.length();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView user_iv, read_iv;
        TextView event_name_tv, event_time_tv;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            user_iv = itemView.findViewById(R.id.user_pic);
            event_name_tv = itemView.findViewById(R.id.event_name);
            event_time_tv = itemView.findViewById(R.id.event_time);
            read_iv = itemView.findViewById(R.id.read);

        }
    }

    private String changeUtcDateForamat(String old_date) {

        String new_date = null;
        String convert_date;
        String time;
        Date date, server_date;

        Date currnet_date = new Date();

        String convert_current_day = DATE_ONLY_FORMAT.format(currnet_date);

        try {
            UTC_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = UTC_FORMAT.parse(old_date);

            DEFAULT_DATE_FORMAT.setTimeZone(TimeZone.getDefault());
            new_date = DEFAULT_DATE_FORMAT.format(date);

            time = DEFAULT_TIME_FORMAT.format(date.getTime());


            convert_date = DATE_ONLY_FORMAT.format(date);


            server_date = DATE_ONLY_FORMAT.parse(convert_date);

            currnet_date = DATE_ONLY_FORMAT.parse(convert_current_day);


            if (currnet_date.equals(server_date)) {
                new_date = "Today" + " " + time;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new_date;
    }

    public interface EventsInterface {
        void onEventSelect(JSONObject jsonObject);
    }

}
