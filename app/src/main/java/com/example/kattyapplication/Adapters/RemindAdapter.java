package com.example.kattyapplication.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kattyapplication.API.APIService;
import com.example.kattyapplication.Models.Message;
import com.example.kattyapplication.Models.Remind;
import com.example.kattyapplication.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.MyViewHolder> {

    private ArrayList<Remind> list, list2;

    public RemindAdapter(ArrayList<Remind> list) {
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;
        private TextView tvContentRemind;
        private TextView tvDate;
        private Switch swt;

        public MyViewHolder (final View view){
            super(view);
            tvTime = view.findViewById(R.id.tvTime);
            tvContentRemind = view.findViewById(R.id.tvContentRemind);
            tvDate = view.findViewById(R.id.tvDate);
            swt = view.findViewById(R.id.swt);

        }
    }

    @NonNull
    @Override
    public RemindAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remind_item_recyclerview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemindAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        Date time = list.get(position).getThoiGian();
        String time = list.get(position).getThoiGian();
        //2020-12-12T12:12
        String timeFormat = time.substring(11, 16);
        String contentRemind = list.get(position).getNoiDung();
        String dateFormat = time.substring(0,10);
        Boolean ischecked;
        if(list.get(position).getTrangThai() == 0){
            ischecked = false;
        }else{
            ischecked = true;
        }
        holder.tvTime.setText(timeFormat);
        holder.tvDate.setText(dateFormat);
        holder.tvContentRemind.setText(contentRemind);
        holder.swt.setChecked(ischecked);
        holder.swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                list2 = new ArrayList();
                APIService.apiService.getList().enqueue(new Callback<List<Remind>>() {
                    @Override
                    public void onResponse(Call<List<Remind>> call, Response<List<Remind>> response) {
                        list2 = (ArrayList<Remind>) response.body();
                    }

                    @Override
                    public void onFailure(Call<List<Remind>> call, Throwable t) {

                    }
                });

                if(holder.swt.isChecked() == false){
                    Remind remind = list.get(position);
                    remind.setTrangThai(0);
                    APIService.apiService.changeRemind(remind).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Log.e("Update thanh cong", response.body().getId() + "");

                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Log.d("Update that bai", t.toString());
                        }
                    });
                }else{
                    Remind remind = list.get(position);
                    Log.d("Remind", list.get(position).toString());
                    remind.setTrangThai(1);
                    APIService.apiService.changeRemind(remind).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Log.e("Update thanh cong", response.body().getId() + "");

                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Log.d("Update that bai", t.toString());
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
