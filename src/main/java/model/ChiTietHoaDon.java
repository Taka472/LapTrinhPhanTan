package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import helper.ChiTietHoaDonID;

import java.util.Date;

@Setter
@Getter
@ToString
@Entity
@Table(name = "ChiTietHoaDon")
public class ChiTietHoaDon {
    @EmbeddedId
    private ChiTietHoaDonID Id;

    @ManyToOne
    @MapsId("maHD")
    @JoinColumn(name = "maHD", referencedColumnName = "maHD", insertable = false, updatable = false)
    private HoaDon hoaDon;

    @ManyToOne
    @MapsId("maLK")
    @JoinColumn(name = "maLK", referencedColumnName = "maLK", insertable = false, updatable = false)
    private LinhKien linhKien;

    @Column(columnDefinition = "int")
    private int soLuong;

    @Column(columnDefinition = "double")
    private double thue;

    @Column(columnDefinition = "double")
    private double donGia;

    @Column(columnDefinition = "double")
    private double tongTien;

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(ChiTietHoaDonID id, HoaDon hoaDon, LinhKien linhKien, int soLuong, double thue, double donGia, double tongTien) {
        this.Id = id;
        this.hoaDon = hoaDon;
        this.linhKien = linhKien;
        this.soLuong = soLuong;
        this.thue = thue;
        this.donGia = donGia;
        this.tongTien = tongTien;
    }
}
