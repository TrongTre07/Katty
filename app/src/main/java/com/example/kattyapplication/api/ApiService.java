package com.example.kattyapplication.api;

import com.example.kattyapplication.model.Infor_pet;
import com.example.kattyapplication.model.SetlistSpend;
import com.example.kattyapplication.model.Spend;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://trongtre.kynalab.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("api/Values/all-information")
    Call<List<Infor_pet>> getInforPet();

    @GET("api/Values/layDanhSachTieudung")
    Call<List<Spend>> getSpend();

    @POST("api/Values/add-tieudung")
    Call<Message> addSpend (@Body Spend spend);


    @POST("api/Values/change-tieudung")
    Call<Message> updateSpend (@Body SetlistSpend setlistSpend);

    @POST("api/Values/delete-tieudung")
    Call<Message> deleteSpend(@Body Integer id);

    @POST("api/Values/delete-tieudung")
    Call<Message> deleteTieuDung (@Query("id") int id);

}
