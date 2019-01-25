package com.example.kotaluwukcom.catatgorontalo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kotaluwukcom.catatgorontalo.network.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Masuk extends AppCompatActivity {

    SessionManager sessionManager;
    Button btn_register;
    private ProgressBar loading;
    private EditText nrpText;
    private EditText passwordText;
    private Button btnMasuk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);


        loading = findViewById(R.id.loading);
        nrpText = (EditText) findViewById(R.id.txt_nrp);
        passwordText = (EditText) findViewById(R.id.txt_password);
        btnMasuk = (Button) findViewById(R.id.btn_login);


        sessionManager = new SessionManager(this);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNrp = nrpText.getText().toString().trim();
                String mPass = passwordText.getText().toString().trim();

                if (!mNrp.isEmpty() || !mPass.isEmpty()) {
                    Login(mNrp, mPass);
                } else {
                    nrpText.setError("NRP Tidak Boleh Kosong");
                    passwordText.setError("Password Tidak Boleh Kosong");
                }
            }
        });

    }


    private void Login(final String nrp, final String password) {

        loading.setVisibility(View.VISIBLE);
        btnMasuk.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.API_LOGIN,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String nrp = object.getString("nrp").trim();
                                    String nama = object.getString("nama").trim();
                                    String pangkat = object.getString("pangkat").trim();
                                    String jabatan = object.getString("jabatan").trim();
                                    String telpon = object.getString("telpon").trim();
                                    String foto_pol = object.getString("foto_pol").trim();

                                    sessionManager.createSession(nrp, nama, pangkat, jabatan, telpon, foto_pol);

                                    Intent intent = new Intent(Masuk.this, MainActivity.class);
                                    intent.putExtra("nrp", nrp);
                                    intent.putExtra("nama", nama);
                                    intent.putExtra("pangkat", pangkat);
                                    intent.putExtra("jabatan", jabatan);
                                    intent.putExtra("telpon", telpon);
                                    intent.putExtra("foto_pol", foto_pol);
                                    startActivity(intent);
                                    finish();

                                    loading.setVisibility(View.GONE);


                                }

                            }else {
                                loading.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(Masuk.this);
                                builder.setMessage("Password Salah")
                                        .setNegativeButton("Ulangi", null).create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btnMasuk.setVisibility(View.VISIBLE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Masuk.this);
                            builder.setMessage("NRP Salah")
                                    .setNegativeButton("Ulangi", null).create().show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btnMasuk.setVisibility(View.VISIBLE);
                        Toast.makeText(Masuk.this, "Error " +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nrp", nrp);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

}
