package com.example.kattyapplication.Models;

import java.util.Date;

public class Remind {
    private int id;
    private String thoiGian;
    private String noiDung;
    private int trangThai;


    public Remind(int id, String thoiGian, String noiDung, int trangThai) {
        this.id = id;
        this.thoiGian = thoiGian;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
    }

    public Remind(String thoiGian, String noiDung, int trangThai) {
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

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
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

    @Override
    public String toString() {
        return "Remind{" +
                "id=" + id +
                ", thoiGian=" + thoiGian +
                ", noiDung='" + noiDung + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
