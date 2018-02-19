package com.pertamina.pertaminatuban.service;

import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.marine.models.MarineIdentifier;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.PortCharges;
import com.pertamina.pertaminatuban.marine.models.ShipCondition;
import com.pertamina.pertaminatuban.marine.models.ShipParticular;
import com.pertamina.pertaminatuban.marine.models.TankerMovement;
import com.pertamina.pertaminatuban.marine.models.TemporaryStop;
import com.pertamina.pertaminatuban.marine.models.WaitingTime;
import com.pertamina.pertaminatuban.models.LoginResponse;
import com.pertamina.pertaminatuban.models.RegisterData;
import com.pertamina.pertaminatuban.models.RegisterResponse;
import com.pertamina.pertaminatuban.models.UserCredential;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserClient {

    @POST("login")
    Call<LoginResponse> login(@Body UserCredential credential);

    @POST("register")
    Call<RegisterResponse> register(@Body RegisterData data);

    @POST("distribusi/matbal")
    Call<Object> postMatbal(@Body ArrayList<Matbal> matbals);
    @GET("distribusi/matbal/{bulan}")
    Call<ArrayList<Matbal>> getMatbal(@Path("bulan") int bulan);
    @GET("distribusi/matbal/total/tahun/{tahun}")
    Call<ArrayList<Matbal>> getMatbalTahun(@Path("tahun") String tahun);
    @GET("distribusi/matbal/total/bulan/{tahun}/{bulan}")
    Call<ArrayList<Matbal>> getMatbalBulan(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @GET("distribusi/matbal/total/{tanggal}")
    Call<ArrayList<Matbal>> getMatbalHari(@Path("tanggal") String tanggal);

    @GET("distribusi/konsumen/{bulan}")
    Call<ArrayList<Konsumen>> getKonsumen(@Path("bulan") int bulan);
    @POST("distribusi/konsumen")
    Call<Object> postKonsumen(@Body ArrayList<Konsumen> konsumens);
    @GET("distribusi/konsumen/total/tahun/{tahun}")
    Call<ArrayList<Konsumen>> getKonsumenTahun(@Path("tahun") String tahun);
    @GET("distribusi/konsumen/total/bulan/{tahun}/{bulan}")
    Call<ArrayList<Konsumen>> getKonsumenBulan(@Path("tahun") String tahun, @Path("bulan") String bulan);
    @GET("distribusi/konsumen/total/{tanggal}")
    Call<ArrayList<Konsumen>> getKonsumenTanggal(@Path("tanggal") String tanggal);

    @GET("distribusi/opers/{bulan}")
    Call<ArrayList<Opers>> getOpers(@Path("bulan") int bulan);

    @GET("distribusi/wilayah/{bulan}")
    Call<ArrayList<Wilayah>> getWilayah(@Path("bulan") int bulan);

    @GET("distribusi/ritase/{bulan}")
    Call<ArrayList<Ritase>> getRitase(@Path("bulan") int bulan);

    @GET("distribusi/{data}/{bulan}")
    Call<Object> getObject(@Path("data") String jenisData, @Path("bulan") int bulan);

    @POST("distribusi/wilayah")
    Call<Object> postWilayah(@Body ArrayList<Wilayah> wilayahs);

    @POST("distribusi/ritase")
    Call<Object> postRitase(@Body ArrayList<Ritase> ritases);

    @POST("distribusi/opers")
    Call<Object> postOpers(@Body Opers opers);

    //initial tanker
    @GET("marine/initialtanker/{bulan}")
    Call<ArrayList<InitialTanker>> getInitialTanker(@Path("bulan") String bulan);
    @POST("marine/initialtanker")
    Call<Object> postInitialTanker(@Body ArrayList<MarineInput> marine);
    @POST("marine/initialtanker/check")
    Call<Object> getInitInitialTanker(@Body MarineIdentifier identifier);

    //port charges
    @GET("marine/portcharges/{bulan}")
    Call<ArrayList<PortCharges>> getPortCharges(@Path("bulan") String bulan);
    @POST("marine/portcharges")
    Call<Object> postPortCharges(@Body ArrayList<MarineInput> marine);
    @POST("marine/portcharges/check")
    Call<PortCharges> getInitPortCharges(@Body MarineIdentifier identifier);

    //ship condition
    @GET("marine/shipcondition/{bulan}")
    Call<ArrayList<ShipCondition>> getShipCondition(@Path("bulan") String bulan);
    @POST("marine/shipcondition")
    Call<Object> postShipCondition(@Body ArrayList<MarineInput> marine);
    @POST("marine/shipcondition/check")
    Call<ShipCondition> getInitShipCondition(@Body MarineIdentifier identifier);

    //ship particular
    @GET("marine/shipparticular/{bulan}")
    Call<ArrayList<ShipParticular>> getShipParticular(@Path("bulan") String bulan);
    @POST("marine/shipparticular")
    Call<Object> postShipParticular(@Body ArrayList<MarineInput> marine);
    @POST("marine/shipparticular/check")
    Call<ShipParticular> getInitShipParticular(@Body MarineIdentifier identifier);

    //tanker movement
    @GET("marine/tankermovement/{bulan}")
    Call<ArrayList<TankerMovement>> getTankerMovement(@Path("bulan") String bulan);
    @POST("marine/tankermovement")
    Call<Object> postTankerMovement(@Body ArrayList<MarineInput> marine);
    @POST("marine/tankermovement/check")
    Call<TankerMovement> getInitTankerMovement(@Body MarineIdentifier identifier);

    //temporary stop
    @GET("marine/tempstopact/{bulan}")
    Call<ArrayList<TemporaryStop>> getTemporaryStop(@Path("bulan") String bulan);
    @POST("marine/tempstopact")
    Call<Object> postTemporaryStop(@Body ArrayList<MarineInput> marine);
    @POST("marine/tempstopact/check")
    Call<TemporaryStop> getInitTemporaryStop(@Body MarineIdentifier identifier);

    //waiting time
    @GET("marine/waitingexcess/{bulan}")
    Call<ArrayList<WaitingTime>> getWaitingTime(@Path("bulan") String bulan);
    @POST("marine/waitingexcess")
    Call<Object> postWaitingTime(@Body ArrayList<MarineInput> marine);
    @POST("marine/waitingexcess/check")
    Call<WaitingTime> getInitWaitingTime(@Body MarineIdentifier identifier);

    //Experiment
    @GET("marine/initialtanker/{bulan}")
    Call<Object> getInitialTankerExperiment(@Path("bulan") String bulan);

}
