package com.example.kattyapplication.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kattyapplication.R;
import com.example.kattyapplication.adapter.supportAdapter;
import com.example.kattyapplication.api.CallApi;
import com.example.kattyapplication.api.ISupport;
import com.example.kattyapplication.model.SupportPost;
import com.example.kattyapplication.model.support;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SupportFragment extends Fragment {

    ArrayList<support> list;
    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    supportAdapter adapterSp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.viewSP);
        editText = (EditText) view.findViewById(R.id.edtSearch);
//        initList();
        CallApiSupportAll();
        CallApiSupportByID(1);
        PostApiSupport();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SupportFragment.this.adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

//    public void initList() {
//        items = new String[]{"Java", "JavaScript", "C#", "PHP", "С++", "Python", "C", "SQL", "Ruby", "Objective-C"};
//        listItems = new ArrayList<>(Arrays.asList(items));
//        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);
//        listView.setAdapter(adapter);
//    }

    public void CallApiSupportAll() {

        CallApi.callApi.getSupportAll().enqueue(new Callback<List<support>>() {
            @Override
            public void onResponse(Call<List<support>> call, Response<List<support>> response) {
                list = (ArrayList<support>) response.body();
                adapterSp = new supportAdapter(getContext(), SupportFragment.this, list);
                listView.setAdapter(adapterSp);
            }

            @Override
            public void onFailure(Call<List<support>> call, Throwable t) {
                Toast.makeText(getContext(), "loi roi", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void CallApiSupportByID(Integer id) {
        CallApi.callApi.getSupportByID(id).enqueue(new Callback<support>() {
            @Override
            public void onResponse(Call<support> call, Response<support> response) {
                support support = response.body();
                Log.d("+++++", support.toString());
            }

            @Override
            public void onFailure(Call<support> call, Throwable t) {

            }
        });
    }

    public void PostApiSupport() {
        String tentrieuChung = "bệnh viêm dạ dày ở chó";
        String noiDung = "-Nguyên nhân\n" +
                "Do giun móc, virus parvo, virus gây bệnh care hoặc ăn phải thức ăn và nước uống có chứa vi khuẩn thương hàn (Salmonella), vi khuẩn yếm khí (Clostridium), vi khuẩn E.Coli,...\n" +
                "\n" +
                "-Triệu chứng\n" +
                "Chó mắc bệnh sẽ sốt, bỏ ăn, đôi lúc kèm theo những cơn run rẩy, nôn mửa liên tục, tiêu chảy nặng, chuyển qua thời kỳ cuối thì phân có màu nâu sẫm, rất tanh, thời kỳ này chó không đi được, chỉ có nằm một chỗ. Chăm sóc không chu đáo, kịp thời thì chó có thể chết từ 2-4 ngày sau khi nhiễm bệnh.\n" +
                "\n" +
                "-Cách điều trị\n" +
                "Nếu phát hiện chó nhiễm bệnh thì nên cho chó ngừng ăn trong 24 giờ đầu, chỉ cần cho chó uống đủ nước. Nếu như chó bị nôn thì có thể sử dụng anticholinergic và chlopromazin hoặc metoclopramid để chó được thoải mái hơn.\n" +
                "Việc truyền dịch để bù nước, chất điện giải đã mất cũng sẽ giúp chó được ổn hơn. Còn nếu chó đau bụng nhiều thì có thể sử dụng thuốc giảm đau perimidine, chó bị tiêu chảy thì có thể kết hợp điều trị giữa kaolin và pectin hoặc bismuth subcarbonate,...";
        SupportPost supportPost = new SupportPost(tentrieuChung, noiDung);
        CallApi.callApi.savePost(tentrieuChung, noiDung).enqueue(new Callback<SupportPost>() {
            @Override
            public void onResponse(Call<SupportPost> call, Response<SupportPost> response) {

            }

            @Override
            public void onFailure(Call<SupportPost> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
