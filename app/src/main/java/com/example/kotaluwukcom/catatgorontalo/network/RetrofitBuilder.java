package com.example.kotaluwukcom.catatgorontalo.network;

import android.content.Context;

import com.example.kotaluwukcom.catatgorontalo.BuildConfig;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.BelumInterface;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.LihatInterface;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SedangInterface;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SelesaiInterface;


import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private String url = Config.API_URL;

    public static Retrofit builder (Context context) {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient().newBuilder();
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS);

        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        okhttpBuilder.cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okhttpBuilder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(okhttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    private static Retrofit getRetrofit(){
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://52.187.117.60/wisata_semarang/wisata/")
                .baseUrl("http://192.168.1.15/catatgorontalo/")
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();

        return retrofit;
    }

    public static LihatInterface getLihatService(){ return getRetrofit().create(LihatInterface.class); }

    public static BelumInterface getBelumService(){ return getRetrofit().create(BelumInterface.class); }

    public static SedangInterface getSedangService(){ return getRetrofit().create(SedangInterface.class); }

    public static SelesaiInterface getSelesaiService(){ return getRetrofit().create(SelesaiInterface.class); }



}
