package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.finance.models.PostRealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FinanceClient {
    @POST("financecsv/getData")
    Call<ArrayList<RealisasiAnggaran>> getRealisasiAnggaran(@Body PostRealisasiAnggaran post);
    @POST("financecsv/getData")
    Call<Object> getRealisasiAnggaranRaw(@Body PostRealisasiAnggaran post);
}
