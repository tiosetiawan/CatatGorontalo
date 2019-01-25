package com.example.kotaluwukcom.catatgorontalo.network.interfaces;

import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SedangInterface {
    //membaca data
    @GET("laporse.php")
    Call<ListLapor> ambilDataSedangProses();


}
