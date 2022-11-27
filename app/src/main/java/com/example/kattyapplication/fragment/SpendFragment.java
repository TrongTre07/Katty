package com.example.kattyapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kattyapplication.R;
import com.example.kattyapplication.adapter.SpendAdapter;
import com.example.kattyapplication.api.ApiService;
import com.example.kattyapplication.api.Message;
import com.example.kattyapplication.model.Infor_pet;
import com.example.kattyapplication.model.SetlistSpend;
import com.example.kattyapplication.model.Spend;
import com.example.kattyapplication.model.TieuDung;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpendFragment extends Fragment {
    ListView lvSpend;
    ArrayList<Spend> list;
    ArrayList<Infor_pet> listTC;
    FloatingActionButton floatAdd;
    Button btnSum;
    Dialog dialog;
    EditText edtLoaiTD, edtGiatien;
    TextView tvSum, tvCancel, tvAdd, tvUpdate;
    TextView tvLoaiTD, tvGiatien, tvTenTC;
    SpendAdapter adapter;
    Spend item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spend_fragment, container, false);
        lvSpend = view.findViewById(R.id.lvSpend);
        floatAdd = view.findViewById(R.id.floatAddTD);
        btnSum = view.findViewById(R.id.btnSum);
        tvTenTC = view.findViewById(R.id.tvTenTC);
        tvGiatien = view.findViewById(R.id.tvGiatien);
        tvLoaiTD = view.findViewById(R.id.tvLoaiTD);

        getlistPet();
        callApiSpend();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd(getActivity());
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogStatistical(getActivity());
            }
        });

        lvSpend.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                openDialogUpdate(getActivity(), item.getId());

                return true;
            }
        });

        lvSpend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                xoa(item.getId());
            }
        });

        return view;
    }

    public void callApiSpend() {
        ApiService.apiService.getSpend().enqueue(new Callback<List<Spend>>() {
            @Override
            public void onResponse(Call<List<Spend>> call, Response<List<Spend>> response) {
                list = (ArrayList<Spend>) response.body();
                adapter = new SpendAdapter(getContext(), SpendFragment.this, list);
                lvSpend.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Spend>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void openDialogAdd(final Context context) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_dialog_add);
        edtLoaiTD = dialog.findViewById(R.id.edtLoaitdAdd);
        edtGiatien = dialog.findViewById(R.id.edtGiatienADD);
        Spinner spnTenTCAdd = dialog.findViewById(R.id.spnTenTCAdd);
        tvCancel = dialog.findViewById(R.id.tvCancelAdd);
        tvAdd = dialog.findViewById(R.id.tvAdd);

         getTenTC(spnTenTCAdd);


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TieuDung item = new TieuDung();


                if (validate() > 0) {
                    HashMap<String, Object> hsPet = (HashMap<String, Object>) spnTenTCAdd.getSelectedItem();
                    Integer idThuCung = (Integer) hsPet.get("id");
                    Log.d("kjbjhbjhgjgjhbkjgjh", ""+idThuCung);
                    item.setLoaiTieuDung(edtLoaiTD.getText().toString());
                    item.setGiaTien(Integer.parseInt(edtGiatien.getText().toString()));
                    TieuDung tieuDung = new TieuDung(item.getLoaiTieuDung(), item.getGiaTien(), idThuCung);
                    ApiService.apiService.addSpend(tieuDung).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                               //thanh cong
                                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
                                callApiSpend();

                            }else {
                                //that bai
                                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(context, "Khong thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }



    public void openDialogUpdate(final Context context, int id) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_dialog_update);
        edtLoaiTD = dialog.findViewById(R.id.edtLoaitdUpdate);
        edtGiatien = dialog.findViewById(R.id.edtGiatienUdate);
        Spinner spnTenTCUpdate = dialog.findViewById(R.id.spnTenTCUpdate);
        tvCancel = dialog.findViewById(R.id.tvCancelUpdate);
        tvUpdate = dialog.findViewById(R.id.tvUpdate);
        getTenTC(spnTenTCUpdate);

        edtLoaiTD.setText(String.valueOf(item.getLoaiTieuDung()));
        edtGiatien.setText(String.valueOf(item.getGiaTien()));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TieuDung item = new TieuDung();


                if (validate() > 0) {
                    HashMap<String, Object> hsPet = (HashMap<String, Object>) spnTenTCUpdate.getSelectedItem();
                    Integer idThuCung = (Integer) hsPet.get("id");
                    Log.d("idTC>>>>", ""+idThuCung);
                    item.setLoaiTieuDung(edtLoaiTD.getText().toString());
                    item.setGiaTien(Integer.parseInt(edtGiatien.getText().toString()));
                    TieuDung tieuDung = new TieuDung(item.getLoaiTieuDung(), item.getGiaTien(), idThuCung);
                    SetlistSpend setlistSpend = new SetlistSpend(id, item.getLoaiTieuDung(), item.getGiaTien(), idThuCung );
                    Log.d("SLDKFJSLDKFJDSLKFJDSLK", setlistSpend.toString());
                    ApiService.apiService.updateSpend(setlistSpend).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                                callApiSpend();

                            }else {
                                //that bai
                                Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    });

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public void getTenTC(Spinner spnTenTC) {

        ArrayList<Infor_pet> test = new ArrayList<>();
        test = getlistPet();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Infor_pet TC : test) {
            HashMap<String, Object> tc = new HashMap<>();
            tc.put("id", TC.getId());
            tc.put("tenThuCung", TC.getTenThuCung());
            listHM.add(tc);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenThuCung"},
                new int[]{android.R.id.text1}
        );
        spnTenTC.setAdapter(adapter);
    }


    public void openDialogStatistical(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_statistical);
        tvSum = dialog.findViewById(R.id.tvSum);
        tvCancel = dialog.findViewById(R.id.tvCancelS);

        ArrayList<Spend> test = new ArrayList<>();
        test = getlistSpend();
        for (Spend TD : test) {
            HashMap<String, Object> td = new HashMap<>();
            td.put("id", TD.getId());
            td.put("tenThuCung", TD.getTenThuCung());
            int total = 0;
            for(Spend Total : test){
                total = total + Total.getGiaTien();
            }
            tvSum.setText(total +"VND");
        }

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void xoa(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiService.apiService.deleteTieuDung(id).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message = response.body();
                        if(message.getId() == 1){
                            Toast.makeText(getContext(), "Delete successfully", Toast.LENGTH_LONG).show();
                            callApiSpend();
                        }else{
                            Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public int validate() {
        int check = 1;
        if (edtLoaiTD.getText().length() == 0 || edtGiatien.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        return check;
    }

    public ArrayList<Infor_pet> getlistPet() {
        ApiService.apiService.getInforPet().enqueue(new Callback<List<Infor_pet>>() {
            @Override
            public void onResponse(Call<List<Infor_pet>> call, Response<List<Infor_pet>> response) {
                listTC = (ArrayList<Infor_pet>) response.body();
                Log.d("Pet Infor:", "" + response.body().size());

            }

            @Override
            public void onFailure(Call<List<Infor_pet>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return listTC;
    }

    public ArrayList<Spend> getlistSpend() {
        ApiService.apiService.getSpend().enqueue(new Callback<List<Spend>>() {
            @Override
            public void onResponse(Call<List<Spend>> call, Response<List<Spend>> response) {
                list = (ArrayList<Spend>) response.body();
                Log.d("Pet Infor:", "" + response.body().size());

            }

            @Override
            public void onFailure(Call<List<Spend>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

}
