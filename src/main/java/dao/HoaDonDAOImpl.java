package dao;

import model.HoaDon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.mariadb.jdbc.Connection;

public class HoaDonDAOImpl implements HoaDonDAO {
    private Connection connection;

    public HoaDonDAOImpl() {
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
    public HoaDon findById(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE maHD = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getHoaDonFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HoaDon> findAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(getHoaDonFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addHoaDon(HoaDon hd) {
        String sql = "INSERT INTO HoaDon (maHD, `Nhan Vien`, `Khach Hang`, ngayMua, tongTien) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, hd.getMaHD());
            ps.setString(2, hd.getNhanVien().getMaNV());
            ps.setString(3, hd.getKhachHang().getMaKH());
            ps.setDate(4, new java.sql.Date(hd.getNgayMua().getTime()));
            ps.setDouble(5, hd.getTongTien());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateHoaDon(HoaDon hd) {
        String sql = "UPDATE HoaDon SET `Nhan Vien` = ?, `Khach Hang` = ?, ngayMua = ?, tongTien = ? WHERE maHD = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, hd.getNhanVien().getMaNV());
            ps.setString(2, hd.getKhachHang().getMaKH());
            ps.setDate(3, new java.sql.Date(hd.getNgayMua().getTime()));
            ps.setDouble(4, hd.getTongTien());
            ps.setString(5, hd.getMaHD());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteHoaDon(String maHD) {
        String sql = "DELETE FROM HoaDon WHERE maHD = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private HoaDon getHoaDonFromResultSet(ResultSet rs) throws SQLException {
        String maHD = rs.getString("maHD");
        String maNV = rs.getString("Nhan Vien");
        String maKH = rs.getString("Khach Hang");

        NhanVienDAO nhanVienDAO = new NhanVienDAOImpl();
        KhachHangDAO khachHangDAO = new KhachHangDAOImpl();

        return new HoaDon(
                maHD,
                nhanVienDAO.findById(maNV),
                khachHangDAO.findById(maKH),
                rs.getDate("ngayMua"),
                rs.getDouble("tongTien")
        );
    }
}
