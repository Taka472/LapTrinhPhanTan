package dao;

import model.LinhKien;

import java.util.List;

public interface LinhKienDAO {
    LinhKien findById(String maLK);
    List<LinhKien> findAll();
    boolean addLinhKien(LinhKien linhKien);
    boolean updateLinhKien(LinhKien linhKien);
    boolean deleteLinhKien(String maLK);
}
