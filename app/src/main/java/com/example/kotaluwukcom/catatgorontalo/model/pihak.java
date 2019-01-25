package com.example.kotaluwukcom.catatgorontalo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class pihak {

    @SerializedName("id_pihak")
    @Expose
    private String idpihak;

    @SerializedName("nama_pihak")
    @Expose
    private  String namapihak;

    @SerializedName("umur_pihak")
    @Expose
    private  String umur_pihak;

    @SerializedName("pekerjaan")
    @Expose
    private String pekerjaan;

    @SerializedName("alamat")
    @Expose
    private String alamat;

    @SerializedName("jenis_kendaraan")
    @Expose
    private String jeniskendaraan;

    @SerializedName("plat_kendaraan")
    @Expose
    private  String platkendaraan;

    public String getIdpihak() {
        return idpihak;
    }

    public void setIdpihak(String idpihak) {
        this.idpihak = idpihak;
    }

    public String getNamapihak() {
        return namapihak;
    }

    public void setNamapihak(String namapihak) {
        this.namapihak = namapihak;
    }

    public String getUmur_pihak() {
        return umur_pihak;
    }

    public void setUmur_pihak(String umur_pihak) {
        this.umur_pihak = umur_pihak;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJeniskendaraan() {
        return jeniskendaraan;
    }

    public void setJeniskendaraan(String jeniskendaraan) {
        this.jeniskendaraan = jeniskendaraan;
    }

    public String getPlatkendaraan() {
        return platkendaraan;
    }

    public void setPlatkendaraan(String platkendaraan) {
        this.platkendaraan = platkendaraan;
    }
}
