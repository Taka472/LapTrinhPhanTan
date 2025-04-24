package dao;

import model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mariadb.jdbc.Connection;

import java.sql.DriverManager;

public class KhachHangDAOImpl implements KhachHangDAO{
    private Connection connection;

    public KhachHangDAOImpl() {
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
    public KhachHang findById(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getKhachHangFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<KhachHang> findAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(getKhachHangFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (maKH, tenKH, sdt, CCCD, diaChi, email, gioiTinh, ngaySinh) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParams(ps, kh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET tenKH = ?, sdt = ?, CCCD = ?, diaChi = ?, email = ?, gioiTinh = ?, ngaySinh = ? " +
                "WHERE maKH = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getCCCD());
            ps.setString(4, kh.getDiaChi());
            ps.setString(5, kh.getEmail());
            ps.setBoolean(6, kh.isGioiTinh());
            ps.setDate(7, new java.sql.Date(kh.getNgaySinh().getTime()));
            ps.setString(8, kh.getMaKH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteKhachHang(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE maKH = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private KhachHang getKhachHangFromResultSet(ResultSet rs) throws SQLException {
        return new KhachHang(
                rs.getString("maKH"),
                rs.getString("tenKH"),
                rs.getString("sdt"),
                rs.getString("CCCD"),
                rs.getString("diaChi"),
                rs.getString("email"),
                rs.getBoolean("gioiTinh"),
                rs.getDate("ngaySinh")
        );
    }

    private void setParams(PreparedStatement ps, KhachHang kh) throws SQLException {
        ps.setString(1, kh.getMaKH());
        ps.setString(2, kh.getTenKH());
        ps.setString(3, kh.getSdt());
        ps.setString(4, kh.getCCCD());
        ps.setString(5, kh.getDiaChi());
        ps.setString(6, kh.getEmail());
        ps.setBoolean(7, kh.isGioiTinh());
        ps.setDate(8, new java.sql.Date(kh.getNgaySinh().getTime()));
    }
}
