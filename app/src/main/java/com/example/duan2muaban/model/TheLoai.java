package com.example.duan2muaban.model;

public class TheLoai {
    private int ID;
    private int MaCuaHang;
    private String TenTheLoai;
    private String MoTa;
    private String PhoTo;

    public TheLoai(int ID, int maCuaHang, String tenTheLoai, String moTa, String phoTo) {
        this.ID = ID;
        MaCuaHang = maCuaHang;
        TenTheLoai = tenTheLoai;
        MoTa = moTa;
        PhoTo = phoTo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMaCuaHang() {
        return MaCuaHang;
    }

    public void setMaCuaHang(int maCuaHang) {
        MaCuaHang = maCuaHang;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getPhoTo() {
        return PhoTo;
    }

    public void setPhoTo(String phoTo) {
        PhoTo = phoTo;
    }
}
