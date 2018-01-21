package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.RegisterData;
import com.pertamina.pertaminatuban.models.UserCredential;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("login")
    Call<LoginResponse> login(@Body UserCredential credential);

    @POST("register")
    Call<LoginResponse> register(@Body RegisterData data);

    @GET("distribusi/matbal/{bulan}")
    Call<ArrayList<Matbal>> getMatbal(@Path("bulan") int bulan);

    @GET("distribusi/konsumen/{bulan}")
    Call<ArrayList<Konsumen>> getKonsumen(@Path("bulan") int bulan);

    @GET("distribusi/opers/{bulan}")
    Call<ArrayList<Opers>> getOpers(@Path("bulan") int bulan);

    @GET("distribusi/wilayah/{bulan}")
    Call<ArrayList<Wilayah>> getWilayah(@Path("bulan") int bulan);

    @GET("distribusi/ritase/{bulan}")
    Call<ArrayList<Ritase>> getRitase(@Path("bulan") int bulan);
}
