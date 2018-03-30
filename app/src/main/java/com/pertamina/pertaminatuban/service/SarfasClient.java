package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.saranafasilitas.models.Sarfas;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SarfasClient {
    @POST("/sarfas/")
    Call<Object> postSarfas(@Body Sarfas sarfas);
}
