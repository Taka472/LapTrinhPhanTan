package dao;

import model.KhachHang;

import java.util.List;

public interface KhachHangDAO {
    KhachHang findById(String maKH);
    List<KhachHang> findAll();
    boolean addKhachHang(KhachHang kh);
    boolean updateKhachHang(KhachHang kh);
    boolean deleteKhachHang(String maKH);
}
