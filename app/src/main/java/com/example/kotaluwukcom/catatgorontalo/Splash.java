package com.example.kotaluwukcom.catatgorontalo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        this.requestWindowsFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Masuk.class));
                finish();
            }
        }, 8000L);
        
    }

    private void requestWindowsFeature(int featureNoTitle) {
    }
}
