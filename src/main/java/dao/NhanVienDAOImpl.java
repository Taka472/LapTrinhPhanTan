package dao;

import model.NhanVien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Connection;
import java.sql.DriverManager;

public class NhanVienDAOImpl implements NhanVienDAO {
    private Connection connection;

    public NhanVienDAOImpl() {
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
    public NhanVien findById(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getNhanVienFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<NhanVien> findAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(getNhanVienFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addNhanVien(NhanVien nhanVien) {
        String sql = "INSERT INTO NhanVien (maNV, tenNV, SDT, ngaySinh, diaChi, CMND, ngayVaoLam, trangThai, chucVu) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParams(ps, nhanVien);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateNhanVien(NhanVien nhanVien) {
        String sql = "UPDATE NhanVien SET tenNV = ?, SDT = ?, ngaySinh = ?, diaChi = ?, CMND = ?, ngayVaoLam = ?, trangThai = ?, chucVu = ? WHERE maNV = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setParams(ps, nhanVien);
            ps.setString(9, nhanVien.getMaNV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE maNV = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maNV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private NhanVien getNhanVienFromResultSet(ResultSet rs) throws SQLException {
        return new NhanVien(
                rs.getString("maNV"),
                rs.getString("tenNV"),
                rs.getString("SDT"),
                rs.getDate("ngaySinh"),
                rs.getString("diaChi"),
                rs.getString("CMND"),
                rs.getDate("ngayVaoLam"),
                rs.getBoolean("trangThai"),
                rs.getString("chucVu")
        );
    }

    private void setParams(PreparedStatement ps, NhanVien nhanVien) throws SQLException {
        ps.setString(1, nhanVien.getMaNV());
        ps.setString(2, nhanVien.getTenNV());
        ps.setString(3, nhanVien.getSDT());
        ps.setDate(4, new java.sql.Date(nhanVien.getNgaySinh().getTime()));
        ps.setString(5, nhanVien.getDiaChi());
        ps.setString(6, nhanVien.getCMND());
        ps.setDate(7, new java.sql.Date(nhanVien.getNgayVaoLam().getTime()));
        ps.setBoolean(8, nhanVien.isTrangThai());
        ps.setString(9, nhanVien.getChucVu());
    }
}
