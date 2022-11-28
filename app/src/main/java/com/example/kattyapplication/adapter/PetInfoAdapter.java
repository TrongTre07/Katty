package com.example.kattyapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kattyapplication.R;
import com.example.kattyapplication.fragment.PetInfoFragment;
import com.example.kattyapplication.model.PetInfo;

import java.util.ArrayList;

public class PetInfoAdapter extends ArrayAdapter<PetInfo> {
    private Context context;
    PetInfoFragment fragment;
    private ArrayList<PetInfo> list;
    TextView tvName,tvBreeds;


    public PetInfoAdapter(@NonNull Context context, PetInfoFragment fragment, ArrayList<PetInfo> list) {
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
            view = inflater.inflate(R.layout.pet_info_item, null);
        }
        final PetInfo item = list.get(position);
        if(item != null){
            tvName = view.findViewById(R.id.tvName);
            tvName.setText("Tên thú cưng : "+item.getTenThuCung());
            tvBreeds = view.findViewById(R.id.tvBreeds);
            tvBreeds.setText("Loài: "+item.getLoai());
        }

        return view;
    }
}
