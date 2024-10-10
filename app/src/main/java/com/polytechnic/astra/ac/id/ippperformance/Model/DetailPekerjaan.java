package com.polytechnic.astra.ac.id.ippperformance.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPekerjaan {

    @SerializedName("dtp_id")
    @Expose
    private String dtpId;

    @SerializedName("dtp_nama")
    @Expose
    private String dtpNama;

    @SerializedName("dtp_tanggal")
    @Expose
    private String dtpTanggal;

    @SerializedName("dtp_rencana_jam_awal")
    @Expose
    private String dtpRencanaJamAwal;

    @SerializedName("dtp_rencana_jam_akhir")
    @Expose
    private String dtpRencanaJamAkhir;

    @SerializedName("dtp_aktual_jam_awal")
    @Expose
    private String dtpAktualJamAwal;

    @SerializedName("dtp_aktual_jam_akhir")
    @Expose
    private String dtpAktualJamAkhir;

    @SerializedName("dtp_status")
    @Expose
    private String dtpStatus;

    @SerializedName("pkj_id")
    @Expose
    private String pkjId;

    @SerializedName("dtp_creaby")
    @Expose
    private String dtpCreaBy;

    @SerializedName("dtp_creadate")
    @Expose
    private String dtpCreaDate;

    @SerializedName("dtp_modiby")
    @Expose
    private String dtpModiBy;

    @SerializedName("dtp_modidate")
    @Expose
    private String dtpModiDate;

    public DetailPekerjaan() {
    }

    public DetailPekerjaan(String dtpId, String dtpNama, String dtpTanggal, String dtpRencanaJamAwal,
                           String dtpRencanaJamAkhir, String dtpAktualJamAwal, String dtpAktualJamAkhir,
                           String dtpStatus, String pkjId, String dtpCreaBy, String dtpCreaDate,
                           String dtpModiBy, String dtpModiDate) {
        this.dtpId = dtpId;
        this.dtpNama = dtpNama;
        this.dtpTanggal = dtpTanggal;
        this.dtpRencanaJamAwal = dtpRencanaJamAwal;
        this.dtpRencanaJamAkhir = dtpRencanaJamAkhir;
        this.dtpAktualJamAwal = dtpAktualJamAwal;
        this.dtpAktualJamAkhir = dtpAktualJamAkhir;
        this.dtpStatus = dtpStatus;
        this.pkjId = pkjId;
        this.dtpCreaBy = dtpCreaBy;
        this.dtpCreaDate = dtpCreaDate;
        this.dtpModiBy = dtpModiBy;
        this.dtpModiDate = dtpModiDate;
    }

    public String getDtpId() {
        return dtpId;
    }

    public void setDtpId(String dtpId) {
        this.dtpId = dtpId;
    }

    public String getDtpNama() {
        return dtpNama;
    }

    public void setDtpNama(String dtpNama) {
        this.dtpNama = dtpNama;
    }

    public String getDtpTanggal() {
        return dtpTanggal;
    }

    public void setDtpTanggal(String dtpTanggal) {
        this.dtpTanggal = dtpTanggal;
    }

    public String getDtpRencanaJamAwal() {
        return dtpRencanaJamAwal;
    }

    public void setDtpRencanaJamAwal(String dtpRencanaJamAwal) {
        this.dtpRencanaJamAwal = dtpRencanaJamAwal;
    }

    public String getDtpRencanaJamAkhir() {
        return dtpRencanaJamAkhir;
    }

    public void setDtpRencanaJamAkhir(String dtpRencanaJamAkhir) {
        this.dtpRencanaJamAkhir = dtpRencanaJamAkhir;
    }

    public String getDtpAktualJamAwal() {
        return dtpAktualJamAwal;
    }

    public void setDtpAktualJamAwal(String dtpAktualJamAwal) {
        this.dtpAktualJamAwal = dtpAktualJamAwal;
    }

    public String getDtpAktualJamAkhir() {
        return dtpAktualJamAkhir;
    }

    public void setDtpAktualJamAkhir(String dtpAktualJamAkhir) {
        this.dtpAktualJamAkhir = dtpAktualJamAkhir;
    }

    public String getDtpStatus() {
        return dtpStatus;
    }

    public void setDtpStatus(String dtpStatus) {
        this.dtpStatus = dtpStatus;
    }

    public String getPkjId() {
        return pkjId;
    }

    public void setPkjId(String pkjId) {
        this.pkjId = pkjId;
    }

    public String getDtpCreaBy() {
        return dtpCreaBy;
    }

    public void setDtpCreaBy(String dtpCreaBy) {
        this.dtpCreaBy = dtpCreaBy;
    }

    public String getDtpCreaDate() {
        return dtpCreaDate;
    }

    public void setDtpCreaDate(String dtpCreaDate) {
        this.dtpCreaDate = dtpCreaDate;
    }

    public String getDtpModiBy() {
        return dtpModiBy;
    }

    public void setDtpModiBy(String dtpModiBy) {
        this.dtpModiBy = dtpModiBy;
    }

    public String getDtpModiDate() {
        return dtpModiDate;
    }

    public void setDtpModiDate(String dtpModiDate) {
        this.dtpModiDate = dtpModiDate;
    }
}
