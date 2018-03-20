package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.operation.models.Pumpable;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OperationClient {
    @GET("operasional/tangkitimbun/{tahun}/{bulan}")
    Call<Object> getPumpableRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @POST("operasional/tangkitimbun")
    Call<Object> postPumpable(@Body Object object);
    @GET("operasional/tangkitimbun/{tahun}/{bulan}")
    Call<ArrayList<Pumpable>> getPumpable(@Path("tahun") String tahun, @Path("bulan") String bulan);
}
