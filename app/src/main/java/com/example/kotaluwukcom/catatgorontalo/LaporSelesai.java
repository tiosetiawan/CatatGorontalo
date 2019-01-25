package com.example.kotaluwukcom.catatgorontalo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kotaluwukcom.catatgorontalo.adapter.AdapterSedang;
import com.example.kotaluwukcom.catatgorontalo.adapter.AdapterSelesai;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;
import com.example.kotaluwukcom.catatgorontalo.network.RetrofitBuilder;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SedangInterface;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.SelesaiInterface;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LaporSelesai extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ArrayList<LaporData> listData;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor_selesai);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerLaporSelesai);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshlap);



        pd = (ProgressBar) findViewById(R.id.pd);

        listData = new ArrayList<>();
        ambilData();



        // int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

    }


    private void ambilData() {
        pd.setVisibility(View.VISIBLE);
        SelesaiInterface api = RetrofitBuilder.getSelesaiService();
        retrofit2.Call<ListLapor> call = api.ambilDataSelesaiProses();
        call.enqueue(new Callback<ListLapor>() {
            @Override
            public void onResponse(retrofit2.Call<ListLapor> call, Response<ListLapor> response) {
                pd.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().toString().equals("true")) {
                        listData = response.body().getLapor();
                        AdapterSelesai adapter = new AdapterSelesai(listData, LaporSelesai.this);
                        recyclerView.setAdapter(adapter);

                        //ngetes data di log
                        for (int i = 0; i < listData.size(); i++) {
                            Log.d(TAG, "onResponse: " + listData.get(i).getFoto());
                        }
                    } else {
                        Toast.makeText(LaporSelesai.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LaporSelesai.this, "Respones is Not Succesfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ListLapor> call, Throwable t) {
                Toast.makeText(LaporSelesai.this, "Response Failure", Toast.LENGTH_SHORT).show();
            }


        });


    }

}
