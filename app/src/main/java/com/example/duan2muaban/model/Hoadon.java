package com.example.duan2muaban.model;

public class Hoadon {
    int id, masach, maUser;
    String tenSach, tenUser, giaBan, nhanxet;
    int dathanhtoan;
    Double diemdanhgia;
    int hienthi;

    public Hoadon(int id, int masach, int maUser, String tenSach, String tenUser, String giaBan, String nhanxet, int dathanhtoan, Double diemdanhgia, int hienthi) {
        this.id = id;
        this.masach = masach;
        this.maUser = maUser;
        this.tenSach = tenSach;
        this.tenUser = tenUser;
        this.giaBan = giaBan;
        this.nhanxet = nhanxet;
        this.dathanhtoan = dathanhtoan;
        this.diemdanhgia = diemdanhgia;
        this.hienthi = hienthi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public int getMaUser() {
        return maUser;
    }

    public void setMaUser(int maUser) {
        this.maUser = maUser;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public String getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(String giaBan) {
        this.giaBan = giaBan;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }

    public int getDathanhtoan() {
        return dathanhtoan;
    }

    public void setDathanhtoan(int dathanhtoan) {
        this.dathanhtoan = dathanhtoan;
    }

    public Double getDiemdanhgia() {
        return diemdanhgia;
    }

    public void setDiemdanhgia(Double diemdanhgia) {
        this.diemdanhgia = diemdanhgia;
    }

    public int getHienthi() {
        return hienthi;
    }

    public void setHienthi(int hienthi) {
        this.hienthi = hienthi;
    }
}
