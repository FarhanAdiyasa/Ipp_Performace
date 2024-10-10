package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IntervalModel {

    @SerializedName("itv_id")
    @Expose
    private String itv_id;

    @SerializedName("itv_semester")
    @Expose
    private String itv_semester;

    @SerializedName("itv_tahun_ajaran")
    @Expose
    private String itv_tahun_ajaran;

    @SerializedName("itv_awal_berlaku")
    @Expose
    private String itv_awal_berlaku;

    @SerializedName("itv_akhir_berlaku")
    @Expose
    private String itv_akhir_berlaku;

    @SerializedName("itv_status")
    @Expose
    private String itv_status;

    public IntervalModel() {
    }

    public IntervalModel(String itv_id, String itv_semester, String itv_tahun_ajaran, String itv_awal_berlaku, String itv_akhir_berlaku, String itv_status) {
        this.itv_id = itv_id;
        this.itv_semester = itv_semester;
        this.itv_tahun_ajaran = itv_tahun_ajaran;
        this.itv_awal_berlaku = itv_awal_berlaku;
        this.itv_akhir_berlaku = itv_akhir_berlaku;
        this.itv_status = itv_status;
    }

    public String getItv_id() {
        return itv_id;
    }

    public void setItv_id(String itv_id) {
        this.itv_id = itv_id;
    }

    public String getItv_semester() {
        return itv_semester;
    }

    public void setItv_semester(String itv_semester) {
        this.itv_semester = itv_semester;
    }

    public String getItv_tahun_ajaran() {
        return itv_tahun_ajaran;
    }

    public void setItv_tahun_ajaran(String itv_tahun_ajaran) {
        this.itv_tahun_ajaran = itv_tahun_ajaran;
    }

    public String getItv_awal_berlaku() {
        return itv_awal_berlaku;
    }

    public void setItv_awal_berlaku(String itv_awal_berlaku) {
        this.itv_awal_berlaku = itv_awal_berlaku;
    }

    public String getItv_akhir_berlaku() {
        return itv_akhir_berlaku;
    }

    public void setItv_akhir_berlaku(String itv_akhir_berlaku) {
        this.itv_akhir_berlaku = itv_akhir_berlaku;
    }

    public String getItv_status() {
        return itv_status;
    }

    public void setItv_status(String itv_status) {
        this.itv_status = itv_status;
    }
}
