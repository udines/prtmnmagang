package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.marine.input.MarineInput;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.PortCharges;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.RegisterData;
import com.pertamina.pertaminatuban.models.RegisterResponse;
import com.pertamina.pertaminatuban.models.UserCredential;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("login")
    Call<LoginResponse> login(@Body UserCredential credential);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterData data);

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

    @GET("distribusi/{data}/{bulan}")
    Call<Object> getObject(@Path("data") String jenisData, @Path("bulan") int bulan);

    @POST("distribusi/matbal")
    Call<Object> postMatbal(@Body ArrayList<Matbal> matbals);

    @POST("distribusi/konsumen")
    Call<Object> postKonsumen(@Body ArrayList<Konsumen> konsumens);

    @POST("distribusi/wilayah")
    Call<Object> postWilayah(@Body ArrayList<Wilayah> wilayahs);

    @POST("distribusi/ritase")
    Call<Object> postRitase(@Body ArrayList<Ritase> ritases);

    @POST("distribusi/opers")
    Call<Object> postOpers(@Body Opers opers);

    @GET("marine/initialtanker/{bulan}")
    Call<ArrayList<InitialTanker>> getInitialTanker(@Path("bulan") String bulan);

    @POST("marine/initialtanker")
    Call<Object> postInitialTanker(@Body ArrayList<MarineInput> marine);

    @GET("marine/portcharges/{bulan}")
    Call<ArrayList<PortCharges>> getPortCharges(@Path("bulan") String bulan);

    @POST("marine/portcharges")
    Call<Object> postPortCharges(@Body ArrayList<MarineInput> marine);
}
