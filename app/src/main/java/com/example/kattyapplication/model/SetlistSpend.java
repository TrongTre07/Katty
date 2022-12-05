package com.example.kattyapplication.model;

public class SetlistSpend {
    private int id;
    private String loaiTieuDung;
    private int giaTien;
    private int idThuCung;
    private String ngayChiTieu;

    public SetlistSpend(int id, String loaiTieuDung, int giaTien, int idThuCung, String ngayChiTieu) {
        this.id = id;
        this.loaiTieuDung = loaiTieuDung;
        this.giaTien = giaTien;
        this.idThuCung = idThuCung;
        this.ngayChiTieu = ngayChiTieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNgayChiTieu() {
        return ngayChiTieu;
    }

    public void setNgayChiTieu(String ngayChiTieu) {
        this.ngayChiTieu = ngayChiTieu;
    }

    @Override
    public String toString() {
        return "SetlistSpend{" +
                "id=" + id +
                ", loaiTieuDung='" + loaiTieuDung + '\'' +
                ", giaTien=" + giaTien +
                ", idThuCung=" + idThuCung +
                ", ngayChiTieu='" + ngayChiTieu + '\'' +
                '}';
    }
}
