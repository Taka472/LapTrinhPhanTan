package dao;

import model.HoaDon;

import java.util.List;

public interface HoaDonDAO {
    HoaDon findById(String maHD);
    List<HoaDon> findAll();
    boolean addHoaDon(HoaDon hoaDon);
    boolean updateHoaDon(HoaDon hoaDon);
    boolean deleteHoaDon(String maHD);
}
