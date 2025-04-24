package dao;

import model.LinhKien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Connection;
import java.sql.DriverManager;

public class LinhKienDAOImpl implements LinhKienDAO {
    private Connection connection;

    public LinhKienDAOImpl() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(
                    "jdbc:mariadb://localhost:3306/testdb", "root", "root"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LinhKien findById(String maLK) {
        String sql = "SELECT * FROM LinhKien WHERE maLK = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maLK);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getLinhKienFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LinhKien> findAll() {
        List<LinhKien> list = new ArrayList<>();
        String sql = "SELECT * FROM LinhKien";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(getLinhKienFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addLinhKien(LinhKien linhKien) {
        String sql = "INSERT INTO LinhKien (maLK, tenLK, loaiHang, donGia, soLuong) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParams(ps, linhKien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateLinhKien(LinhKien linhKien) {
        String sql = "UPDATE LinhKien SET tenLK = ?, loaiHang = ?, donGia = ?, soLuong = ? WHERE maLK = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, linhKien.getTenLK());
            ps.setString(2, linhKien.getLoaiHang());
            ps.setDouble(3, linhKien.getDonGia());
            ps.setInt(4, linhKien.getSoLuong());
            ps.setString(5, linhKien.getMaLK());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteLinhKien(String maLK) {
        String sql = "DELETE FROM LinhKien WHERE maLK = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maLK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private LinhKien getLinhKienFromResultSet(ResultSet rs) throws SQLException {
        return new LinhKien(
                rs.getString("maLK"),
                rs.getString("tenLK"),
                rs.getString("loaiHang"),
                rs.getDouble("donGia"),
                rs.getInt("soLuong")
        );
    }

    private void setParams(PreparedStatement ps, LinhKien linhKien) throws SQLException {
        ps.setString(1, linhKien.getMaLK());
        ps.setString(2, linhKien.getTenLK());
        ps.setString(3, linhKien.getLoaiHang());
        ps.setDouble(4, linhKien.getDonGia());
        ps.setInt(5, linhKien.getSoLuong());
    }
}
