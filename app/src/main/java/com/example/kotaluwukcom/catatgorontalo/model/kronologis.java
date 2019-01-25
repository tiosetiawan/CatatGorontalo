package com.example.kotaluwukcom.catatgorontalo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class kronologis {
    @SerializedName("id_kronologis")
    @Expose
    private String idkornologis;

    @SerializedName("foto")
    @Expose
    private  String foto;

    @SerializedName("kronologis")
    @Expose
    private  String kronologis;

    public String getIdkornologis() {
        return idkornologis;
    }

    public void setIdkornologis(String idkornologis) {
        this.idkornologis = idkornologis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKronologis() {
        return kronologis;
    }

    public void setKronologis(String kronologis) {
        this.kronologis = kronologis;
    }
}
