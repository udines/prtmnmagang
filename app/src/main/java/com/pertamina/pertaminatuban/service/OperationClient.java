package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.operation.models.Datels;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.operation.models.Suplai;
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
    @GET("oprtsg/check/{tahun}/{bulan}/{batch}")
    Call<TransferPipeline> getPipeline(@Path("tahun") String tahun, @Path("bulan") String bulan, @Path("batch") String batch);
    @PUT("oprtsg/")
    Call<Object> putPipeline(@Body TransferPipeline pipeline);
    @GET("oprtsg/{tahun}/{bulan}")
    Call<Object> getPipelineBulanRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);

    //tppi
    @POST("oprtppi/")
    Call<Object> postTppi(@Body TransferPipeline pipeline);
    @GET("oprtppi/check/{tahun}/{bulan}/{batch}")
    Call<TransferPipeline> getTppi(@Path("tahun") String tahun, @Path("bulan") String bulan, @Path("batch") String batch);
    @PUT("oprtppi/")
    Call<Object> putTppi(@Body TransferPipeline pipeline);
    @GET("oprtppi/{tahun}/{bulan}")
    Call<Object> getTppiBulanRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);

    //twu
    @POST("oprtwu/")
    Call<Object> postTwu(@Body TransferPipeline pipeline);
    @GET("oprtwu/check/{tahun}/{bulan}/{batch}")
    Call<TransferPipeline> getTwu(@Path("tahun") String tahun, @Path("bulan") String bulan, @Path("batch") String batch);
    @POST("oprtwu/")
    Call<Object> putTwu(@Body TransferPipeline pipeline);
    @GET("oprtwu/{tahun}/{bulan}")
    Call<Object> getTwuBulanRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);

    //suplai
    @POST("suplaibbm/")
    Call<Object> postSuplai(@Body ArrayList<Suplai> suplais);
    @GET("suplaibbm/{tahun}/{bulan}")
    Call<ArrayList<Suplai>> getSuplaiBulan(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @PUT("suplaibbm/")
    Call<Object> putSuplai(@Body ArrayList<Suplai> suplais);
}
