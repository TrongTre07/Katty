package com.example.kattyapplication.model;

public class Infor_pet {
    private int id;
    private String tenThuCung;
    private  String loai;

    public Infor_pet() {
    }

    public Infor_pet(int id, String tenThuCung, String loai) {
        this.id = id;
        this.tenThuCung = tenThuCung;
        this.loai = loai;
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
}
