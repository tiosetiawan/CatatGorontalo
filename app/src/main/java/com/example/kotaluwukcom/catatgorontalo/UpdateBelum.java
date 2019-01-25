package com.example.kotaluwukcom.catatgorontalo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;
import com.example.kotaluwukcom.catatgorontalo.network.Config;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.BelumInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateBelum extends AppCompatActivity {

    public static final String URL = "http://192.168.1.13/catatgorontalo/";
    private RadioButton radioUpButton;
    private ProgressDialog progress;

    RadioGroup radiostatus;
    RadioButton radiobelum, radiosedang, radioselesai;
    Button buttonupdate;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_belum);


        radiostatus = (RadioGroup) findViewById(R.id.radiostatus);
        radiobelum = (RadioButton) findViewById(R.id.radiobelum);
        radiosedang = (RadioButton) findViewById(R.id.radiosedang);
        radioselesai = (RadioButton) findViewById(R.id.radioselesai);
        id = (TextView) findViewById(R.id.idlapor);

        buttonupdate = (Button) findViewById(R.id.buttonupdate);

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ubah();
            }
        });

      //  int selectedId = radiostatus.getCheckedRadioButtonId();
        // mencari id radio button
     //   radioUpButton = (RadioButton) findViewById(selectedId);
        //String status = radioUpButton.getText().toString();

        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        String id_laporan = intent.getStringExtra("id_laporan");


        id.setText(id_laporan);

        if (status.equals("belum diproses")) {
            radiobelum.setChecked(true);

        }else{
            radiosedang.setChecked(true);
        }

    }

    private void ubah() {

        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        String id_laporan = id.getText().toString();

        int selectedId = radiostatus.getCheckedRadioButtonId();
        // mencari id radio button
        radioUpButton = (RadioButton) findViewById(selectedId);
        String status = radioUpButton.getText().toString();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BelumInterface api = retrofit.create(BelumInterface.class);
        Call<ListLapor> call = api.updatebelum(id_laporan, status);
        call.enqueue(new Callback<ListLapor>() {
            @Override
            public void onResponse(Call<ListLapor> call, Response<ListLapor> response) {
                ArrayList<LaporData> value = response.body().getLapor();
                String message = response.body().getMessage();
                progress.dismiss();
                if (response.isSuccessful()) {
                    if (value.equals("1")) {
                        Toast.makeText(UpdateBelum.this, message, Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = new Intent(UpdateBelum.this, LaporSedang.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UpdateBelum.this, message, Toast.LENGTH_SHORT).show();
                    }
                }


            @Override
            public void onFailure(Call<ListLapor> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(UpdateBelum.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
