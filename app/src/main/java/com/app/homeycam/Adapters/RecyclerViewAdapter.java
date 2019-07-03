package com.app.homeycam.Adapters;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.app.homeycam.R;
import com.app.homeycam.Rawheaders.Settings.ItemType;
import com.app.homeycam.Rawheaders.Settings.Model;
import com.app.homeycam.Utils.LoginPrefManager;
import com.github.nkzawa.socketio.client.Socket;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;
import com.suke.widget.SwitchButton;

import java.util.Calendar;

public class RecyclerViewAdapter extends ListAdapter<Model, RecyclerView.ViewHolder> implements KmStickyListener {


    SettingInterface settingInterface;

    Context context;

    Socket local_socket;
    LoginPrefManager loginPrefManager;

    String recording_start, recording_end, status;

    public RecyclerViewAdapter(Context context, Socket local_socket, String recording_start, String recording_end, String status, SettingInterface settingInterface) {
        super(ModelDiffUtilCallback);
        this.context = context;
        this.local_socket = local_socket;
        this.recording_start = recording_start;
        this.recording_end = recording_end;
        this.status = status;
        this.settingInterface = settingInterface;
        loginPrefManager = new LoginPrefManager(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header, viewGroup, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == ItemType.Post) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
            return new PostViewHolder(itemView);
        } else if (viewType == ItemType.Normal) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_normal, viewGroup, false);
            return new NormalViewHolder(itemView);
        } else if (viewType == ItemType.Alerts) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_view_setting, viewGroup, false);
            return new AlertView(itemView);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_last, viewGroup, false);
            return new EmptyViewHodler(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == ItemType.Header) {
            ((HeaderViewHolder) viewHolder).bind(getItem(i));
        } else if (getItemViewType(i) == ItemType.Post) {
            ((PostViewHolder) viewHolder).bind(getItem(i));
        } else if (getItemViewType(i) == ItemType.Normal) {
            ((NormalViewHolder) viewHolder).bind(getItem(i));
        } else if (getItemViewType(i) == ItemType.Alerts) {
            ((AlertView) viewHolder).bind(getItem(i));
        } else {
            ((EmptyViewHodler) viewHolder).bind(getItem(i));
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(Model model) {
            title.setText(model.title);

        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public NormalViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(Model model) {
            title.setText(model.title);
        }
    }

    class EmptyViewHodler extends RecyclerView.ViewHolder {
        public TextView title;

        public EmptyViewHodler(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Model model) {
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_post);
        }

        public void bind(Model model) {
            title.setText(model.title);
            int position = getAdapterPosition();

            if (model.type != 1) {
                title.setVisibility(View.GONE);
            }

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (settingInterface != null) {
                        settingInterface.onClick(title.getText().toString());
                    }
                }
            });

        }
    }

    class AlertView extends RecyclerView.ViewHolder {
        public TextView from_time, to_time;
        public LinearLayout from_layout, to_layout;
        public SwitchButton switchCompat;


        public AlertView(@NonNull View itemView) {
            super(itemView);
            from_time = itemView.findViewById(R.id.from_time);
            to_time = itemView.findViewById(R.id.to_time);
            from_layout = itemView.findViewById(R.id.from_layout);
            to_layout = itemView.findViewById(R.id.to_layout);
            switchCompat = itemView.findViewById(R.id.switch_button);

        }

        public void bind(Model model) {
            int position = getAdapterPosition();

            from_time.setText(recording_start);
            to_time.setText(recording_end);

            if (status.equalsIgnoreCase("on")) {
                switchCompat.setChecked(true);
            }


            from_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTImerPicker(from_time);
                }
            });
            to_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTImerPicker(to_time);
                }
            });


        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public Integer getHeaderPositionForItem(Integer itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public Integer getHeaderLayout(Integer headerPosition) {
        return R.layout.item_header;
    }

    @Override
    public void bindHeaderData(View header, Integer headerPosition) {
        TextView tv = header.findViewById(R.id.title_header);
        tv.setText(getItem(headerPosition).title);
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).type.equals(ItemType.Header);
    }

    public static final DiffUtil.ItemCallback<Model> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<Model>() {
                @Override
                public boolean areItemsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.title.equals(t1.title);
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Model model, @NonNull Model t1) {
                    return model.equals(t1);
                }
            };


    public interface SettingInterface {
        void onClick(String name);
    }

    private void showTImerPicker(TextView textView) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }


    public void  updateSwitch(){


    }

}
