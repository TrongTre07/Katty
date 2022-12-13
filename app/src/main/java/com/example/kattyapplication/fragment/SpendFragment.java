package com.example.kattyapplication.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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
    TextView tvCancel, tvAdd, tvUpdate, tvResult, tvTotal, tvDate;
    TextView tvLoaiTD, tvGiatien, tvTenTC;
    SpendAdapter adapter;
    Spend item;
    View view;
    ProgressBar pb;
    Spinner spnTenTCUpdate, spnTenTCAdd, spnMonth, spnTotal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.spend_fragment, container, false);
        lvSpend = view.findViewById(R.id.lvSpend);
        floatAdd = view.findViewById(R.id.floatAddTD);
        btnSum = view.findViewById(R.id.btnSum);
        tvTenTC = view.findViewById(R.id.tvTenTC);
        tvGiatien = view.findViewById(R.id.tvGiatien);
        tvLoaiTD = view.findViewById(R.id.tvLoaiTD);

        pb = view.findViewById(R.id.ProgressBar);
        pb.setVisibility(view.VISIBLE);

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

        lvSpend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                openDialogUpdate(getActivity(), item.getId());

            }
        });


        lvSpend.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                xoa(item.getId());
                return false;
            }
        });

        lvSpend.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == SCROLL_STATE_TOUCH_SCROLL){
                    btnSum.setVisibility(absListView.INVISIBLE);
                }else {
                    btnSum.setVisibility(absListView.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

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
                pb.setVisibility(view.GONE);

            }

            @Override
            public void onFailure(Call<List<Spend>> call, Throwable t) {
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void openDialogAdd(final Context context) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_dialog_add);
        edtLoaiTD = dialog.findViewById(R.id.edtLoaitdAdd);
        edtGiatien = dialog.findViewById(R.id.edtGiatienADD);
        tvDate = dialog.findViewById(R.id.tvDateAdd);
        spnTenTCAdd = dialog.findViewById(R.id.spnTenTCAdd);
        tvCancel = dialog.findViewById(R.id.tvCancelAdd);
        tvAdd = dialog.findViewById(R.id.tvAdd);

        edtLoaiTD.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                }
                return false;
            }
        });
        edtGiatien.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                }
                return false;
            }
        });


         getTenTC(spnTenTCAdd);

        Calendar calendar = Calendar.getInstance();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay= "";
                                String thang= "";
                                if(i2 < 10){
                                    ngay = "0" + i2;
                                }else{
                                    ngay = String.valueOf(i2);
                                }

                                if((i1 + 1) < 10){
                                    thang = "0" + (i1 + 1);
                                }
                                else{
                                    thang = String.valueOf((i1 + 1));
                                }

                                tvDate.setText(i + "-" + thang + "-" + ngay);
                            }
                        },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spend item = new Spend();


                if (validate() > 0) {
                    HashMap<String, Object> hsPet = (HashMap<String, Object>) spnTenTCAdd.getSelectedItem();
                    Integer idThuCung = (Integer) hsPet.get("id");
                    Log.d("id thú cưng >>> ", ""+idThuCung);
                    item.setLoaiTieuDung(edtLoaiTD.getText().toString());
                    item.setGiaTien(Integer.parseInt(edtGiatien.getText().toString()));

                    item.setNgayChiTieu(tvDate.getText().toString());

                    Spend spend = new Spend(item.getLoaiTieuDung(), item.getGiaTien(), idThuCung, item.getNgayChiTieu());
                    ApiService.apiService.addSpend(spend).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                               //thanh cong
                                Toast.makeText(context, "Thêm Thành công", Toast.LENGTH_SHORT).show();
                                callApiSpend();

                            }else {
                                //that bai
                                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(context, "Không thành công", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }



    public void openDialogUpdate(final Context context, int id) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_dialog_update);
        edtLoaiTD = dialog.findViewById(R.id.edtLoaitdUpdate);
        edtGiatien = dialog.findViewById(R.id.edtGiatienUdate);
        tvDate = dialog.findViewById(R.id.tvDateUpdate);
        spnTenTCUpdate = dialog.findViewById(R.id.spnTenTCUpdate);
        tvCancel = dialog.findViewById(R.id.tvCancelUpdate);
        tvUpdate = dialog.findViewById(R.id.tvUpdate);
        getTenTC(spnTenTCUpdate);

        edtLoaiTD.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                }
                return false;
            }
        });
        edtGiatien.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();
                }
                return false;
            }
        });

        edtLoaiTD.setText(String.valueOf(item.getLoaiTieuDung()));
        edtGiatien.setText(String.valueOf(item.getGiaTien()));
        String day = item.getNgayChiTieu();
        String sub = day.substring(0, 10);
        tvDate.setText(sub);

        Calendar calendar = Calendar.getInstance();
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String ngay= "";
                                String thang= "";
                                if(i2 < 10){
                                    ngay = "0" + i2;
                                }else{
                                    ngay = String.valueOf(i2);
                                }

                                if((i1 + 1) < 10){
                                    thang = "0" + (i1 + 1);
                                }
                                else{
                                    thang = String.valueOf((i1 + 1));
                                }

                                tvDate.setText(i + "-" + thang + "-" + ngay);
                            }
                        },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spend item = new Spend();



                if (validate() > 0) {
                    HashMap<String, Object> hsPet = (HashMap<String, Object>) spnTenTCUpdate.getSelectedItem();
                    Integer idThuCung = (Integer) hsPet.get("id");
                    Log.d("idTC>>>>", ""+idThuCung);
                    item.setLoaiTieuDung(edtLoaiTD.getText().toString());
                    item.setGiaTien(Integer.parseInt(edtGiatien.getText().toString()));
                    item.setNgayChiTieu(tvDate.getText().toString());

                    SetlistSpend setlistSpend = new SetlistSpend(id, item.getLoaiTieuDung(), item.getGiaTien(), idThuCung, item.getNgayChiTieu() );
                    Log.d("SLDKFJSLDKFJDSLKFJDSLK", setlistSpend.toString());
                    ApiService.apiService.updateSpend(setlistSpend).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();

                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                callApiSpend();

                            }else {
                                //that bai
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_LONG).show();
                        }
                    });

                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    public void getMonth(Spinner spnMonth){
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        ArrayAdapter ad = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item, month);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMonth.setAdapter(ad);

    }


    public void openDialogStatistical(final Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.spend_statistical);
        tvResult = dialog.findViewById(R.id.tvResult);
        spnMonth = dialog.findViewById(R.id.spnMonth);
        tvCancel = dialog.findViewById(R.id.tvCancelS);
        tvTotal = dialog.findViewById(R.id.tvTotal);
        spnTotal = dialog.findViewById(R.id.spnTotal);
        getTenTC(spnTotal);
        getMonth(spnMonth);




        tvTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Spend> list = new ArrayList<>();
                list = getlistSpend();
                HashMap<String, Object> hsPet = (HashMap<String, Object>) spnTotal.getSelectedItem();
                Integer idTC = (Integer) hsPet.get("id");

                String month = spnMonth.getSelectedItem().toString();
                Log.d("month>>>> ", month);

                Integer m = Integer.parseInt(month) - 1;


                int total = 0;
                int total1 = 0;
                for(Spend Total : list){
                    String day = Total.getNgayChiTieu();
                    String sub = day.substring(5, 7);
                    Integer sub2 = Integer.parseInt(sub);

                    if(idTC.equals(Total.getIdThuCung())){

                        if(sub.equals(month)){
                            total = total + Total.getGiaTien();
                        }

                        if(sub2 == m){
                           total1 = total1 + Total.getGiaTien();
                        }

                    }
                }

                int finalTotal = total;
                tvResult.setText(finalTotal +"VND");

                int tt = total1;

                PieChart pie = dialog.findViewById(R.id.pie);

                ArrayList<PieEntry> data = new ArrayList<>();
                data.add(new PieEntry(finalTotal,"chi tháng: "+ month));
                data.add(new PieEntry(tt,"chi tháng: " + m));

                PieDataSet pieDataSet = new PieDataSet(data, "Chi tiêu");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieDataSet.setValueTextColor(Color.BLACK);
                pieDataSet.setValueTextSize(16f);

                PieData pieData = new PieData(pieDataSet);
                pie.setData(pieData);
                pie.getDescription().setEnabled(false);
                pie.setCenterText("Khoản chi tiêu");
                pie.animate();

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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
                            Toast.makeText(getContext(), "Xóa thành Công", Toast.LENGTH_LONG).show();
                            callApiSpend();
                        }else{
                            Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
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
        if (edtLoaiTD.getText().length() == 0 || edtGiatien.getText().length() == 0 ) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        String loaitd = edtLoaiTD.getText().toString();
        String gia = edtGiatien.getText().toString();
        if(!Pattern.matches("[a-zA-Z0-9]+", loaitd)){
            Toast.makeText(getContext(), "Bạn phải nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        if(!Pattern.matches("[0-9]+", gia)){
            Toast.makeText(getContext(), "Bạn phải nhập đúng định dạng", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        return list;
    }

    public void closeKeyBoard(){
        view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
