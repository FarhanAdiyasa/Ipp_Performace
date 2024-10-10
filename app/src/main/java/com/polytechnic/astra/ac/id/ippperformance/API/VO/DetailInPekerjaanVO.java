package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polytechnic.astra.ac.id.ippperformance.Model.DetailPekerjaan;
import com.polytechnic.astra.ac.id.ippperformance.Model.IntervalModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;

import java.util.List;

public class DetailInPekerjaanVO {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pekerjaan")
    @Expose
    private PekerjaanModel pekerjaan;

    @SerializedName("kategori")
    @Expose
    private List<KategoriModel> kategori;

    @SerializedName("interval")
    @Expose
    private List<IntervalModel> interval;

    @SerializedName("detail_pekerjaan")
    @Expose
    private List<DetailPekerjaan> detail_pekerjaan;

    public DetailInPekerjaanVO() {
    }

    public DetailInPekerjaanVO(String title, PekerjaanModel pekerjaan, List<KategoriModel> kategori, List<IntervalModel> interval, List<DetailPekerjaan> detail_pekerjaan) {
        this.title = title;
        this.pekerjaan = pekerjaan;
        this.kategori = kategori;
        this.interval = interval;
        this.detail_pekerjaan = detail_pekerjaan;
    }

    public List<DetailPekerjaan> getDetail_pekerjaan() {
        return detail_pekerjaan;
    }

    public void setDetail_pekerjaan(List<DetailPekerjaan> detail_pekerjaan) {
        this.detail_pekerjaan = detail_pekerjaan;
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

    public PekerjaanModel getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(PekerjaanModel pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
}
