package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {

    @SerializedName("kry_id")
    @Expose
    private String kry_id;

    @SerializedName("kry_npk")
    @Expose
    private String kry_npk;

    @SerializedName("kry_nama")
    @Expose
    private String kry_nama;

    @SerializedName("kry_username")
    @Expose
    private String kry_username;

    @SerializedName("kry_password")
    @Expose
    private String kry_password;

    @SerializedName("kry_jab_akademik")
    @Expose
    private String kry_jab_akademik;

    @SerializedName("kry_foto")
    @Expose
    private String kry_foto;

    @SerializedName("kry_status")
    @Expose
    private String kry_status;

    @SerializedName("jab_id")
    @Expose
    private String jab_id;

    @SerializedName("kry_creaby")
    @Expose
    private String kry_creaby;

    @SerializedName("kry_creadate")
    @Expose
    private String kry_creadate;

    @SerializedName("kry_modiby")
    @Expose
    private String kry_modiby;

    @SerializedName("kry_modidate")
    @Expose
    private String kry_modidate;

    public UserModel() {
    }

    public UserModel(String kry_id, String kry_npk, String kry_nama, String kry_username, String kry_password, String kry_jab_akademik, String kry_foto, String kry_status, String jab_id, String kry_creaby, String kry_creadate, String kry_modiby, String kry_modidate) {
        this.kry_id = kry_id;
        this.kry_npk = kry_npk;
        this.kry_nama = kry_nama;
        this.kry_username = kry_username;
        this.kry_password = kry_password;
        this.kry_jab_akademik = kry_jab_akademik;
        this.kry_foto = kry_foto;
        this.kry_status = kry_status;
        this.jab_id = jab_id;
        this.kry_creaby = kry_creaby;
        this.kry_creadate = kry_creadate;
        this.kry_modiby = kry_modiby;
        this.kry_modidate = kry_modidate;
    }

    public String getKry_id() {
        return kry_id;
    }

    public void setKry_id(String kry_id) {
        this.kry_id = kry_id;
    }

    public String getKry_npk() {
        return kry_npk;
    }

    public void setKry_npk(String kry_npk) {
        this.kry_npk = kry_npk;
    }

    public String getKry_nama() {
        return kry_nama;
    }

    public void setKry_nama(String kry_nama) {
        this.kry_nama = kry_nama;
    }

    public String getKry_username() {
        return kry_username;
    }

    public void setKry_username(String kry_username) {
        this.kry_username = kry_username;
    }

    public String getKry_password() {
        return kry_password;
    }

    public void setKry_password(String kry_password) {
        this.kry_password = kry_password;
    }

    public String getKry_jab_akademik() {
        return kry_jab_akademik;
    }

    public void setKry_jab_akademik(String kry_jab_akademik) {
        this.kry_jab_akademik = kry_jab_akademik;
    }

    public String getKry_foto() {
        return kry_foto;
    }

    public void setKry_foto(String kry_foto) {
        this.kry_foto = kry_foto;
    }

    public String getKry_status() {
        return kry_status;
    }

    public void setKry_status(String kry_status) {
        this.kry_status = kry_status;
    }

    public String getJab_id() {
        return jab_id;
    }

    public void setJab_id(String jab_id) {
        this.jab_id = jab_id;
    }

    public String getKry_creaby() {
        return kry_creaby;
    }

    public void setKry_creaby(String kry_creaby) {
        this.kry_creaby = kry_creaby;
    }

    public String getKry_creadate() {
        return kry_creadate;
    }

    public void setKry_creadate(String kry_creadate) {
        this.kry_creadate = kry_creadate;
    }

    public String getKry_modiby() {
        return kry_modiby;
    }

    public void setKry_modiby(String kry_modiby) {
        this.kry_modiby = kry_modiby;
    }

    public String getKry_modidate() {
        return kry_modidate;
    }

    public void setKry_modidate(String kry_modidate) {
        this.kry_modidate = kry_modidate;
    }
}
