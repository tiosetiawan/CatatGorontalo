package com.example.kotaluwukcom.catatgorontalo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.SessionManager;
import com.example.kotaluwukcom.catatgorontalo.network.AppController;
import com.example.kotaluwukcom.catatgorontalo.network.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {


    SessionManager sessionManager;
    private TextView nrp;
    private TextView nama;
    private TextView pangkat;
    private TextView jabatan;
    private TextView telpon;
    private ImageView foto;
    private Button btnLogout;
    private String urlpol = Config.API_URL_FOTO_POL;
    String getId;

    public static void start(Context context) {
        Intent intent = new Intent(context, ProfileFragment.class);
        context.startActivity(intent);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_profile = inflater.inflate(R.layout.fragment_profile, container, false);

        nrp = (TextView) view_profile.findViewById(R.id.nrp);
        nama = (TextView) view_profile.findViewById(R.id.nama);
        pangkat = (TextView) view_profile.findViewById(R.id.pangkat);
        jabatan = (TextView) view_profile.findViewById(R.id.jabatan);
        telpon = (TextView) view_profile.findViewById(R.id.telpon);
        foto = (ImageView)view_profile.findViewById(R.id.foto);

        sessionManager = new SessionManager(getContext());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.NRP);

        btnLogout = (Button) view_profile.findViewById(R.id.btn_logout);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKeluar();
                sessionManager.logout();
            }
        });

        return view_profile;

    }

    private void showDialogKeluar(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar!")
                .setIcon(R.mipmap.ic_catat)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        sessionManager.logout();
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.API_READ_POL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(AppController.TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){

                                for (int i =0; i < jsonArray.length(); i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strNrp = object.getString("nrp").trim();
                                    String strNama = object.getString("nama").trim();
                                    String strPangkat = object.getString("pangkat").trim();
                                    String strJabatan = object.getString("jabatan").trim();
                                    String strTelpon = object.getString("telpon").trim();
                                    String strFoto = object.getString("foto_pol").trim();

                                    nrp.setText(strNrp);
                                    nama.setText(strNama);
                                    pangkat.setText(strPangkat);
                                    jabatan.setText(strJabatan);
                                    telpon.setText(strTelpon);
                                    //foto.glide
                                    Glide.with(ProfileFragment.this)
                                            .load(urlpol+strFoto).apply(RequestOptions.circleCropTransform())
                                            .into(foto);

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error Reading Detail "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("nrp", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void onResume() {
        super.onResume();
        getUserDetail();
    }


}
