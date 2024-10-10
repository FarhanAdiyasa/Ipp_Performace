package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polytechnic.astra.ac.id.ippperformance.Model.IntervalModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;

import java.util.List;

public class PekerjaanVO {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("kategori")
    @Expose
    private List<KategoriModel> kategori;

    @SerializedName("interval")
    @Expose
    private List<IntervalModel> interval;

    @SerializedName("pekerjaan")
    @Expose
    private List<PekerjaanModel> pekerjaan;

    public PekerjaanVO() {
    }

    public PekerjaanVO(String title, List<KategoriModel> kategori, List<IntervalModel> interval, List<PekerjaanModel> pekerjaan) {
        this.title = title;
        this.kategori = kategori;
        this.interval = interval;
        this.pekerjaan = pekerjaan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<KategoriModel> getKategori() {
        return kategori;
    }

    public void setKategori(List<KategoriModel> kategori) {
        this.kategori = kategori;
    }

    public List<IntervalModel> getInterval() {
        return interval;
    }

    public void setInterval(List<IntervalModel> interval) {
        this.interval = interval;
    }

    public List<PekerjaanModel> getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(List<PekerjaanModel> pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
}
