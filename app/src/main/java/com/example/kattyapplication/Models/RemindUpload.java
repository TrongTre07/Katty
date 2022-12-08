package com.example.kattyapplication.Models;

import java.util.Date;

public class RemindUpload {
    private int id;
    private Date thoiGian;
    private String noiDung;
    private int trangThai;

    public RemindUpload(int id, Date thoiGian, String noiDung, int trangThai) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public RemindUpload(Date thoiGian, String noiDung, int trangThai) {
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
