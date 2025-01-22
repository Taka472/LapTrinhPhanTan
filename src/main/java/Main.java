import helper.ChiTietHoaDonID;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import model.*;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("default").createEntityManager();
        Faker faker = new Faker();
        EntityTransaction tr = em.getTransaction();

        InitialNhanVien(em, tr, faker);
        InitialTaiKhoan(em, tr, faker);
        InitialKhachHang(em, tr, faker);
        InitialLinhKien(em, tr, faker);
        InitialHoaDon(em, tr, faker);
        InitialChiTiet(em, tr, faker);
    }

    public static void InitialNhanVien(EntityManager em, EntityTransaction tr, Faker faker) {
        try (em) {
            tr.begin();

            for (int i = 0; i < 10; i++) {
                String maNV = faker.regexify("NV[0-9]{6}");
                String tenNV = faker.name().fullName();
                String SDT = faker.phoneNumber().cellPhone();
                Date ngaySinh = faker.date().birthday();
                String diaChi = faker.address().fullAddress();
                String CMND = faker.idNumber().valid();
                Date ngayVaoLam = faker.date().past(5, java.util.concurrent.TimeUnit.DAYS);
                boolean trangThai = faker.bool().bool();
                String chucVu = faker.job().title();

                NhanVien nhanVien = new NhanVien(maNV, tenNV, SDT, ngaySinh, diaChi, CMND, ngayVaoLam, trangThai, chucVu);

                em.persist(nhanVien);
            }

            tr.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void InitialTaiKhoan(EntityManager em, EntityTransaction tr, Faker faker) {
        try {
            tr.begin();

            List<NhanVien> nhanViens = em.createQuery("SELECT * FROM NhanVien", NhanVien.class).getResultList();

            for (NhanVien nhanVien : nhanViens) {
                String matKhau = faker.internet().password(3, 13);

                TaiKhoan taiKhoan = new TaiKhoan(nhanVien, matKhau);

                em.persist(taiKhoan);
            }

            tr.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void InitialKhachHang(EntityManager em, EntityTransaction tr, Faker faker) {
        try {
            tr.begin();

            for (int i = 0; i < 10; i++) {
                String maKH = faker.regexify("KH[0-9]{6}");
                String tenKH = faker.name().fullName();
                String SDT = faker.phoneNumber().cellPhone();
                boolean gioiTinh = faker.bool().bool();
                Date ngaySinh = faker.date().birthday();
                String diaChi = faker.address().fullAddress();
                String CMND = faker.idNumber().valid();
                String email = faker.internet().emailAddress();

                KhachHang khachHang = new KhachHang(maKH, tenKH, SDT, CMND, diaChi, email, gioiTinh, ngaySinh);

                em.persist(khachHang);
            }

            tr.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void InitialLinhKien(EntityManager em, EntityTransaction tr, Faker faker) {
        try {
            tr.begin();

            for (int i = 0; i < 10; i++) {
                String maLK = faker.regexify("LK[0-9]{6}");
                String tenLK = faker.commerce().productName();
                String loaiLK = faker.commerce().department();
                double giaBan = Double.parseDouble(faker.commerce().price(50.0, 5000.0));
                String moTa = faker.lorem().sentence();
                int soLuongTon = faker.number().numberBetween(1, 100);

                // Create LinhKien instance with generated data
                LinhKien linhKien = new LinhKien(maLK, tenLK, loaiLK, giaBan, soLuongTon);

                // Persist LinhKien instance
                em.persist(linhKien);
            }

            tr.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void InitialHoaDon(EntityManager em, EntityTransaction tr, Faker faker) {
        try {
            tr.begin();

            for (int i = 0; i < 10; i++) {
                String maHD = faker.regexify("HD[0-9]{6}");

                NhanVien nhanVien = em.createQuery("SELECT n FROM NhanVien n ORDER BY RAND()", NhanVien.class)
                        .setMaxResults(1)
                        .getSingleResult();

                KhachHang khachHang = em.createQuery("SELECT k FROM KhachHang k ORDER BY RAND()", KhachHang.class)
                        .setMaxResults(1)
                        .getSingleResult();

                Date ngayLap = faker.date().past(30, java.util.concurrent.TimeUnit.DAYS);
                double tongTien = Double.parseDouble(faker.commerce().price(100.0, 5000.0));

                HoaDon hoaDon = new HoaDon(maHD, nhanVien, khachHang, ngayLap, tongTien);

                em.persist(hoaDon);
            }

            tr.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }

    public static void InitialChiTiet(EntityManager em, EntityTransaction tr, Faker faker) {
        try {
            tr.begin();

            List<HoaDon> hoaDons = em.createQuery("SELECT h FROM HoaDon h", HoaDon.class).getResultList();

            List<LinhKien> linhKiens = em.createQuery("SELECT l FROM LinhKien l", LinhKien.class).getResultList();

            for (HoaDon hoaDon : hoaDons) {
                for (int j = 0; j < 5; j++) {
                    // Randomly select a LinhKien (Product) from the list of existing products
                    LinhKien linhKien = linhKiens.get(faker.number().numberBetween(0, linhKiens.size()));

                    // Create ChiTietHoaDon
                    ChiTietHoaDonID chiTietHoaDonID = new ChiTietHoaDonID(hoaDon.getMaHD(), linhKien.getMaLK());

                    int soLuong = faker.number().numberBetween(1, 10);
                    double thue = faker.number().randomDouble(2, 1, 5);
                    double donGia = linhKien.getDonGia();
                    double tongTien = soLuong * donGia * (100 + thue) / 100;

                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(chiTietHoaDonID, hoaDon, linhKien, soLuong, thue, donGia, tongTien);

                    em.persist(chiTietHoaDon);
                }
            }

            tr.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (tr.isActive()) {
                tr.rollback();
            }
        } finally {
            em.close();
        }
    }
}
