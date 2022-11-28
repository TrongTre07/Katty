package com.example.kattyapplication.fragment;

import static android.content.ContentValues.TAG;

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
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SupportFragment extends Fragment {

    ArrayList<support> list;
    ArrayList<support> list2= new ArrayList<>();

    String[] items;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    supportAdapter adapterSp;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.support_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.viewSP);
        editText = (EditText) view.findViewById(R.id.edtSearch);
//        initList();

        CallApiSupportAll();
//        SearchList();
//        CallApiSupportByID(1);
//        SearchViewSupport();

        //support task

        //bắt sự kiện khi click vào nút tìm kiếm
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                list2 = new ArrayList<>();
                String searchEdt = editText.getText().toString();
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

                        adapterSp = new supportAdapter(getContext(), SupportFragment.this, list2);
                        listView.setAdapter(adapterSp);
                    }

                    if(searchEdt.isEmpty()){
                        CallApiSupportAll();
                    }

                    return true;
                }
                return false;
            }
        });



//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                SupportFragment.this.adapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        return view;
    }

    public void search(String tenTrieuChung){
        // duyệt list
        for (support item: list) {
            // nếu trùng thì add zô list 2
            if (tenTrieuChung.equalsIgnoreCase(item.getTenTrieuChung())){
                Log.d("ten",item.getTenTrieuChung());
                //list2.add(item);
                list2.add(item);

            }
        }
    }

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

}
