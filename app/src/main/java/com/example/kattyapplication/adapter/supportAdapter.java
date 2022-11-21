package com.example.kattyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kattyapplication.R;
import com.example.kattyapplication.fragment.SupportFragment;
import com.example.kattyapplication.model.support;

import java.util.ArrayList;

public class supportAdapter extends ArrayAdapter<support> {
    private Context context;
    SupportFragment fragment;
    private ArrayList<support> list;
    TextView tvTenBenh, tvNoiDung;

    public supportAdapter(@NonNull Context context, SupportFragment fragment, ArrayList<support> list) {
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
            view = inflater.inflate(R.layout.support_item, null);
        }
        final support item = list.get(position);
        if(item != null){
            tvTenBenh = view.findViewById(R.id.tvTenBenh);
            tvTenBenh.setText("Tên triệu chứng : "+item.getTenTrieuChung());

            tvNoiDung = view.findViewById(R.id.tvNoiDung);
            tvNoiDung.setText("Nội dung: "+item.getNoiDung());

        }

        return view;
    }




}
