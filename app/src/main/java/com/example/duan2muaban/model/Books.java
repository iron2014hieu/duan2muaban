package com.example.duan2muaban.model;

public class Books {
    private int id, matheloai, macuahang;
    private String tensach, hinhanh, chitiet;
    private Double giaban, tongdiem;
    private int  landanhgia;
    private String linkbook;

    public Books(int id, int matheloai, int macuahang, String tensach, String hinhanh, String chitiet, Double giaban, Double tongdiem, int landanhgia, String linkbook) {
        this.id = id;
        this.matheloai = matheloai;
        this.macuahang = macuahang;
        this.tensach = tensach;
        this.hinhanh = hinhanh;
        this.chitiet = chitiet;
        this.giaban = giaban;
        this.tongdiem = tongdiem;
        this.landanhgia = landanhgia;
        this.linkbook = linkbook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatheloai() {
        return matheloai;
    }

    public void setMatheloai(int matheloai) {
        this.matheloai = matheloai;
    }

    public int getMacuahang() {
        return macuahang;
    }

    public void setMacuahang(int macuahang) {
        this.macuahang = macuahang;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public Double getGiaban() {
        return giaban;
    }

    public void setGiaban(Double giaban) {
        this.giaban = giaban;
    }

    public Double getTongdiem() {
        return tongdiem;
    }

    public void setTongdiem(Double tongdiem) {
        this.tongdiem = tongdiem;
    }

    public int getLandanhgia() {
        return landanhgia;
    }

    public void setLandanhgia(int landanhgia) {
        this.landanhgia = landanhgia;
    }

    public String getLinkbook() {
        return linkbook;
    }

    public void setLinkbook(String linkbook) {
        this.linkbook = linkbook;
    }
}
