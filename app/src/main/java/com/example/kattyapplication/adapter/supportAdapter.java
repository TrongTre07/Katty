package com.example.kattyapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.kattyapplication.R;
import com.example.kattyapplication.fragment.SupportFragment;
import com.example.kattyapplication.model.support;

import java.util.ArrayList;
import java.util.List;

public class supportAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<support> list;

    TextView tvTenBenh, tvNoiDung;
    CardView cardView;

    public supportAdapter(Context context, ArrayList<support> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.support_item, parent, false);
        }



        support item = list.get(position);

        if (item != null) {
            tvTenBenh = convertView.findViewById(R.id.tvTenBenh);
            tvTenBenh.setText("Triệu chứng : " + item.getTenTrieuChung());
            cardView = convertView.findViewById(R.id.onClickSupport);
//            tvNoiDung = convertView.findViewById(R.id.tvNoiDung);
//            tvNoiDung.setText("Nội dung: "+item.getNoiDung());

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    View view = inflater.inflate(R.layout.support_dialog, null);
                    builder.setView(view);
                    AlertDialog alertDialogSupport = builder.create();

                    tvTenBenh = view.findViewById(R.id.tvTenBenh);
                    tvTenBenh.setText("Tên triệu chứng : " + item.getTenTrieuChung());

                    tvNoiDung = view.findViewById(R.id.tvNoiDung);
                    tvNoiDung.setText("Nội dung: " + item.getNoiDung());
                    tvNoiDung.setMovementMethod(new ScrollingMovementMethod());

                    alertDialogSupport.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    alertDialogSupport.show();
                }
            });
        }

        return convertView;

    }
}
