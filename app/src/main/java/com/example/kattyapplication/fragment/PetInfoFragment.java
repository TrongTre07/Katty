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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.kattyapplication.R;
import com.example.kattyapplication.adapter.PetInfoAdapter;
import com.example.kattyapplication.api.ApiService;
import com.example.kattyapplication.api.Message;
import com.example.kattyapplication.model.Pet;
import com.example.kattyapplication.model.PetInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PetInfoFragment extends Fragment {
    ListView lvPetInfo;
    ArrayList<PetInfo> list;
    ArrayList<Pet> listpet;
    FloatingActionButton floatAdd;
    Dialog dialog;
    EditText edName, edBreeds;
    TextView tvCancel, tvAdd, tvUpdate;
    TextView tvName, tvBreeds;
    PetInfoAdapter adapter;
    PetInfo item;
    ImageView ivDelete;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pet_info_fragment, container, false);
        lvPetInfo = view.findViewById(R.id.lvPetinfo);
        floatAdd = view.findViewById(R.id.floatAddPet);
        tvName = view.findViewById(R.id.tvName);
        tvBreeds = view.findViewById(R.id.tvBreeds);
        ivDelete = view.findViewById(R.id.ivDelete);

        callApiPetInfo();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogAdd(getActivity());
            }
        });

        lvPetInfo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                openDialogUpdate(getActivity(), item.getId());

                return true;
            }
        });

        lvPetInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = list.get(i);
                Log.d("Item INforrrrr:", item.toString());
                xoa(item.getId());
            }
        });

        return view;
    }

    public void callApiPetInfo() {
        ApiService.apiService.getPetInfo().enqueue(new Callback<List<PetInfo>>() {
            @Override
            public void onResponse(Call<List<PetInfo>> call, Response<List<PetInfo>> response) {
                list = (ArrayList<PetInfo>) response.body();
                adapter = new PetInfoAdapter(getContext(), PetInfoFragment.this, list);
                lvPetInfo.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<PetInfo>> call, Throwable t) {
                Log.d("Got Error:", t.toString());
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void openDialogAdd(final Context context) {

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.pet_info_dialog_add);
        edName = dialog.findViewById(R.id.edNameAdd);
        edBreeds = dialog.findViewById(R.id.edBreedsAdd);
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
                String name = edName.getText().toString();
                String breed = edBreeds.getText().toString();
                Pet item = new Pet(name, breed);


                if (validate() > 0) {
                    item.setTenThuCung(edName.getText().toString());
                    item.setLoai(edBreeds.getText().toString());
                    Pet pet = new Pet(item.getTenThuCung(),item.getLoai());
                    ApiService.apiService.addPetInfo(pet).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
                                callApiPetInfo();

                            }else {
                                //that bai
                                Toast.makeText(context, "Add Fail", Toast.LENGTH_SHORT).show();
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

    public void openDialogUpdate(final Context context, int id) {


        dialog = new Dialog(context);
        dialog.setContentView(R.layout.pet_info_dialog_update);
        edName = dialog.findViewById(R.id.edNameUpdate);
        edBreeds = dialog.findViewById(R.id.edBreedsUpdate);
        tvCancel = dialog.findViewById(R.id.tvCancelUpdate);
        tvUpdate = dialog.findViewById(R.id.tvUpdate);


        edName.setText(String.valueOf(item.getTenThuCung()));
        edBreeds.setText(String.valueOf(item.getLoai()));

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String breed = edBreeds.getText().toString();
                PetInfo item = new PetInfo(id, name, breed);


                if (validate() > 0) {
                    item.setTenThuCung(edName.getText().toString());
                    item.setLoai(edBreeds.getText().toString());
                    PetInfo petInfo = new PetInfo(id, item.getTenThuCung(), item.getLoai());
                    ApiService.apiService.UpdatePetInfo(petInfo).enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Message message = response.body();
                            Log.d("test",message.getId()+"");
                            if(message.getId() == 1){
                                //thanh cong
                                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                                callApiPetInfo();

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

    public void xoa(int id) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiService.apiService.DeletePetInfo(id).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        Message message = response.body();
                        if(message.getId() == 1){
                            Toast.makeText(getContext(), "Delete successfully", Toast.LENGTH_LONG).show();
                            callApiPetInfo();
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
        if (edName.getText().length() == 0 || edBreeds.getText().length() == 0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }

        return check;
    }
}