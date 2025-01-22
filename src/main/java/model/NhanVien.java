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
@Table(name = "NhanVien")
public class NhanVien {

    @Id
    @Column(columnDefinition = "varchar(8)", unique = true, nullable = false)
    private String maNV;

    @Column(columnDefinition = "varchar(45)", unique = true, nullable = false)
    private String tenNV;

    @Column(columnDefinition = "varchar(10)", nullable = false)
    private String SDT;

    @Column(columnDefinition = "date", unique = true, nullable = false)
    private Date ngaySinh;

    @Column(columnDefinition = "varchar(100)")
    private String diaChi;

    @Column(columnDefinition = "varchar(12)")
    private String CMND;

    @Column(columnDefinition = "date")
    private Date ngayVaoLam;

    @Column(columnDefinition = "bool")
    private boolean trangThai;

    @Column(columnDefinition = "varchar(20)")
    private String chucVu;

    public NhanVien() {}

    public NhanVien(String maNV, String tenNV, String SDT, Date ngaySinh, String diaChi, String CMND,
                    Date ngayVaoLam, boolean trangThai, String chucVu) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.SDT = SDT;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.CMND = CMND;
        this.ngayVaoLam = ngayVaoLam;
        this.trangThai = trangThai;
        this.chucVu = chucVu;
    }
}
