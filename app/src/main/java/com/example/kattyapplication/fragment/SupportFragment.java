package com.example.kattyapplication.fragment;

import static android.content.ContentValues.TAG;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.kattyapplication.R;
import com.example.kattyapplication.adapter.supportAdapter;
import com.example.kattyapplication.api.CallApi;
import com.example.kattyapplication.model.Message;
import com.example.kattyapplication.model.SupportPost;
import com.example.kattyapplication.model.support;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.text.Regex;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SupportFragment extends Fragment {

    ArrayList<support> list;
    ArrayList<support> list2;

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    AutoCompleteTextView editText;
    supportAdapter adapterSp;
    View view;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.support_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.viewSP);
        editText =  (AutoCompleteTextView) view.findViewById(R.id.edtSearch);
        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(view.VISIBLE);
//        initList();
        CallApiSupportAll();
//        suggestionSupport();
//        AutoCompleteTV();
//        SearchList();
//        CallApiSupportByID(1);
//        SearchViewSupport();

        //support task

        //bắt sự kiện khi click vào nút tìm kiếm
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                list2 = new ArrayList<>();
//                String searchEdt = editText.toString();
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    closeKeyBoard();

                }
                //keycode
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        (event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // truyền cái chữ của edit vô a
                   String a= editText.getText().toString();
                   search(a);
                   // nếu add được thì trong list 2 có dữ liệu sẽ chuyển list thành list 2
                    if(list2.size()>0){

                        adapterSp = new supportAdapter(getContext(), list2);
                        listView.setAdapter(adapterSp);
                    }
//
                    if(a.isEmpty()){
                        CallApiSupportAll();
                    }

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    public void search(String tenTrieuChung){
        // duyệt list
        for (support item: list) {
            // nếu trùng thì add zô list 2
//            String sql= "ho\\w+";
            Pattern pattern= Pattern.compile(tenTrieuChung,Pattern.UNICODE_CASE);
            Matcher matcher= pattern.matcher(item.getTenTrieuChung());
            if (matcher.find()){
                Log.d("ten cho",item.getTenTrieuChung());
                list2.add(item);
            }
        }
    }



//    public void suggestionSupport(){
//        list = getListSupport();
//        for(support item: list){
//            String[] tenbenh = item.getTenTrieuChung();
//            ArrayAdapter adapter = new ArrayAdapter (getContext(), android.R.layout.simple_list_item_1, tenbenh);
//            editText.setAdapter(adapter);
//            editText.setThreshold(1);
//            String text = editText.getText().toString();
//            String text1 = "/^"+text+"$/";
//            if (text1.toUpperCase().matches(x.getTenTrieuChung().toUpperCase())){
//                list2.add(x);
//                adapterSp = new supportAdapter(getContext(), SupportFragment.this, list2);
//                listView.setAdapter(adapterSp);
//                Log.d("ten",item.getTenTrieuChung());
//            }
//        }
//
//    }

//    public void AutoCompleteTV(){
//        String[] diseases = getResources().getStringArray(R.array.diseases);
//        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, diseases);
//        editText.setAdapter(adapter);
//    }

    public void closeKeyBoard(){
        view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

//    public void initList() {
//        items = new String[]{"Java", "JavaScript", "C#", "PHP", "С++", "Python", "C", "SQL", "Ruby", "Objective-C"};
//        listItems = new ArrayList<>(Arrays.asList(items));
//        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listItems);
//        listView.setAdapter(adapter);
//    }

//    public void SearchList(){
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, COUNTRIES);
//        AutoCompleteTextView editText = (AutoCompleteTextView) view.findViewById(R.id.edtSearch);
//        editText.setAdapter(adapter);
//    }

//    private static final String[] COUNTRIES = new String[] {
//            "Belgium", "France", "Italy", "Germany", "Spain"
//    };

    public void CallApiSupportAll() {

        CallApi.callApi.getSupportAll().enqueue(new Callback<List<support>>() {
            @Override
            public void onResponse(Call<List<support>> call, Response<List<support>> response) {
                list = (ArrayList<support>) response.body();
                adapterSp = new supportAdapter(getContext(), list);
                listView.setAdapter(adapterSp);
                progressBar.setVisibility(view.GONE);
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

    public ArrayList<support> getListSupport() {
        CallApi.callApi.getSupportAll().enqueue(new Callback<List<support>>() {
            @Override
            public void onResponse(Call<List<support>> call, Response<List<support>> response) {
                list = (ArrayList<support>) response.body();
                Log.d("Pet Infor:", "" + response.body().size());
            }

            @Override
            public void onFailure(Call<List<support>> call, Throwable t) {

            }
        });
        return list;
    }

//    public void PostApiSupport() {
//
//        //trong dep trai
//        String tentrieuChung = "bệnh viêm dạ dày ở chó";
//        String noiDung = "-Nguyên nhân\n" +
//                "Do giun móc, virus parvo, virus gây bệnh care hoặc ăn phải thức ăn và nước uống có chứa vi khuẩn thương hàn (Salmonella), vi khuẩn yếm khí (Clostridium), vi khuẩn E.Coli,...\n" +
//                "\n" +
//                "-Triệu chứng\n" +
//                "Chó mắc bệnh sẽ sốt, bỏ ăn, đôi lúc kèm theo những cơn run rẩy, nôn mửa liên tục, tiêu chảy nặng, chuyển qua thời kỳ cuối thì phân có màu nâu sẫm, rất tanh, thời kỳ này chó không đi được, chỉ có nằm một chỗ. Chăm sóc không chu đáo, kịp thời thì chó có thể chết từ 2-4 ngày sau khi nhiễm bệnh.\n" +
//                "\n" +
//                "-Cách điều trị\n" +
//                "Nếu phát hiện chó nhiễm bệnh thì nên cho chó ngừng ăn trong 24 giờ đầu, chỉ cần cho chó uống đủ nước. Nếu như chó bị nôn thì có thể sử dụng anticholinergic và chlopromazin hoặc metoclopramid để chó được thoải mái hơn.\n" +
//                "Việc truyền dịch để bù nước, chất điện giải đã mất cũng sẽ giúp chó được ổn hơn. Còn nếu chó đau bụng nhiều thì có thể sử dụng thuốc giảm đau perimidine, chó bị tiêu chảy thì có thể kết hợp điều trị giữa kaolin và pectin hoặc bismuth subcarbonate,...";
//        SupportPost supportPost = new SupportPost(tentrieuChung, noiDung);
//        CallApi.callApi.savePost(tentrieuChung, noiDung).enqueue(new Callback<SupportPost>() {
//            @Override
//            public void onResponse(Call<SupportPost> call, Response<SupportPost> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<SupportPost> call, Throwable t) {
//                Log.d(TAG, "onFailure: ");
//            }
//        });
//    }

}
