package com.example.kotaluwukcom.catatgorontalo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kotaluwukcom.catatgorontalo.network.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CatatPihak extends AppCompatActivity {

    private static final String TAG = CatatKronologis.class.getSimpleName();
    public static final String URL = "http://192.168.1.15/catatgorontalo/";
    private String CATAT_PIHAK_URL = "http://192.168.1.15/catatgorontalo/input_pihak.php";

    private ProgressDialog progress;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    int success;

    Button simpanpihak;
    TextView id;
    EditText namaPihak, umurPihak, kerjaPihak, alamatPihak, jenisKendaraan, noKendaraan;


    private String KEY_NAMA = "nama_pihak";
    private String KEY_UMUR = "umur_pihak";
    private String KEY_KERJA = "pekerjaan";
    private String KEY_ALAMAT = "alamat";
    private String KEY_JENIS  = "jenis_kendaraan";
    private String KEY_PLAT   =  "plat_kendaraan";


    public static void start(Context context) {
        Intent intent = new Intent(context, CatatKronologis.class);
        context.startActivity(intent);

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catat_pihak);

        namaPihak = (EditText)findViewById(R.id.namapihak);
        umurPihak = (EditText)findViewById(R.id.umurpihak);
        kerjaPihak = (EditText)findViewById(R.id.kerjapihak);
        alamatPihak = (EditText) findViewById(R.id.alamatpihak);
        jenisKendaraan = (EditText) findViewById(R.id.jeniskendaraan);
        noKendaraan = (EditText) findViewById(R.id.nokendaraan);
        id = (TextView) findViewById(R.id.textid);

        Intent intent = getIntent();
        String id_laporan = intent.getStringExtra("id_laporan");

        id.setText(id_laporan);

        simpanpihak = (Button) findViewById(R.id.simpanpihak);

        simpanpihak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = namaPihak.getText().toString().trim();
                String umur = umurPihak.getText().toString().trim();
                String kerja = kerjaPihak.getText().toString().trim();
                String alamat = alamatPihak.getText().toString().trim();
                String jenis = jenisKendaraan.getText().toString().trim();
                String no = noKendaraan.getText().toString().trim();

                if (!nama.isEmpty() || !umur.isEmpty() || !kerja.isEmpty() || !alamat.isEmpty()
                        || !jenis.isEmpty() || !no.isEmpty() ){
                        simpanPihak();
                     //  inputPihak();
                }else {
                    namaPihak.setError("Nama tidak boleh kosong");
                    umurPihak.setError("Umur tidak boleh kosong");
                    kerjaPihak.setError("Pekerjaan tidak boleh kosong");
                    alamatPihak.setError("Alamat tidak boleh kosong");
                    jenisKendaraan.setError("Jenis Kendaraan tidak boleh kosong");
                    noKendaraan.setError("No kendaraan tidak boleh kosong");
                }
            }
        });

    }

    private void simpanPihak() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CATAT_PIHAK_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);
                            if (success == 1) {
                                Log.e("v Add", jObj.toString());
                                Toast.makeText(CatatPihak.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(CatatPihak.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(CatatPihak.this);
                        builder
                                .setMessage("Pihak berhasil diinput. Jika pihak lebih dari 1 klik kembali tombol pihak")
                                .setCancelable(false)
                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // jika tombol diklik, maka akan menutup activity ini
                                        CatatPihak.this.finish();
                                    }
                                });

                        // membuat alert dialog dari builder
                        AlertDialog alertDialog = builder.create();

                        // menampilkan alert dialog
                        alertDialog.show();

                        //menghilangkan progress dialog
                        // loading.dismiss();
                        // Intent intent = new Intent(CatatPihak.this, LaporSedang.class);
                        // startActivity(intent);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        AlertDialog.Builder builder = new AlertDialog.Builder(CatatPihak.this);
                        builder.setMessage("Terjadi Kesalahan.")
                                .setNegativeButton("Ulangi", null).create().show();
                        Log.e(TAG, "Terjadi Kesalahan");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters

                Map<String, String> params = new HashMap<String, String>();
                //menambah parameter yang di kirim ke web servis
                params.put("id_laporan", id.getText().toString().trim());
                params.put(KEY_NAMA, namaPihak.getText().toString().trim());
                params.put(KEY_UMUR, umurPihak.getText().toString().trim());
                params.put(KEY_KERJA, kerjaPihak.getText().toString().trim());
                params.put(KEY_ALAMAT, alamatPihak.getText().toString().trim());
                params.put(KEY_JENIS, jenisKendaraan.getText().toString().trim());
                params.put(KEY_PLAT, noKendaraan.getText().toString().trim());



                //kembali ke parameters
                Log.e(TAG, "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }





}
