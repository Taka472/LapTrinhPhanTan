package helper;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChiTietHoaDonID implements Serializable {

    private String maHD;
    private String maLK;

    public ChiTietHoaDonID() {}

    // Constructor
    public ChiTietHoaDonID(String maHD, String maLK) {
        this.maHD = maHD;
        this.maLK = maLK;
    }

    // Getters and Setters
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaLK() {
        return maLK;
    }

    public void setMaLK(String maLK) {
        this.maLK = maLK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietHoaDonID that = (ChiTietHoaDonID) o;
        return Objects.equals(maHD, that.maHD) && Objects.equals(maLK, that.maLK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maHD, maLK);
    }
}
