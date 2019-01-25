package com.example.kotaluwukcom.catatgorontalo.network;

import android.content.Context;

import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SedangInterface;

public class SedangService {

    private SedangInterface sedangInterface;

    public SedangService (Context context){
        sedangInterface = RetrofitBuilder.builder(context)
                .create(SedangInterface.class);
    }

}
