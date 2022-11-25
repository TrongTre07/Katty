package com.example.kattyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kattyapplication.R;
import com.example.kattyapplication.fragment.SpendFragment;
import com.example.kattyapplication.model.Spend;
import com.example.kattyapplication.model.TieuDung;

import java.util.ArrayList;

public class SpendAdapter extends ArrayAdapter<Spend> {
    private Context context;
    SpendFragment fragment;
    private ArrayList<Spend> list;
    private ArrayList<TieuDung> listTD;
    TextView tvLoaiTD, tvGiatien, tvTenTC;
    ImageView ivDelete;


    public SpendAdapter(@NonNull Context context, SpendFragment fragment, ArrayList<Spend> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spend_item, null);
        }
        final Spend item = list.get(position);
        if(item != null){
            tvLoaiTD = view.findViewById(R.id.tvLoaiTD);
            tvLoaiTD.setText("Loại tiêu dùng : "+item.getLoaiTieuDung());

            tvGiatien = view.findViewById(R.id.tvGiatien);
            tvGiatien.setText("Giá tiền : "+item.getGiaTien());

            tvTenTC = view.findViewById(R.id.tvTenTC);
            tvTenTC.setText("Tên Pet: "+item.getTenThuCung());

        }



        return view;
    }
}
