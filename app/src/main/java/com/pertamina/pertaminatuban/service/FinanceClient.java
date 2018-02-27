package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.finance.models.CostPerLiter;
import com.pertamina.pertaminatuban.finance.models.PostRealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FinanceClient {

    @GET("financecsv/getData/{tahun}")
    Call<ArrayList<RealisasiAnggaran>> getRealisasiAnggaran(@Path("tahun") String tahun);
    @POST("financecsv/getData")
    Call<Object> getRealisasiAnggaranRaw(@Body PostRealisasiAnggaran post);

    //CPL
    @GET("financecsv/cpl/{tahun}")
    Call<ArrayList<CostPerLiter>> getCpl(@Path("tahun") String tahun);
}
