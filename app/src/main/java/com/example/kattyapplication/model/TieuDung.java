package com.example.kattyapplication.model;

public class TieuDung {
    private String loaiTieuDung;
    private int giaTien;
    private int idThuCung;

    public TieuDung() {
    }

    public TieuDung(String loaiTieuDung, int giaTien, int idThuCung) {
        this.loaiTieuDung = loaiTieuDung;
        this.giaTien = giaTien;
        this.idThuCung = idThuCung;
    }

    public String getLoaiTieuDung() {
        return loaiTieuDung;
    }

    public void setLoaiTieuDung(String loaiTieuDung) {
        this.loaiTieuDung = loaiTieuDung;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getIdThuCung() {
        return idThuCung;
    }

    public void setIdThuCung(int idThuCung) {
        this.idThuCung = idThuCung;
    }
}
