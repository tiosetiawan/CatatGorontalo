package com.example.kotaluwukcom.catatgorontalo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.model.ListLapor;
import com.example.kotaluwukcom.catatgorontalo.network.Config;
import com.example.kotaluwukcom.catatgorontalo.network.RetrofitBuilder;
import com.example.kotaluwukcom.catatgorontalo.network.interfaces.LihatInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetaFragment extends Fragment implements OnMapReadyCallback {

    private static final int RC_CAMERA_AND_LOCATION = 100;
    private SupportMapFragment mapFragment;

    private GoogleMap mMap;
    ArrayList<LaporData> listData;
    Double lat;
    Double lng;
    String status;
    private String url = Config.API_URL;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_peta, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
        listData = new ArrayList<>();




        return view;
    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ambilData();

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(0.559098, 123.0455238);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Kantor").snippet("Aplikasi Lapor Gorontalo").draggable(true)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.cs))
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14f));
        //intent ke map app


    }



    private void ambilData() {
        /*final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Mohon Bersabar");
        progress.show();
*/
        LihatInterface api = RetrofitBuilder.getLihatService();
        retrofit2.Call<ListLapor> call = api.ambilDataLapor();
        call.enqueue(new Callback<ListLapor>() {
            @Override
            public void onResponse(Call<ListLapor> call, Response<ListLapor> response) {
                //     progress.hide();
                if (response.isSuccessful()){
                    if(response.body().getSuccess().toString().equals("true")){
                        listData = response.body().getLapor();

                        for (int i = 0; i < listData.size(); i++) {

                            Double lat = Double.valueOf(listData.get(i).getLatitudeLapor());
                            Double lng = Double.valueOf(listData.get(i).getLongtitudeLapor());
                            String status = String.valueOf(listData.get(i).getStatus());
                            LatLng sydney = new LatLng(lat,lng);

                            if(status.equals("belum diproses")) {
                                mMap.addMarker(new MarkerOptions().position(sydney).title(listData.get(i).getNama())
                                        .snippet(listData.get(i).getDeskripsi())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.titikmerah))
                                );
                            }else if(status.equals("sedang diproses")){
                                mMap.addMarker(new MarkerOptions().position(sydney).title(listData.get(i).getNama())
                                        .snippet(listData.get(i).getDeskripsi())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.titikkuning))
                                );
                            }else if(status.equals("selesai diproses")){
                                mMap.addMarker(new MarkerOptions().position(sydney).title(listData.get(i).getNama())
                                        .snippet(listData.get(i).getDeskripsi())
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.titikhijau))
                                );
                            }


                            String TAG ="aaa";
                            Log.d(TAG, "onResponse: " + listData.get(i).getFoto());
                        }
                    } else {
                        //Toast.makeText(this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(getActivity(), "Respones is Not Succesfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListLapor> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
