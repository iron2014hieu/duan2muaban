package com.example.duan2muaban.model;

public class DatMua {
    private boolean isSelected;
    int masach;
    String sanpham;
    int gia;
    int soluong;
    int tongtien;
    String hinhanh;

    public DatMua() {
    }

    public DatMua(boolean isSelected, int masach, String sanpham, int gia, int soluong, int tongtien, String hinhanh) {
        this.isSelected = isSelected;
        this.masach = masach;
        this.sanpham = sanpham;
        this.gia = gia;
        this.soluong = soluong;
        this.tongtien = tongtien;
        this.hinhanh = hinhanh;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
