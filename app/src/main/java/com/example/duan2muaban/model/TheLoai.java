package com.example.duan2muaban.model;

public class TheLoai {
    private int maLoai;
    private String tenLoai;
    private String image;

    public TheLoai(int maLoai, String tenLoai, String image) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.image = image;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
