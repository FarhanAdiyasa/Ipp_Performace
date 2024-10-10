package com.polytechnic.astra.ac.id.ippperformance.API.VO;

public class RekapitulasiViewVO {
    private String Kategori;
    private String Pekerjaan;
    private String Keterangan;
    private String Persentase;

    public RekapitulasiViewVO() {

    }
    public RekapitulasiViewVO(String kategori, String pekerjaan, String keterangan, String persentase) {
        Kategori = kategori;
        Pekerjaan = pekerjaan;
        Keterangan = keterangan;
        Persentase = persentase;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getPekerjaan() {
        return Pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        Pekerjaan = pekerjaan;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getPersentase() {
        return Persentase;
    }

    public void setPersentase(String persentase) {
        Persentase = persentase;
    }
}