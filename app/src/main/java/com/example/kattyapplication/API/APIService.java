package com.example.kattyapplication.API;

import com.example.kattyapplication.Models.Message;
import com.example.kattyapplication.Models.Remind;
import com.example.kattyapplication.Models.RemindUpload;
import com.example.kattyapplication.model.Infor_pet;
import com.example.kattyapplication.model.SetlistSpend;
import com.example.kattyapplication.model.Spend;
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

public interface APIService {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    APIService apiService = new Retrofit.Builder().baseUrl("https://trongtre.kynalab.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    //https://trongtre.kynalab.com/api/Values/all-remind

    @GET("api/Values/all-remind")
    Call<List<Remind>> getList();

    @POST("api/Values/add-remind")
    Call<Message> addRemind (@Body RemindUpload remindUpload);

    @POST("api/Values/add-remind")
    Call<Message> addRemind2 (@Body Remind remind);

    @POST("api/values/change-remind")
    Call<Message> changeRemind (@Body Remind remind);

    @POST("api/Values/delete-remind")
    Call<Message> deleteRemind (@Query("id") int id);

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
