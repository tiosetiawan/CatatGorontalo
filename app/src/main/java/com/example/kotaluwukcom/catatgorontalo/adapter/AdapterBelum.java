package com.example.kotaluwukcom.catatgorontalo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.UpdateBelum;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.network.Config;

import java.util.ArrayList;

public class AdapterBelum extends RecyclerView.Adapter<AdapterBelum.MyHolder> {

    private ArrayList<LaporData> listData;
    private Context context;
    RequestManager  glide;
    private String urllap = Config.API_URL_FOTO_LAP;
    private String urlmas = Config.API_URL_FOTO_MAS;

    public AdapterBelum(ArrayList<LaporData> listData, Context context){

        this.listData = listData;
        this.context = context;
        glide = Glide.with(context);

    }

    public AdapterBelum.MyHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lapor, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;

    }


    public void onBindViewHolder (AdapterBelum.MyHolder holder, final int position){
        final LaporData laporData = listData.get(position);

        glide.load(urlmas+listData.get(position).getFoto_mas()).apply(RequestOptions.circleCropTransform())
                .into(holder.imgView);

        holder.id.setText(listData.get(position).getIdLaporan());

        holder.tv_name.setText(listData.get(position).getNama());
        holder.tv_time.setText(listData.get(position).getJamlapor());
        holder.tv_status.setText(listData.get(position).getDeskripsi());
        holder.status.setText(listData.get(position).getStatus());



        holder.petunjuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + listData.get(position).getLatitudeLapor() + "," + listData.get(position).getLongtitudeLapor() + "");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });


        if (laporData.getFoto() == null) {
            holder.imgPost.setVisibility(View.GONE);
        } else {
            holder.imgPost.setVisibility(View.VISIBLE);
            glide.load(urllap+listData.get(position).getFoto())
                    .into(holder.imgPost);
        }


    }

    public int getItemCount(){ return listData.size();}




    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgView, imgPost;
        TextView tv_name, tv_time, tv_status, status, id;
        Button petunjuk;


        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imgPost = (ImageView) itemView.findViewById(R.id.imgView_postPic);
            imgView = (ImageView) itemView.findViewById(R.id.imgView_proPic);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status =(TextView) itemView.findViewById(R.id.tv_status);
            petunjuk = (Button) itemView.findViewById(R.id.petunjuk);
            status = (TextView) itemView.findViewById(R.id.status);
            id     = (TextView) itemView.findViewById(R.id.idlapor);


        }

        public void onClick(View view) {
            String statusbelum = status.getText().toString();
            String id_laporan = id.getText().toString();

            Intent i = new Intent(context, UpdateBelum.class);
            i.putExtra("status", statusbelum);
            i.putExtra("id_laporan", id_laporan);
            context.startActivity(i);
        }

    }



}
