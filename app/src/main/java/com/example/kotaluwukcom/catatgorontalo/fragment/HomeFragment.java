package com.example.kotaluwukcom.catatgorontalo.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.kotaluwukcom.catatgorontalo.LaporBelum;
import com.example.kotaluwukcom.catatgorontalo.LaporSedang;
import com.example.kotaluwukcom.catatgorontalo.LaporSelesai;
import com.example.kotaluwukcom.catatgorontalo.MainActivity;
import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;
import com.example.kotaluwukcom.catatgorontalo.network.RetrofitBuilder;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.LihatInterface;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    ArrayList<LaporData> listData;
    private CardView belum, sedang, selesai;
    private TextView jumbelum, jumsedang, jumselesai;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;

    private RequestQueue requestQueue1;
    private StringRequest stringRequest1;

    private RequestQueue requestQueue2;
    private StringRequest stringRequest2;

    ArrayList<HashMap<String, String>> list_data;
    ArrayList<HashMap<String, String>> list_data1;
    ArrayList<HashMap<String, String>> list_data2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view_home = inflater.inflate(R.layout.fragment_home, container, false);

        //sesuaikan dengan ip pc anda
        String url = "http://192.168.1.15/catatgorontalo/jumlahbelum.php";
        String url1 = "http://192.168.1.15/catatgorontalo/jumlahsedang.php";
        String url2 = "http://192.168.1.15/catatgorontalo/jumlahselesai.php";

        belum = (CardView) view_home.findViewById(R.id.belum);
        sedang = (CardView) view_home.findViewById(R.id.sedang);
        selesai = (CardView) view_home.findViewById(R.id.selesai);

        jumbelum = (TextView) view_home.findViewById(R.id.jumlahbelum);
        jumsedang = (TextView) view_home.findViewById(R.id.jumlahsedang);
        jumselesai = (TextView) view_home.findViewById(R.id.jumlahselesai);



        requestQueue = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue1 = Volley.newRequestQueue(getActivity().getApplication());
        requestQueue2 = Volley.newRequestQueue(getActivity().getApplication());

        list_data = new ArrayList<HashMap<String, String>>();
        list_data1 = new ArrayList<HashMap<String, String>>();
        list_data2 = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("jumlahbelum");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("belum", json.getString("belum"));

                        list_data.add(map);
                    }

                    jumbelum.setText(list_data.get(0).get("belum"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        requestQueue.add(stringRequest);

        stringRequest1 = new StringRequest(Request.Method.GET, url1, new com.android.volley.Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("jumlahsedang");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("sedang", json.getString("sedang"));

                        list_data1.add(map);
                    }

                    jumsedang.setText(list_data1.get(0).get("sedang"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


        requestQueue1.add(stringRequest1);


        stringRequest2 = new StringRequest(Request.Method.GET, url2, new com.android.volley.Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("jumlahselesai");
                    for (int a = 0; a < jsonArray.length(); a ++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map  = new HashMap<String, String>();
                        map.put("selesai", json.getString("selesai"));

                        list_data2.add(map);
                    }

                    jumselesai.setText(list_data2.get(0).get("selesai"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Reading Detail "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue2.add(stringRequest2);



        belum.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                Intent goPindah = new Intent(getActivity(), LaporBelum.class);
                startActivity(goPindah);
            }

        });

        sedang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPindah = new Intent(getActivity(), LaporSedang.class);
                startActivity(goPindah);
            }
        });

        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPindah = new Intent(getActivity(), LaporSelesai.class);
                startActivity(goPindah);
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("kecelakaan");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( getActivity(),  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });

        return view_home;
    }




}
