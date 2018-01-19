package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.models.UserCredential;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by laptop on 1/19/2018.
 */

public interface UserClient {

    @POST("login")
    Call<Object> UserClient(@Body UserCredential credential);
}
