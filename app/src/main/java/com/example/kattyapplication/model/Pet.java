package com.example.kattyapplication.model;

public class Pet {
    private String tenThuCung;
    private String loai;

    public Pet(String tenThuCung, String loai) {
        this.tenThuCung = tenThuCung;
        this.loai = loai;
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
}
