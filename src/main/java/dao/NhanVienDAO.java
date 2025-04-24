package dao;

import model.NhanVien;

import java.util.List;

public interface NhanVienDAO {
    NhanVien findById(String maNV);
    List<NhanVien> findAll();
    boolean addNhanVien(NhanVien nhanVien);
    boolean updateNhanVien(NhanVien nhanVien);
    boolean deleteNhanVien(String maNV);
}
