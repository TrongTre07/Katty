package com.example.kattyapplication.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class support {
    private Integer id;
    private String tenTrieuChung;
    private String noiDung;

    public support() {
    }

    public support(Integer id, String tenTrieuChung, String noiDung) {
        this.id = id;
        this.tenTrieuChung = tenTrieuChung;
        this.noiDung = noiDung;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenTrieuChung() {
        return tenTrieuChung;
    }

    public void setTenTrieuChung(String tenTrieuChung) {
        this.tenTrieuChung = tenTrieuChung;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    @Override
    public String toString() {
        return "support{" +
                "id=" + id +
                ", tenTrieuChung='" + tenTrieuChung + '\'' +
                ", noiDung='" + noiDung + '\'' +
                '}';
    }

}
