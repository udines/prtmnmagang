package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.qualityquantity.models.WorkingLoss;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QqClient {
    @GET("quality/workinglosses/{wilayah}/{tahun}/{bulan}")
    Call<ArrayList<WorkingLoss>> getWorkingLossBulan(@Path("wilayah") String wilayah, @Path("tahun") String tahun, @Path("bulan") String bulan);
    @POST("quality/workinglosses")
    Call<Object> postWorkingLoss(@Body ArrayList<WorkingLoss> workingLosses);
}
