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
@Table(name = "KhachHang")
public class KhachHang {
    @Id
    @Column(columnDefinition = "varchar(8)", unique = true, nullable = false)
    private String maKH;

    @Column(columnDefinition = "varchar(30)")
    private String tenKH;

    @Column(columnDefinition = "varchar(10)")
    private String sdt;

    @Column(columnDefinition = "varchar(12)")
    private String CCCD;

    @Column(columnDefinition = "varchar(50)")
    private String diaChi;

    @Column(columnDefinition = "varchar(40)")
    private String email;

    @Column(columnDefinition = "boolean")
    private boolean gioiTinh;

    @Column(columnDefinition = "ngaySinh")
    private Date ngaySinh;

    public KhachHang() {}

    public KhachHang(String maKH, String tenKH, String sdt,  String CCCD, String diaChi, String email, boolean gioiTinh, Date ngaySinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.CCCD = CCCD;
        this.diaChi = diaChi;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }
}
