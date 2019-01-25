package com.example.kotaluwukcom.catatgorontalo.network;

import android.content.Context;

import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SelesaiInterface;

public class SelesaiService {

    private SelesaiInterface selesaiInterface;

    public SelesaiService (Context context){
        selesaiInterface = RetrofitBuilder.builder(context)
                .create(SelesaiInterface.class);
    }

}
