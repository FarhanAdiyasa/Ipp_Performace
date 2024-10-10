package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PekerjaanModel {

    @SerializedName("pkj_id")
    @Expose
    private String pkjId;

    @SerializedName("pkj_nama")
    @Expose
    private String pkjNama;

    @SerializedName("pkj_keterangan")
    @Expose
    private String pkjKeterangan;

    @SerializedName("ktg_id")
    @Expose
    private String ktgId;

    @SerializedName("itv_id")
    @Expose
    private String itvId;

    @SerializedName("pkj_status")
    @Expose
    private String pkjStatus;

    @SerializedName("pkj_creaby")
    @Expose
    private String pkjCreaby;

    @SerializedName("pkj_creadate")
    @Expose
    private String pkjCreadate;

    @SerializedName("pkj_modiby")
    @Expose
    private String pkjModiby;

    @SerializedName("pkj_modidate")
    @Expose
    private String pkjModidate;

    public PekerjaanModel() {
    }

    public PekerjaanModel(String pkjId, String pkjNama, String pkjKeterangan, String ktgId, String itvId, String pkjStatus, String pkjCreaby, String pkjCreadate, String pkjModiby, String pkjModidate) {
        this.pkjId = pkjId;
        this.pkjNama = pkjNama;
        this.pkjKeterangan = pkjKeterangan;
        this.ktgId = ktgId;
        this.itvId = itvId;
        this.pkjStatus = pkjStatus;
        this.pkjCreaby = pkjCreaby;
        this.pkjCreadate = pkjCreadate;
        this.pkjModiby = pkjModiby;
        this.pkjModidate = pkjModidate;
    }

    public PekerjaanModel(PekerjaanModel body) {
        this.pkjId = body.getPkjId();
        this.pkjNama = body.getPkjNama();
        this.pkjKeterangan = body.getPkjKeterangan();
        this.ktgId = body.getKtgId();
        this.itvId = body.getItvId();
        this.pkjStatus = body.getPkjStatus();
        this.pkjCreaby = body.getPkjCreaby();
        this.pkjCreadate = body.getPkjCreadate();
        this.pkjModiby = body.getPkjModiby();
        this.pkjModidate = body.getPkjModidate();
    }


    public String getPkjId() {
        return pkjId;
    }

    public void setPkjId(String pkjId) {
        this.pkjId = pkjId;
    }

    public String getPkjNama() {
        return pkjNama;
    }

    public void setPkjNama(String pkjNama) {
        this.pkjNama = pkjNama;
    }

    public String getPkjKeterangan() {
        return pkjKeterangan;
    }

    public void setPkjKeterangan(String pkjKeterangan) {
        this.pkjKeterangan = pkjKeterangan;
    }

    public String getKtgId() {
        return ktgId;
    }

    public void setKtgId(String ktgId) {
        this.ktgId = ktgId;
    }

    public String getItvId() {
        return itvId;
    }

    public void setItvId(String itvId) {
        this.itvId = itvId;
    }

    public String getPkjStatus() {
        return pkjStatus;
    }

    public void setPkjStatus(String pkjStatus) {
        this.pkjStatus = pkjStatus;
    }

    public String getPkjCreaby() {
        return pkjCreaby;
    }

    public void setPkjCreaby(String pkjCreaby) {
        this.pkjCreaby = pkjCreaby;
    }

    public String getPkjCreadate() {
        return pkjCreadate;
    }

    public void setPkjCreadate(String pkjCreadate) {
        this.pkjCreadate = pkjCreadate;
    }

    public String getPkjModiby() {
        return pkjModiby;
    }

    public void setPkjModiby(String pkjModiby) {
        this.pkjModiby = pkjModiby;
    }

    public String getPkjModidate() {
        return pkjModidate;
    }

    public void setPkjModidate(String pkjModidate) {
        this.pkjModidate = pkjModidate;
    }
}
