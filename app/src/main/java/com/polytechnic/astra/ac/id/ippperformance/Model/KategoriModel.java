package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KategoriModel {

    @SerializedName("ktg_id")
    @Expose
    private String ktg_id;

    @SerializedName("ktg_nama")
    @Expose
    private String ktg_nama;

    @SerializedName("ktg_status")
    @Expose
    private String ktg_status;

    public KategoriModel() {
    }

    public KategoriModel(String ktg_id, String ktg_nama, String ktg_status) {
        this.ktg_id = ktg_id;
        this.ktg_nama = ktg_nama;
        this.ktg_status = ktg_status;
    }

    public String getKtg_id() {
        return ktg_id;
    }

    public void setKtg_id(String ktg_id) {
        this.ktg_id = ktg_id;
    }

    public String getKtg_nama() {
        return ktg_nama;
    }

    public void setKtg_nama(String ktg_nama) {
        this.ktg_nama = ktg_nama;
    }

    public String getKtg_status() {
        return ktg_status;
    }

    public void setKtg_status(String ktg_status) {
        this.ktg_status = ktg_status;
    }
}
