package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class ProdiModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("status")
    @Expose
    private Integer status;

    public ProdiModel() {
        this.id = UUID.randomUUID().toString();
        this.nama = "";
        this.status = 0;
    }

    public ProdiModel(String id, String nama, Integer status) {
        this.id = id;
        this.nama = nama;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
