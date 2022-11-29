package com.example.kattyapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportPost {

    private String tenTrieuChung;
    private String noiDung;

    public SupportPost() {
    }

    public SupportPost(String tenTrieuChung, String noiDung) {
        this.tenTrieuChung = tenTrieuChung;
        this.noiDung = noiDung;
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
        return "SupportPost{" +
                "tenTrieuChung='" + tenTrieuChung + '\'' +
                ", noiDung='" + noiDung + '\'' +
                '}';
    }

}
