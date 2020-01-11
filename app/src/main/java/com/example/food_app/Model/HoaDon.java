package com.example.food_app.Model;

import java.util.Date;

public class HoaDon {
    public int MaHD;
    public String email;
    public String ngay;
    public String trangthai;

    public HoaDon() {
    }

    public HoaDon(int maHD, String email, String ngay, String trangthai) {
        MaHD = maHD;
        this.email = email;
        this.ngay = ngay;
        this.trangthai = trangthai;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int maHD) {
        MaHD = maHD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
