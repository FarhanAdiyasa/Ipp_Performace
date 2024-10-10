package com.polytechnic.astra.ac.id.ippperformance.API.VO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDetailPekerjaanVO {
    @SerializedName("dtp_nama")
    @Expose
    private String dtp_nama;

    @SerializedName("ktg_id")
    @Expose
    private String ktg_id;

    @SerializedName("pkj_id")
    @Expose
    private String pkj_id;

    @SerializedName("dtp_tanggal")
    @Expose
    private String dtp_tanggal;

    @SerializedName("dtp_rencana_jam_awal")
    @Expose
    private String dtp_rencana_jam_awal;

    @SerializedName("dtp_rencana_jam_akhir")
    @Expose
    private String dtp_rencana_jam_akhir;

    @SerializedName("dtp_bukti_foto")
    @Expose
    private String dtp_bukti_foto;

    public AddDetailPekerjaanVO(String dtp_nama, String ktg_id, String pkj_id, String dtp_tanggal, String dtp_rencana_jam_awal, String dtp_rencana_jam_akhir, String dtp_bukti_foto) {
        this.dtp_nama = dtp_nama;
        this.ktg_id = ktg_id;
        this.pkj_id = pkj_id;
        this.dtp_tanggal = dtp_tanggal;
        this.dtp_rencana_jam_awal = dtp_rencana_jam_awal;
        this.dtp_rencana_jam_akhir = dtp_rencana_jam_akhir;
        this.dtp_bukti_foto = dtp_bukti_foto;
    }

    public String getDtp_bukti_foto() {
        return dtp_bukti_foto;
    }

    public void setDtp_bukti_foto(String dtp_bukti_foto) {
        this.dtp_bukti_foto = dtp_bukti_foto;
    }

    public String getDtp_nama() {
        return dtp_nama;
    }

    public void setDtp_nama(String dtp_nama) {
        this.dtp_nama = dtp_nama;
    }

    public String getKtg_id() {
        return ktg_id;
    }

    public void setKtg_id(String ktg_id) {
        this.ktg_id = ktg_id;
    }

    public String getPkj_id() {
        return pkj_id;
    }

    public void setPkj_id(String pkj_id) {
        this.pkj_id = pkj_id;
    }

    public String getDtp_tanggal() {
        return dtp_tanggal;
    }

    public void setDtp_tanggal(String dtp_tanggal) {
        this.dtp_tanggal = dtp_tanggal;
    }

    public String getDtp_rencana_jam_awal() {
        return dtp_rencana_jam_awal;
    }

    public void setDtp_rencana_jam_awal(String dtp_rencana_jam_awal) {
        this.dtp_rencana_jam_awal = dtp_rencana_jam_awal;
    }

    public String getDtp_rencana_jam_akhir() {
        return dtp_rencana_jam_akhir;
    }

    public void setDtp_rencana_jam_akhir(String dtp_rencana_jam_akhir) {
        this.dtp_rencana_jam_akhir = dtp_rencana_jam_akhir;
    }
}
