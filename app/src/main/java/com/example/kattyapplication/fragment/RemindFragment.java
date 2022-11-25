package com.example.kattyapplication.fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kattyapplication.R;
import com.example.kattyapplication.Receivers.AlarmReceiver;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;


public class RemindFragment extends Fragment {

    MaterialTimePicker picker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    EditText edtContentRemind;
    public static final String LAST_TEXT = "";
    TextView tvTime;
    Button btnSetAlarm, btnCancelAlarm;
    String content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remind_fragment, container, false);

        createNotificationChannel();

        edtContentRemind = view.findViewById(R.id.edtContentRemind);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        edtContentRemind.setText(pref.getString(LAST_TEXT, ""));
        edtContentRemind.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                content = edtContentRemind.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                content = edtContentRemind.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                pref.edit().putString(LAST_TEXT, s.toString()).commit();
                content = edtContentRemind.getText().toString();

            }
        });

        tvTime = view.findViewById(R.id.tvTime);
        btnSetAlarm = view.findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = view.findViewById(R.id.btnCancelAlarm);

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        return view;
    }

//    public static String sendContent() {
//        return content;
//    }

    private void cancelAlarm() {

        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(getContext(), "Đã Hủy Nhắc Nhở", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm() {

        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("content", content);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(getContext(), "Thiết Lập Thành Công", Toast.LENGTH_SHORT).show();
    }

    private void showTimePicker() {

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Chọn Thời Gian")
                .build();

        picker.show(getParentFragmentManager(), "katty-remind");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvTime.setText(picker.getHour() + " : " + picker.getMinute());

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Katty_Channel";
            String description = "Katty Channel Disciption";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Katty_Remind", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
