package com.example.kattyapplication.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kattyapplication.API.APIService;
import com.example.kattyapplication.Adapters.RemindAdapter;
import com.example.kattyapplication.Models.Message;
import com.example.kattyapplication.Models.Remind;
import com.example.kattyapplication.Models.RemindUpload;
import com.example.kattyapplication.R;
import com.example.kattyapplication.Receivers.AlarmReceiver;
import com.example.kattyapplication.Receivers.SwitchButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemindFragmentRecyclerView extends Fragment implements SwitchButton {


    ArrayList<Remind> list;
    SimpleDateFormat simpleDateFormat;
    RecyclerView recyclerView;

    MaterialTimePicker picker;
    static Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    String content;
    TextView tvTime;
    MaterialDatePicker.Builder materialDateBuilder;
    MaterialDatePicker materialDatePicker;
    ProgressBar progressBar;
    RemindAdapter remindAdapter;

//    MaterialDatePicker materialDatePicker = materialDateBuilder.build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remind_fragment_recyclerview, container, false);

//        tvTime = view.findViewById(R.id.tvTimeRecyclerView);

        createNotificationChannel();
        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        calendar = Calendar.getInstance();

        list = new ArrayList<>();

        APIService.apiService.getList().enqueue(new Callback<List<Remind>>() {
            @Override
            public void onResponse(Call<List<Remind>> call, Response<List<Remind>> response) {
                Log.d("Successfully", response.body().size() + "");
                list = (ArrayList<Remind>) response.body();

                remindAdapter = new RemindAdapter(list, RemindFragmentRecyclerView.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView = view.findViewById(R.id.rcvRemind);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(remindAdapter);
                DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                        itemDecorator.VERTICAL));
                progressBar.setVisibility(View.GONE);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String time = list.get(position).getThoiGian();
                        Log.e("Time", time.substring(0, 4) + time.substring(5, 7) + time.substring(8, 10));
                        int year = Integer.parseInt(time.substring(0, 4));
                        int month = Integer.parseInt(time.substring(5, 7)) - 1;
                        int day = Integer.parseInt(time.substring(8, 10));
                        Log.e("Year Month Day", year + month + day + " ");

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                                final Calendar cal = Calendar.getInstance();
//                                Log.e("Calendar finding:", cal.toString());
//                                Date date = Calendar.getInstance().getTime();

                                picker = new MaterialTimePicker.Builder()
                                        .setTimeFormat(TimeFormat.CLOCK_24H)
                                        .setHour(Integer.parseInt(time.substring(11, 13)))
                                        .setMinute(Integer.parseInt(time.substring(14, 16)))
                                        .setTitleText("Chọn Thời Gian")
                                        .build();
//                                Log.e("Hour, Minute", String.valueOf(cal.HOUR) + String.valueOf(cal.MINUTE));

                                picker.show(getParentFragmentManager(), "katty-remind");

                                picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

//                                        Log.d("Hour:", "" + picker.getMinute());

                                        calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                                        calendar.set(Calendar.MINUTE, picker.getMinute());
                                        calendar.set(Calendar.SECOND, 0);
                                        calendar.set(Calendar.MILLISECOND, 0);
                                        Log.d("Calendar:", "" + calendar.toString());

                                        AlertDialog.Builder alerDialog = new AlertDialog.Builder(getContext());
                                        alerDialog.setTitle("Muốn Katty Nhắc Gì Nè.");
                                        EditText edtContent = new EditText(getContext());
                                        edtContent.setText(list.get(position).getNoiDung());
                                        edtContent.setInputType(InputType.TYPE_CLASS_TEXT);
                                        alerDialog.setView(edtContent);
                                        alerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                content = edtContent.getText().toString();

                                                String time = simpleDateFormat.format(calendar.getTime());
                                                String timeAPI = time.replace(" ", "T");

                                                Log.e("Remind content", content);
                                                Remind remind = new Remind(list.get(position).getId(), timeAPI, content, 1);
                                                APIService.apiService.changeRemind(remind).enqueue(new Callback<Message>() {
                                                    @Override
                                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                                        Log.e("Update", response.body().toString());

                                                        list.set(position, remind);
                                                        remindAdapter.notifyItemChanged(position);

                                                        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                                                        Intent intentUpdate = new Intent(getContext(), AlarmReceiver.class);
                                                        Log.e("Content Update", content);
                                                        intentUpdate.putExtra("content", content);
                                                        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
                                                        pendingIntent = PendingIntent.getBroadcast(getContext(), iUniqueId, intentUpdate, PendingIntent.FLAG_IMMUTABLE);
                                                        Log.e("calendar", simpleDateFormat.format(calendar.getTime()));
                                                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                                                        Toast.makeText(getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Message> call, Throwable t) {
                                                        Log.e("Update", t.toString());
                                                    }
                                                });


                                            }
                                        });
                                        alerDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        alerDialog.show();
                                    }
                                });

                            }
                        }, year, month, day);
                        datePickerDialog.setTitle("Chọn Ngày");
                        datePickerDialog.show();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
                        deleteDialog.setTitle("Bạn có chắc muốn xóa không?");
                        deleteDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int pos = list.get(position).getId();
                                progressBar.setVisibility(View.VISIBLE);

                                APIService.apiService.deleteRemind(pos).enqueue(new Callback<Message>() {
                                    @Override
                                    public void onResponse(Call<Message> call, Response<Message> response) {
                                        Log.d("Xoa:", String.valueOf(response.body().getId()));
                                        Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();

                                        list.remove(position);
                                        remindAdapter.notifyItemRemoved(position);

                                        cancelAlarm();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onFailure(Call<Message> call, Throwable t) {
                                        Log.d("Xoa:", t.toString());
                                        Toast.makeText(getContext(), "Khong xoa xóa duoc!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        deleteDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        deleteDialog.show();
                    }
                }));
            }

            @Override
            public void onFailure(Call<List<Remind>> call, Throwable t) {
                Log.d("Got Error", t.toString());
            }
        });

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        return view;
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }


    private void showTimePicker() {
        Calendar cal = Calendar.getInstance();
        Log.e("Calendar finding:", cal.toString());
        Date date = Calendar.getInstance().getTime();

        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(cal.getTime().getHours())
                .setMinute(cal.getTime().getMinutes())
                .setTitleText("Chọn Thời Gian")
                .build();
        Log.e("Hour, Minute", String.valueOf(cal.HOUR) + String.valueOf(cal.MINUTE));

        picker.show(getParentFragmentManager(), "katty-remind");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Hour:", "" + picker.getMinute());
//                tvTime.setText(picker.getHour() + " : " + picker.getMinute());

//                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, picker.getHour());
                calendar.set(Calendar.MINUTE, picker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Log.d("Calendar:", "" + calendar.toString());

                showContentTextView();
            }
        });

    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimePicker();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("Chọn Ngày");
        datePickerDialog.show();

    }

    public void showContentTextView() {
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(getContext());
        alerDialog.setTitle("Muốn Katty Nhắc Gì Nè.");
        EditText edtContent = new EditText(getContext());
        edtContent.setInputType(InputType.TYPE_CLASS_TEXT);
        alerDialog.setView(edtContent);
        alerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                content = edtContent.getText().toString();

                setAlarm();

            }
        });
        alerDialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alerDialog.show();
    }

    public void setAlarm() {
        String time = simpleDateFormat.format(calendar.getTime());
        String timeAPI = time.replace(" ", "T");

        Remind remind = new Remind(timeAPI, content, 1);
        Log.e("Remind", remind.toString());
        APIService.apiService.addRemind2(remind).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Log.d("Thanh cong:", response.body().getId() + "");
                list.add(remind);
                remindAdapter.notifyItemInserted(list.size());

                alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                intent.putExtra("content", content);
                int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
                pendingIntent = PendingIntent.getBroadcast(getContext(), iUniqueId, intent, PendingIntent.FLAG_IMMUTABLE);
                Log.e("calendar", simpleDateFormat.format(calendar.getTime()));
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                Toast.makeText(getContext(), "Thiết Lập Thành Công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("KHong duoc", t.toString());
            }
        });


    }

    public void setAlarmForSwitchButton() {

        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("content", content);
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        pendingIntent = PendingIntent.getBroadcast(getContext(), iUniqueId, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm() {

        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);
        pendingIntent = PendingIntent.getBroadcast(getContext(), iUniqueId, intent, PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
//        Toast.makeText(getContext(), "Đã Hủy Nhắc Nhở", Toast.LENGTH_SHORT).show();
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
