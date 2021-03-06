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
import com.example.kotaluwukcom.catatgorontalo.CatatKronologis;
import com.example.kotaluwukcom.catatgorontalo.CatatPihak;
import com.example.kotaluwukcom.catatgorontalo.LaporSedang;
import com.example.kotaluwukcom.catatgorontalo.R;
import com.example.kotaluwukcom.catatgorontalo.UpdateBelum;
import com.example.kotaluwukcom.catatgorontalo.model.LaporData;
import com.example.kotaluwukcom.catatgorontalo.network.Config;

import java.util.ArrayList;

public class AdapterSedang extends RecyclerView.Adapter<AdapterSedang.MyHolder> {

    private ArrayList<LaporData> listData;
    private Context context;
    RequestManager glide;
    private String urllap = Config.API_URL_FOTO_LAP;
    private String urlmas = Config.API_URL_FOTO_MAS;
    Button catatpihak, catatkronologis;
    TextView id;


    public AdapterSedang(ArrayList<LaporData> listData, Context context){

        this.listData = listData;
        this.context = context;
        glide = Glide.with(context);

    }

    public AdapterSedang.MyHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lapor_sedang, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;

    }

    public void onBindViewHolder (AdapterSedang.MyHolder holder, final int position){
        final LaporData laporData = listData.get(position);

        glide.load(urlmas+listData.get(position).getFoto_mas()).apply(RequestOptions.circleCropTransform())
                .into(holder.imgView);

        holder.id.setText(listData.get(position).getIdLaporan());
        holder.tv_name.setText(listData.get(position).getNama());
        holder.tv_time.setText(listData.get(position).getJamlapor());
        holder.tv_status.setText(listData.get(position).getDeskripsi());
        holder.statusupdate.setText(listData.get(position).getStatus());




        if (laporData.getFoto() == null) {
            holder.imgPost.setVisibility(View.GONE);
        } else {
            holder.imgPost.setVisibility(View.VISIBLE);
            glide.load(urllap+listData.get(position).getFoto())
                    .into(holder.imgPost);
        }


    }


    public int getItemCount(){ return listData.size();}


    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgView, imgPost;
        TextView tv_name, tv_time, tv_status, statusupdate, id;
        Button catatpihak, catatkronologis;


        public MyHolder(View itemView) {
            super(itemView);


            imgPost = (ImageView) itemView.findViewById(R.id.imgView_postPic);
            imgView = (ImageView) itemView.findViewById(R.id.imgView_proPic);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time   = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status =(TextView) itemView.findViewById(R.id.tv_status);
            statusupdate = (TextView) itemView.findViewById(R.id.statuss);
            id           = (TextView) itemView.findViewById(R.id.idlapor);
            catatpihak = (Button) itemView.findViewById(R.id.catatpihak);
            catatkronologis = (Button) itemView.findViewById(R.id.catatkronologis);

            catatpihak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_laporan = id.getText().toString();
                    // String nama = tv_name.getText().toString();
                    Intent i = new Intent(context, CatatPihak.class);

                    i.putExtra("id_laporan", id_laporan);
                    context.startActivity(i);
                }
            });

            catatkronologis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_laporan = id.getText().toString();
                    // String nama = tv_name.getText().toString();
                    Intent i = new Intent(context, CatatKronologis.class);

                    i.putExtra("id_laporan", id_laporan);
                    context.startActivity(i);
                }
            });



            statusupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String statusbelum = statusupdate.getText().toString();
                    String id_laporan = id.getText().toString();

                    Intent i = new Intent(context, UpdateBelum.class);
                    i.putExtra("status", statusbelum);
                    i.putExtra("id_laporan", id_laporan);
                    context.startActivity(i);
                }
            });

        }

       /* public void onClick(View view) {
            String id_laporan = id.getText().toString();
           // String nama = tv_name.getText().toString();


            Intent i = new Intent(context, LaporSedang.class);

            i.putExtra("id_laporan", id_laporan);
            context.startActivity(i);
        }*/



    }



}
