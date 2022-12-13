package com.example.kattyapplication.API;
import com.example.kattyapplication.model.Message;
import com.example.kattyapplication.model.SupportPost;
import com.example.kattyapplication.model.support;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CallApi {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    CallApi callApi = new Retrofit.Builder()
            .baseUrl("https://trongtre.kynalab.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CallApi.class);
    @GET("api/Values/all-support")
    Call<List<support>> getSupportAll();
    @GET("api/Values/detail-support/{id}")
    Call <support> getSupportByID(@Path("id")  Integer id);

}


