package com.example.kotaluwukcom.catatgorontalo.network.interfaces;

import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BelumInterface {
    //membaca data
    @GET("laporb.php")
    Call<ListLapor> ambilDataBelumProses();

    @FormUrlEncoded
    @POST("updatelaporb.php")
    Call<ListLapor> updatebelum(
                                @Field("id_laporan") String idlaporan,
                                @Field("status") String status);

                     //  @Field("kelas") String kelas,
                    // @Field("sesi") String sesi);


}
