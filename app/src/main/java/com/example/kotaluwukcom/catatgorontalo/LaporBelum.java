package com.example.kotaluwukcom.catatgorontalo;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.adapter.AdapterBelum;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;
import com.example.kotaluwukcom.catatgorontalo.network.BelumService;
import com.example.kotaluwukcom.catatgorontalo.network.RetrofitBuilder;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.BelumInterface;
import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LaporBelum extends AppCompatActivity {


    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList<LaporData> listData;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar pd;
    AdapterBelum adapterBelum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor_belum);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerLapor);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshlap);

        FirebaseMessaging.getInstance().subscribeToTopic("kecelakaan");


        pd = (ProgressBar) findViewById(R.id.pd);

        listData = new ArrayList<>();
        ambilData();



      // int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        //new GridLayoutManager(this, 2);

    }


    private void ambilData() {
        pd.setVisibility(View.VISIBLE);
        BelumInterface api = RetrofitBuilder.getBelumService();
        retrofit2.Call<ListLapor> call = api.ambilDataBelumProses();
        call.enqueue(new Callback<ListLapor>() {
            @Override
            public void onResponse(retrofit2.Call<ListLapor> call, Response<ListLapor> response) {
                pd.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().toString().equals("true")) {
                        listData = response.body().getLapor();
                        AdapterBelum adapter = new AdapterBelum(listData, LaporBelum.this);
                        recyclerView.setAdapter(adapter);

                        //ngetes data di log
                        for (int i = 0; i < listData.size(); i++) {
                            Log.d(TAG, "onResponse: " + listData.get(i).getFoto());
                        }
                    } else {
                        Toast.makeText(LaporBelum.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LaporBelum.this, "Respones is Not Succesfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ListLapor> call, Throwable t) {
                Toast.makeText(LaporBelum.this, "Response Failure", Toast.LENGTH_SHORT).show();
            }


        });


    }



}
