package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RekapitulasiVO {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("pekerjaan")
    @Expose
    private List<RekapitulasiViewVO> pekerjaan;

    public RekapitulasiVO() {
    }

    public RekapitulasiVO(String title , List<RekapitulasiViewVO> pekerjaan) {
        this.title = title;
        this.pekerjaan = pekerjaan;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<RekapitulasiViewVO> getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(List<RekapitulasiViewVO> pekerjaan) {
        this.pekerjaan = pekerjaan;
    }
}