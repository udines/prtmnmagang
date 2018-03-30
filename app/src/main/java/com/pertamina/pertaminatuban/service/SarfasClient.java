package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.saranafasilitas.models.GetBody;
import com.pertamina.pertaminatuban.saranafasilitas.models.Sarfas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SarfasClient {
    @POST("/sarfas/")
    Call<Object> postSarfas(@Body Sarfas sarfas);
    @POST("sarfas/{tahun}/{bulan}")
    Call<ArrayList<Sarfas>> getSarfasBulan(@Body GetBody body, @Path("tahun") String tahun, @Path("bulan") String bulan);
}
