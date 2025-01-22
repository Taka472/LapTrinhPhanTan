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
@Table(name = "TaiKhoan")
public class TaiKhoan {
    @OneToOne
    @JoinColumn(name = "Nhan Vien", referencedColumnName = "maNV")
    private NhanVien nhanVien;

    @Column(columnDefinition = "varchar(20)")
    private String matKhau;

    public TaiKhoan() {}

    public TaiKhoan(NhanVien nhanVien, String matKhau) {
        this.nhanVien = nhanVien;
        this.matKhau = matKhau;
    }

}
