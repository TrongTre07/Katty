package com.example.kattyapplication.model;

import java.util.Date;

public class Spend {
    private int id;
    private String loaiTieuDung;
    private int giaTien;
    private int idThuCung;
    private String ngayChiTieu;
    private String tenThuCung;
    private String loai;

    public Spend() {
    }


    public Spend(String loaiTieuDung, int giaTien, int idThuCung, String ngayChiTieu) {
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

    public String getTenThuCung() {
        return tenThuCung;
    }

    public void setTenThuCung(String tenThuCung) {
        this.tenThuCung = tenThuCung;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    @Override
    public String toString() {
        return "Spend{" +
                "id=" + id +
                ", loaiTieuDung='" + loaiTieuDung + '\'' +
                ", giaTien=" + giaTien +
                ", idThuCung=" + idThuCung +
                ", ngayChiTieu='" + ngayChiTieu + '\'' +
                ", tenThuCung='" + tenThuCung + '\'' +
                ", loai='" + loai + '\'' +
                '}';
    }
}
