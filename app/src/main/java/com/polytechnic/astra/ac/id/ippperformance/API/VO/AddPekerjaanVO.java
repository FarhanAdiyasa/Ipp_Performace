package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPekerjaanVO {
    @SerializedName("pkj_id")
    @Expose
    private String pkj_id;
    @SerializedName("pkj_nama")
    @Expose
    private String pkj_nama;

    @SerializedName("ktg_id")
    @Expose
    private String ktg_id;

    @SerializedName("itv_id")
    @Expose
    private String itv_id;

    @SerializedName("pkj_keterangan")
    @Expose
    private String pkj_keterangan;

    public AddPekerjaanVO(){

    }
    @Override
    public String toString() {
        return "AddPekerjaanVO{" +
                "pkjId='" + pkj_id + '\'' +
                ", pkjNama='" + pkj_nama + '\'' +
                ", pkjKeterangan='" + pkj_keterangan + '\'' +
                ", ktgId='" + ktg_id + '\'' +
                ", itvId='" + itv_id + '\'' +
                '}';
    }
    public AddPekerjaanVO(String pkj_id, String pkj_nama, String ktg_id, String itv_id, String pkj_keterangan) {
        this.pkj_id = pkj_id;
        this.pkj_nama = pkj_nama;
        this.ktg_id = ktg_id;
        this.itv_id = itv_id;
        this.pkj_keterangan = pkj_keterangan;
    }

    public String getPkj_id() {
        return pkj_id;
    }

    public void setPkj_id(String pkj_id) {
        this.pkj_id = pkj_id;
    }

    public String getPkj_nama() {
        return pkj_nama;
    }

    public void setPkj_nama(String pkj_nama) {
        this.pkj_nama = pkj_nama;
    }

    public String getKtg_id() {
        return ktg_id;
    }

    public void setKtg_id(String ktg_id) {
        this.ktg_id = ktg_id;
    }

    public String getItv_id() {
        return itv_id;
    }

    public void setItv_id(String itv_id) {
        this.itv_id = itv_id;
    }

    public String getPkj_keterangan() {
        return pkj_keterangan;
    }

    public void setPkj_keterangan(String pkj_keterangan) {
        this.pkj_keterangan = pkj_keterangan;
    }
}

