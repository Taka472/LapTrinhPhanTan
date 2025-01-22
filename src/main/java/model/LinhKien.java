package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@Entity
@Table(name = "LinhKien")
public class LinhKien {
    @Id
    @Column(columnDefinition = "varchar(8)", unique = true, nullable = false)
    private String maLK;

    @Column(columnDefinition = "varchar(40)")
    private String tenLK;

    @Column(columnDefinition = "varchar(30)")
    private String loaiHang;

    @Column(columnDefinition = "double")
    private double donGia;

    @Column(columnDefinition = "int")
    private int soLuong;

    public LinhKien() {}

    public LinhKien(String maLK, String tenLK, String loaiHang, double donGia, int soLuong) {
        this.maLK = maLK;
        this.tenLK = tenLK;
        this.loaiHang = loaiHang;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public String getMaLK() {
        return maLK;
    }

    public double getDonGia() {
        return donGia;
    }
}
