package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardResultVo {
    @SerializedName("cpengajaran")
    @Expose
    private String cPengajaran;
    @SerializedName("cpenelitian")
    @Expose
    private String cpenelitian;
    @SerializedName("cpengabdian")
    @Expose
    private String cpengabdian;
    @SerializedName("clainnya")
    @Expose
    private String clainnya;


    public DashboardResultVo() {
    }


    public DashboardResultVo(String cPengajaran, String cpenelitian, String cpengabdian, String clainnya) {
        this.cPengajaran = cPengajaran;
        this.cpenelitian = cpenelitian;
        this.cpengabdian = cpengabdian;
        this.clainnya = clainnya;
    }

    public String getcPengajaran() {
        return cPengajaran;
    }

    public void setcPengajaran(String cPengajaran) {
        this.cPengajaran = cPengajaran;
    }

    public String getCpenelitian() {
        return cpenelitian;
    }

    public void setCpenelitian(String cpenelitian) {
        this.cpenelitian = cpenelitian;
    }

    public String getCpengabdian() {
        return cpengabdian;
    }

    public void setCpengabdian(String cpengabdian) {
        this.cpengabdian = cpengabdian;
    }

    public String getClainnya() {
        return clainnya;
    }

    public void setClainnya(String clainnya) {
        this.clainnya = clainnya;
    }

}

