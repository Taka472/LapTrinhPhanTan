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
@Table(name = "HoaDon")
public class HoaDon {
    @Id
    @Column(columnDefinition = "varchar(8)", unique = true, nullable = false)
    private String maHD;

    @ManyToOne
    @JoinColumn(name = "Nhan Vien", referencedColumnName = "MaNV")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "Khach Hang", referencedColumnName = "maKH")
    private KhachHang khachHang;

    @Column(columnDefinition = "Date")
    private Date ngayMua;

    @Column(columnDefinition = "double")
    private double tongTien;

    public HoaDon() {}

    public HoaDon(String maHD, NhanVien nhanVien, KhachHang khachHang, Date ngayLap, double tongTien) {
        this.maHD = maHD;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayMua = ngayLap;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }
}
