package com.example.food_app.Model;

public class SanPham {
    public int masp;
    public String motasp;
    public String tensp;
    public String loaisp;
    public int soluong;
    public float giatien;

    public SanPham(int masp, String motasp, String tensp, String loaisp, int soluong, float giatien) {
        this.masp = masp;
        this.motasp = motasp;
        this.tensp = tensp;
        this.loaisp = loaisp;
        this.soluong = soluong;
        this.giatien = giatien;
    }



    public SanPham() {
    }

    public SanPham(String motasp, String tensp) {
        this.motasp = motasp;
        this.tensp = tensp;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getMotasp() {
        return motasp;
    }

    public void setMotasp(String motasp) {
        this.motasp = motasp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(String loaisp) {
        this.loaisp = loaisp;
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
}
