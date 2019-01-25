package com.example.kotaluwukcom.catatgorontalo.network;

import android.content.Context;

import com.example.kotaluwukcom.catatgorontalo.network.interfaces.LihatInterface;


public class LihatService {
    private LihatInterface lihatInterface;

    public LihatService(Context context){
        lihatInterface = RetrofitBuilder.builder(context)
                .create(LihatInterface.class);
    }

}
