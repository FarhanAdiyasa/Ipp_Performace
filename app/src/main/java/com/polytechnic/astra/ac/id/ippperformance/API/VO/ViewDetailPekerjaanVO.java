package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewDetailPekerjaanVO {

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
    @SerializedName("dtp_bukti_foto")
    @Expose
    private String dtpBuktiFoto;
    private String dtpStatus;
    @SerializedName("pkj_nama")
    @Expose
    private String pkjNama;
    @SerializedName("ktg_nama")
    @Expose
    private String ktgNama;
    private String itvSemester;
    private String itvTahunAjaran;
    private String dtpCreaBy;
    private String dtpCreaDate;
    private String dtpModiBy;
    private String dtpModiDate;

    public ViewDetailPekerjaanVO(String dtpId, String dtpNama, String dtpTanggal, String dtpRencanaJamAwal, String dtpRencanaJamAkhir, String dtpAktualJamAwal, String dtpAktualJamAkhir, String dtpBuktiFoto, String dtpStatus, String pkjNama, String ktgNama, String itvSemester, String itvTahunAjaran, String dtpCreaBy, String dtpCreaDate, String dtpModiBy, String dtpModiDate) {
        this.dtpId = dtpId;
        this.dtpNama = dtpNama;
        this.dtpTanggal = dtpTanggal;
        this.dtpRencanaJamAwal = dtpRencanaJamAwal;
        this.dtpRencanaJamAkhir = dtpRencanaJamAkhir;
        this.dtpAktualJamAwal = dtpAktualJamAwal;
        this.dtpAktualJamAkhir = dtpAktualJamAkhir;
        this.dtpBuktiFoto = dtpBuktiFoto;
        this.dtpStatus = dtpStatus;
        this.pkjNama = pkjNama;
        this.ktgNama = ktgNama;
        this.itvSemester = itvSemester;
        this.itvTahunAjaran = itvTahunAjaran;
        this.dtpCreaBy = dtpCreaBy;
        this.dtpCreaDate = dtpCreaDate;
        this.dtpModiBy = dtpModiBy;
        this.dtpModiDate = dtpModiDate;
    }

    public ViewDetailPekerjaanVO(ViewDetailPekerjaanVO body) {
        this.dtpId = body.dtpId;
        this.dtpNama = body.dtpNama;
        this.dtpTanggal = body.dtpTanggal;
        this.dtpRencanaJamAwal = body.dtpRencanaJamAwal;
        this.dtpRencanaJamAkhir = body.dtpRencanaJamAkhir;
        this.dtpAktualJamAwal = body.dtpAktualJamAwal;
        this.dtpAktualJamAkhir = body.dtpAktualJamAkhir;
        this.dtpStatus = body.dtpStatus;
        this.pkjNama = body.pkjNama;
        this.ktgNama = body.ktgNama;
        this.itvSemester = body.itvSemester;
        this.itvTahunAjaran = body.itvTahunAjaran;
        this.dtpCreaBy = body.dtpCreaBy;
        this.dtpCreaDate = body.dtpCreaDate;
        this.dtpModiBy = body.dtpModiBy;
        this.dtpModiDate = body.dtpModiDate;
        this.dtpBuktiFoto = body.dtpBuktiFoto;
    }

    public String getItvTahunAjaran() {
        return itvTahunAjaran;
    }

    public void setItvTahunAjaran(String itvTahunAjaran) {
        this.itvTahunAjaran = itvTahunAjaran;
    }

    public ViewDetailPekerjaanVO() {

    }

    public String getDtpBuktiFoto() {
        return dtpBuktiFoto;
    }

    public void setDtpBuktiFoto(String dtpBuktiFoto) {
        this.dtpBuktiFoto = dtpBuktiFoto;
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

    public String getPkjNama() {
        return pkjNama;
    }

    public void setPkjNama(String pkjNama) {
        this.pkjNama = pkjNama;
    }

    public String getKtgNama() {
        return ktgNama;
    }

    public void setKtgNama(String ktgNama) {
        this.ktgNama = ktgNama;
    }

    public String getItvSemester() {
        return itvSemester;
    }

    public void setItvSemester(String itvSemester) {
        this.itvSemester = itvSemester;
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