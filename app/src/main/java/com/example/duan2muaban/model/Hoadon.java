package com.example.duan2muaban.model;

public class Hoadon {
    int mahoadon, mauser,tongtien;
    String tenkh, diachi, sdt;

    public Hoadon(int mahoadon, int mauser, int tongtien, String tenkh, String diachi, String sdt) {
        this.mahoadon = mahoadon;
        this.mauser = mauser;
        this.tongtien = tongtien;
        this.tenkh = tenkh;
        this.diachi = diachi;
        this.sdt = sdt;
    }

    public Hoadon() {
    }

    public int getMahoadon() {
        return mahoadon;
    }

    public void setMahoadon(int mahoadon) {
        this.mahoadon = mahoadon;
    }

    public int getMauser() {
        return mauser;
    }

    public void setMauser(int mauser) {
        this.mauser = mauser;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
