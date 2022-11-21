package com.example.kattyapplication.api;

import com.example.kattyapplication.model.SupportPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISupport {



        @POST("api/Values/add-support")
        Call<SupportPost> createBook(@Body SupportPost supportPost);

}
