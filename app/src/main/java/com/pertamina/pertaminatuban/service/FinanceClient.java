package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.finance.models.CostPerLiter;
import com.pertamina.pertaminatuban.finance.models.LaporanPerjalananDinas;
import com.pertamina.pertaminatuban.finance.models.NoPerjalanan;
import com.pertamina.pertaminatuban.finance.models.PostRealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.TarifAntarkota;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FinanceClient {

    @GET("financecsv/getData/{tahun}")
    Call<ArrayList<RealisasiAnggaran>> getRealisasiAnggaran(@Path("tahun") String tahun);
    @POST("financecsv/getData")
    Call<Object> getRealisasiAnggaranRaw(@Body PostRealisasiAnggaran post);

    //CPL
    @GET("financecsv/cpl/{tahun}")
    Call<ArrayList<CostPerLiter>> getCpl(@Path("tahun") String tahun);

    //Perjalanan Dinas
    @POST("laporandinas/masterperjalanan")
    Call<Object> postPerjalananDinas(@Body LaporanPerjalananDinas laporan);
    @GET("laporandinas/masterperjalanan/{tahun}/{bulan}")
    Call<ArrayList<LaporanPerjalananDinas>> getPerjalananDinas(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @GET("laporandinas/masterperjalanan/{tahun}/{bulan}")
    Call<Object> getPerjalananDinasRaw(@Path("tahun") String tahun, @Path("bulan") String bulan);

    @POST("laporandinas/claimer")
    Call<Object> postUraianPerjalanan(@Body UraianPerjalanan uraian);
    @POST("laporandinas/claimer/get")
    Call<ArrayList<UraianPerjalanan>> getUraianPerjalanan(@Body NoPerjalanan noPerjalanan);
    @PUT("laporandinas/claimer")
    Call<Object> updateUraianPerjalanan(@Body UraianPerjalanan uraian);

    @GET("laporandinas/antarkota")
    Call<ArrayList<TarifAntarkota>> getTarif();
}
