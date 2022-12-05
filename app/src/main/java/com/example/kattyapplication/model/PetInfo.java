package com.example.kattyapplication.model;


import java.util.Date;

public class PetInfo {
    private int id;
    private String tenThuCung;
    private String loai;
    private float canNang;
    private int tuoi;
    private String ngaySinh;
    private String thongTinKhac;

    public PetInfo(int id, String tenThuCung, String loai, float canNang, int tuoi, String ngaySinh, String thongTinKhac) {
        this.id = id;
        this.tenThuCung = tenThuCung;
        this.loai = loai;
        this.canNang = canNang;
        this.tuoi = tuoi;
        this.ngaySinh = ngaySinh;
        this.thongTinKhac = thongTinKhac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getCanNang() {
        return canNang;
    }

    public void setCanNang(float canNang) {
        this.canNang = canNang;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getThongTinKhac() {
        return thongTinKhac;
    }

    public void setThongTinKhac(String thongTinKhac) {
        this.thongTinKhac = thongTinKhac;
    }
}
