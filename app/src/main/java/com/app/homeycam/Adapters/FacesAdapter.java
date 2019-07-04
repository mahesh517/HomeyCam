package com.app.homeycam.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class FacesAdapter extends RecyclerView.Adapter<FacesAdapter.MyViewHolder> {

    Context context;
    JSONArray jsonArray;

    FaceInterface faceInterface;
    private SimpleDateFormat UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    private SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.ENGLISH);
    private SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);
    private SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    int type;

    public FacesAdapter(Context context, int type, JSONArray jsonArray, FaceInterface faceInterface) {
        this.context = context;
        this.jsonArray = jsonArray;
        this.faceInterface = faceInterface;
        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.face_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        if (type == 1) {
            myViewHolder.main_view.setOrientation(LinearLayout.VERTICAL);
        } else {
            myViewHolder.main_view.setOrientation(LinearLayout.HORIZONTAL);
        }

        JSONObject innerObj = null;
        try {
            innerObj = jsonArray.getJSONObject(i);

            JSONArray jsonObject1 = innerObj.getJSONArray("image_selected_check");

            String event_time = innerObj.getString("last_seen");
            String user_name = null;
            if (innerObj.has("person_name")) {
                user_name = innerObj.getString("person_name");

                myViewHolder.name.setText(user_name);

                myViewHolder.help.setVisibility(View.GONE);

                myViewHolder.name.setTextColor(Color.BLACK);
            }
            if (!innerObj.has("person_name")) {

                myViewHolder.help.setVisibility(View.VISIBLE);
                myViewHolder.name.setText("EMPTY");
                myViewHolder.name.setTextColor(Color.WHITE);
            }

            myViewHolder.date.setText(changeUtcDateForamat(event_time));
            if (jsonObject1 != null) {
                JSONObject image_object = jsonObject1.getJSONObject(0);
                String img_name = image_object.getString("file_name");
                String img_url = APIServiceFactory.IMAGE_URL + img_name;
                Glide.with(context).load(img_url).into(myViewHolder.imageView);
            }

            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    JSONObject innerObj = null;
                    try {
                        innerObj = jsonArray.getJSONObject(i);

//                        Log.e("inner", innerObj.toString());

                        String id = innerObj.getString("_id");
                        if (innerObj.has("person_name")) {
                            if (faceInterface != null) {


                                faceInterface.onFaceclicked(true, id, innerObj.toString());
                            }
                        } else {
                            if (faceInterface != null) {
                                faceInterface.onFaceclicked(false, id, innerObj.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        CircleImageView imageView;

        ImageView help;

        TextView name, date;

        LinearLayout main_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_pic);

            name = itemView.findViewById(R.id.name);
            help = itemView.findViewById(R.id.help);
            main_view = itemView.findViewById(R.id.main_view);


            date = itemView.findViewById(R.id.date);
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


    public interface FaceInterface {
        void onFaceclicked(boolean name_status, String id, String jsonObject);
    }


}
