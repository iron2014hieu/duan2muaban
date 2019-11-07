package com.example.duan2muaban.model;

public class DatMua {
    int masach;
    String sanpham;
    int gia;
    int soluong;
    int tongtien;

    public DatMua(int masach, String sanpham, int gia, int soluong, int tongtien) {
        this.masach = masach;
        this.sanpham = sanpham;
        this.gia = gia;
        this.soluong = soluong;
        this.tongtien = tongtien;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getSanpham() {
        return sanpham;
    }

    public void setSanpham(String sanpham) {
        this.sanpham = sanpham;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }
}
