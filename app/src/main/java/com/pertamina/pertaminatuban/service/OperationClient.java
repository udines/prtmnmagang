package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.operation.models.Datels;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.operation.models.TransferPipeline;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OperationClient {

    //pumpables
    @GET("operasional/tangkitimbun/{tahun}/{bulan}")
    Call<Object> getPumpableRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @POST("operasional/tangkitimbun")
    Call<Object> postPumpable(@Body ArrayList<Pumpable> pumpables);
    @GET("operasional/tangkitimbun/{tahun}/{bulan}")
    Call<ArrayList<Pumpable>> getPumpable(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @POST("operasional/tangkitimbuncheck/{tanggal}")
    Call<ArrayList<Pumpable>> getPumpableTanggal(@Path("tanggal") String tanggal, @Body Datels datels);
    @POST("operasional/tangkitimbuncheck/{tanggal}")
    Call<Object> getPumpableTanggalRaw(@Path("tanggal") String tanggal, @Body Datels datels);
    @PUT("operasional/tangkitimbuncheck")
    Call<Object> putPumpable(@Body ArrayList<Pumpable> pumpables);

    //pipeline
    @POST("oprtsg/")
    Call<Object> postPipeline(@Body TransferPipeline pipeline);
    @GET("oprtsg/{tahun}/{bulan}/{batch}")
    Call<TransferPipeline> getPipeline(@Path("tahun") String tahun, @Path("bulan") String bulan, @Path("batch") String batch);
    @POST("oprtsg/")
    Call<Object> putPipeline(@Body TransferPipeline pipeline);
}
