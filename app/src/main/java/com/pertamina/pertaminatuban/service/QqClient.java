package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.qualityquantity.models.ItemFotoSample;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemTestReport;
import com.pertamina.pertaminatuban.qualityquantity.models.WorkingLoss;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface QqClient {

    //working loss
    @GET("quality/workinglosses/{wilayah}/{tahun}/{bulan}")
    Call<ArrayList<WorkingLoss>> getWorkingLossBulan(@Path("wilayah") String wilayah, @Path("tahun") String tahun, @Path("bulan") String bulan);
    @POST("quality/workinglosses")
    Call<Object> postWorkingLoss(@Body ArrayList<WorkingLoss> workingLosses);

    //foto sample
    @GET("quality/ujisample/{tahun}/{bulan}")
    Call<ArrayList<ItemFotoSample>> getFotoSampleBulan(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @Multipart
    @POST("quality/ujisample")
    Call<Object> postFotoSample(@Part MultipartBody.Part image, @Part("deskripsi") RequestBody description);

    //trucking loss dan test report
    @GET("quality/truckingloss/{tahun}/{bulan}")
    Call<ArrayList<ItemTestReport>> getTestReportBulan(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @GET("quality/truckingloss/{tahun}")
    Call<ArrayList<ItemTestReport>> getTruckingLossTahun(@Path("tahun") String tahun);
    @Multipart
    @POST("quality/truckingloss")
    Call<Object> postFilePdf(@Part MultipartBody.Part file, @Part("type") RequestBody type, @Part("deskripsi") RequestBody deskripsi);
}
