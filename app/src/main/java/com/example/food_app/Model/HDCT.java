package com.example.food_app.Model;

public class HDCT {
    public int mahdct;
    public int mahd;
    public int masp;
    public int soluong;
    public float giatien;
    public String trangthai;

    public HDCT() {
    }

    public HDCT(int mahdct, int mahd, int masp, int soluong, float giatien, String trangthai) {
        this.mahdct = mahdct;
        this.mahd = mahd;
        this.masp = masp;
        this.soluong = soluong;
        this.giatien = giatien;
        this.trangthai = trangthai;
    }

    public int getMahdct() {
        return mahdct;
    }

    public void setMahdct(int mahdct) {
        this.mahdct = mahdct;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public float getGiatien() {
        return giatien;
    }

    public void setGiatien(float giatien) {
        this.giatien = giatien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
