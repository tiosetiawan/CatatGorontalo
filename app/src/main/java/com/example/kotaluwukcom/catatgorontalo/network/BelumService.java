package com.example.kotaluwukcom.catatgorontalo.network;

import android.content.Context;

import com.example.kotaluwukcom.catatgorontalo.network.interfaces.BelumInterface;

public class BelumService {

    private BelumInterface belumInterface;

    public BelumService (Context context){
        belumInterface = RetrofitBuilder.builder(context)
                .create(BelumInterface.class);
    }

}
