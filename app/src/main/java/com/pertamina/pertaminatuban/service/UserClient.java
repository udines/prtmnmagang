package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.UserCredential;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by laptop on 1/19/2018.
 */

public interface UserClient {

    @POST("login")
    Call<LoginResponse> login(@Body UserCredential credential);

    @GET("distribusi/matbal/coba/{bulan}")
    Call<ArrayList<Matbal>> getMatbal(@Path("bulan") int bulan);

}
