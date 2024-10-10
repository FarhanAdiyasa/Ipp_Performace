package com.polytechnic.astra.ac.id.ippperformance.API.VO;

public class PekerjaanViewVO {

    private String pkjId;

    private String pkjNama;

    private String pkjKeterangan;

    private String ktgNama;

    private String itvSemester;
    private String tahunAjaran;

    private String pkjStatus;

    private String pkjCreaby;

    private String pkjCreadate;

    private String pkjModiby;

    private String pkjModidate;

    public PekerjaanViewVO() {
    }

    public PekerjaanViewVO(String pkjId, String pkjNama, String pkjKeterangan, String ktgNama, String itvSemester, String tahunAjaran, String pkjStatus, String pkjCreaby, String pkjCreadate, String pkjModiby, String pkjModidate) {
        this.pkjId = pkjId;
        this.pkjNama = pkjNama;
        this.pkjKeterangan = pkjKeterangan;
        this.ktgNama = ktgNama;
        this.itvSemester = itvSemester;
        this.tahunAjaran = tahunAjaran;
        this.pkjStatus = pkjStatus;
        this.pkjCreaby = pkjCreaby;
        this.pkjCreadate = pkjCreadate;
        this.pkjModiby = pkjModiby;
        this.pkjModidate = pkjModidate;
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

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getPkjId() {
        return pkjId;
    }

    public void setPkjId(String pkjId) {
        this.pkjId = pkjId;
    }

    public String getPkjNama() {
        return pkjNama;
    }

    public void setPkjNama(String pkjNama) {
        this.pkjNama = pkjNama;
    }

    public String getPkjKeterangan() {
        return pkjKeterangan;
    }

    public void setPkjKeterangan(String pkjKeterangan) {
        this.pkjKeterangan = pkjKeterangan;
    }

    public String getktgNama() {
        return ktgNama;
    }

    public void setktgNama(String ktgNama) {
        this.ktgNama = ktgNama;
    }

    public String getitvSemester() {
        return itvSemester;
    }

    public void setitvSemester(String itvSemester) {
        this.itvSemester = itvSemester;
    }

    public String getPkjStatus() {
        return pkjStatus;
    }

    public void setPkjStatus(String pkjStatus) {
        this.pkjStatus = pkjStatus;
    }

    public String getPkjCreaby() {
        return pkjCreaby;
    }

    public void setPkjCreaby(String pkjCreaby) {
        this.pkjCreaby = pkjCreaby;
    }

    public String getPkjCreadate() {
        return pkjCreadate;
    }

    public void setPkjCreadate(String pkjCreadate) {
        this.pkjCreadate = pkjCreadate;
    }

    public String getPkjModiby() {
        return pkjModiby;
    }

    public void setPkjModiby(String pkjModiby) {
        this.pkjModiby = pkjModiby;
    }

    public String getPkjModidate() {
        return pkjModidate;
    }

    public void setPkjModidate(String pkjModidate) {
        this.pkjModidate = pkjModidate;
    }
}