package com.example.kotaluwukcom.catatgorontalo.network.interfaces;

import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;


import retrofit2.Call;
import retrofit2.http.GET;

public interface LihatInterface {
    //membaca data
    @GET("read_laporan.php")
    Call<ListLapor> ambilDataLapor();
}
