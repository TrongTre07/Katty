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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kattyapplication.API.APIService;
import com.example.kattyapplication.Models.Message;
import com.example.kattyapplication.R;
import com.example.kattyapplication.adapter.PetInfoAdapter;
//import com.example.kattyapplication.api.ApiService;
//import com.example.kattyapplication.api.Message;
import com.example.kattyapplication.model.Pet;
import com.example.kattyapplication.model.PetInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetInfoFragment extends Fragment {
    ListView lvPetInfo;
    ArrayList<PetInfo> list;
    ArrayList<Pet> listpet;
    FloatingActionButton floatAdd;
    Dialog dialog;
    EditText edName, edBreeds, edWeight, edAge, edOtherInfo;
    TextView tvCancel, tvAdd, tvUpdate;
    TextView tvName, tvBreeds, tvWeight, tvAge, tvBirthday, tvOtherInfo;
    PetInfoAdapter adapter;
    PetInfo item;
    ImageView ivDelete;
    ProgressBar progressBar;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pet_info_fragment, container, false);
        lvPetInfo = view.findViewById(R.id.lvPetinfo);
        floatAdd = view.findViewById(R.id.floatAddPet);
        tvName = view.findViewById(R.id.tvName);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(view.VISIBLE);
//        tvBreeds = view.findViewById(R.id.tvBreeds);
//        tvWeight = view.findViewById(R.id.tvWeight);
//        tvAge = view.findViewById(R.id.tvAge);
//        tvBirthday = view.findViewById(R.id.tvBirthday);
//        tvOtherInfo = view.findViewById(R.id.tvOtherInfo);
//        ivDelete = view.findViewById(R.id.ivDelete);

        callApiPetInfo();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd(getActivity());
            }
        });
        lvPetInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                Log.d("Item INforrrrr:", item.toString());
                xoa(item.getId());
                return true;
            }
        });
        lvPetInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                Log.d("Item INforrrrr:", item.toString());
                openDialogUpdate(getActivity(), item.getId());

            }
        });

        return view;
    }

    public void callApiPetInfo() {
        APIService.apiService.getPetInfo().enqueue(new Callback<List<PetInfo>>() {
            @Override
            public void onResponse(Call<List<PetInfo>> call, Response<List<PetInfo>> response) {
                list = (ArrayList<PetInfo>) response.body();
                adapter = new PetInfoAdapter(getContext(), PetInfoFragment.this, list);
                lvPetInfo.setAdapter(adapter);
                progressBar.setVisibility(view.GONE);
            }

            @Override
            public void onFailure(Call<List<PetInfo>> call, Throwable t) {
                Log.d("Got Error:", t.toString());
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openDialogAdd(final Context context) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.pet_info_dialog_add);
        edName = dialog.findViewById(R.id.edNameAdd);
        edBreeds = dialog.findViewById(R.id.edBreedsAdd);
        edWeight = dialog.findViewById(R.id.edWeightAdd);
        edAge = dialog.findViewById(R.id.edAgeAdd);
        tvBirthday = dialog.findViewById(R.id.tvBirthdayAdd);
        edOtherInfo = dialog.findViewById(R.id.edOtherInfoAdd);
        tvCancel = dialog.findViewById(R.id.tvCancelAdd);
        tvAdd = dialog.findViewById(R.id.tvAdd);



        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate() > 0) {

                    String name = edName.getText().toString();
                    String breed = edBreeds.getText().toString();
                    Float weight= Float.parseFloat(edWeight.getText().toString()) ;
                    Integer age = Integer.parseInt( edAge.getText().toString());
                    String birthday= tvBirthday.getText().toString();
                    String otherinfo = edOtherInfo.getText().toString();
                    Pet item = new Pet(name, breed,weight,age,birthday,otherinfo);

                    item.setTenThuCung(edName.getText().toString());
                    item.setLoai(edBreeds.getText().toString());
                    item.setCanNang(Float.parseFloat(edWeight.getText().toString()));
                    item.setTuoi(Integer.parseInt(edAge.getText().toString()));
                    String birth = tvBirthday.getText().toString();
                    String birthFormat = birth.replace("/", "-");
                    item.setNgaySinh(birthFormat);
                    item.setThongTinKhac(edOtherInfo.getText().toString());
                    Pet pet= new Pet( item.getTenThuCung(), item.getLoai(), item.getCanNang(), item.getTuoi(), item.getNgaySinh() ,item.getThongTinKhac());
                    APIService.apiService.addPetInfo(pet).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                callApiPetInfo();

                            }else {
                                //that bai
                                Toast.makeText(context, "Thêm không được", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Calendar calendar = Calendar.getInstance();


        tvBirthday.setOnClickListener(new View.OnClickListener() {
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

//                                tvBirthday.setText(ngay + "/" + thang + "/" + i);
                                tvBirthday.setText(i + "-" + thang + "-" + ngay);
                            }
                        },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

    }

    public void openDialogUpdate(final Context context, int id) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.pet_info_dialog_update);
        edName = dialog.findViewById(R.id.edNameUpdate);
        edBreeds = dialog.findViewById(R.id.edBreedsUpdate);
        edWeight = dialog.findViewById(R.id.edWeightUpdate);
        edAge = dialog.findViewById(R.id.edAgeUpdate);
        tvBirthday = dialog.findViewById(R.id.tvBirthdayUpdate);
        edOtherInfo = dialog.findViewById(R.id.edOtherInfoUpdate);
        tvCancel = dialog.findViewById(R.id.tvCancelUpdate);
        tvUpdate = dialog.findViewById(R.id.tvUpdate);


        edName.setText(String.valueOf(item.getTenThuCung()));
        edBreeds.setText(String.valueOf(item.getLoai()));
        edWeight.setText(String.valueOf(item.getCanNang()));
        String NS = item.getNgaySinh();
        String sub = NS.substring(0,10);
        edAge.setText(String.valueOf(item.getTuoi()));
        tvBirthday.setText(sub);
        edOtherInfo.setText(String.valueOf(item.getThongTinKhac()));
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate() > 0) {

                    String name = edName.getText().toString();
                    String breed = edBreeds.getText().toString();
                    Float  weight= Float.parseFloat(edWeight.getText().toString()) ;
                    Integer age = Integer.parseInt( edAge.getText().toString());
                    String birthday= tvBirthday.getText().toString();
                    String otherinfo = edOtherInfo.getText().toString();
                    PetInfo item = new PetInfo(id, name, breed,weight,age,birthday,otherinfo);


                    item.setTenThuCung(edName.getText().toString());
                    item.setLoai(edBreeds.getText().toString());
                    item.setCanNang(Float.parseFloat(edWeight.getText().toString()));
                    item.setTuoi(Integer.parseInt(edAge.getText().toString()));
                    item.setNgaySinh(tvBirthday.getText().toString());
                    item.setThongTinKhac(edOtherInfo.getText().toString());
                    PetInfo petInfo = new PetInfo(id, item.getTenThuCung(), item.getLoai(), item.getCanNang(), item.getTuoi(), item.getNgaySinh() ,item.getThongTinKhac());
                    APIService.apiService.UpdatePetInfo(petInfo).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                callApiPetInfo();

                            }else {
                                //that bai
                                Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                            }Toast.makeText(context, message.getContent(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Calendar calendar = Calendar.getInstance();


        tvBirthday.setOnClickListener(new View.OnClickListener() {
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

                                tvBirthday.setText( i+ "/" + thang + "/" +ngay);
                            }
                        },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONTH),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

    }

    public void xoa(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                APIService.apiService.DeletePetInfo(id).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message = response.body();
                        if(message.getId() == 1){
                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                            callApiPetInfo();
                        }else{
                            Toast.makeText(getContext(), "Không xóa được", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
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

//        if (edName.getText().length() == 0 || edBreeds.getText().length() == 0 || edWeight.getText().length() == 0  || edAge.getText().length() == 0 ) {
//            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            check = -1;
//        }
//        String ten = edName.getText().toString();
//        String loai = edBreeds.getText().toString();
////        if(!Pattern.matches("[a-zA-Z0-9]+", ten)){
////            Toast.makeText(getContext(), "Không chứa kí tự đặc biệt", Toast.LENGTH_SHORT).show();
////            check = -1;
////        }
////        if(!Pattern.matches("[a-zA-Z0-9]+", loai)){
////            Toast.makeText(getContext(), "Không chứa kí tự đặc biệt", Toast.LENGTH_SHORT).show();
////            check = -1;
////        }
        String tuoi = edAge.getText().toString();
//        String canNang = edWeight.getText().toString();
        if(!Pattern.matches("[0-9]+",tuoi)){
            Toast.makeText(getContext(), "Bạn phải nhập số", Toast.LENGTH_SHORT).show();
            check = -1;
        }
//        if(!Pattern.matches("[0-9]+",canNang)){
//            Toast.makeText(getContext(), "Bạn phải nhập số", Toast.LENGTH_SHORT).show();
//            check = -1;
        //}

        return check;
    }
}
