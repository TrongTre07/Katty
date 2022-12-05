package com.example.kattyapplication.api;

import com.example.kattyapplication.model.Pet;
import com.example.kattyapplication.model.PetInfo;
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

    //https://trongtre.kynalab.com/api/values/all-information
    @GET("api/Values/all-information")
    Call<List<PetInfo>> getPetInfo();
    @GET("api/Values/detail-information/{id}")
    Call<List<PetInfo>> detailPetInfo() ;
    @POST("api/Values/add-information")
    Call<Message> addPetInfo(@Body Pet pet);
    @POST("api/Values/change-information")
    Call<Message> UpdatePetInfo(@Body PetInfo petInfo) ;
    @POST("api/Values/delete-information")
    Call<Message> DeletePetInfo(@Query("id")Integer Id);

}
