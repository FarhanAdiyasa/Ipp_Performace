package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.polytechnic.astra.ac.id.ippperformance.Model.KategoriModel;
import com.polytechnic.astra.ac.id.ippperformance.Model.PekerjaanModel;

import java.util.List;

public class CreateDetailPekerjaanVO {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pekerjaan")
    @Expose
    private List<PekerjaanModel> pekerjaan;

    @SerializedName("kategori")
    @Expose
    private List<KategoriModel> kategori;

    public CreateDetailPekerjaanVO(String title, List<PekerjaanModel> pekerjaan, List<KategoriModel> kategori) {
        this.title = title;
        this.pekerjaan = pekerjaan;
        this.kategori = kategori;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PekerjaanModel> getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(List<PekerjaanModel> pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public List<KategoriModel> getKategori() {
        return kategori;
    }

    public void setKategori(List<KategoriModel> kategori) {
        this.kategori = kategori;
    }
}
