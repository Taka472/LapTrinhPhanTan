package server;

import dao.KhachHangDAOImpl;
import dao.LinhKienDAOImpl;
import dao.NhanVienDAOImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final int PORT = 9090;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                // Chấp nhận kết nối từ client
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                // Tạo DAO
                KhachHangDAOImpl khachHangDAO = new KhachHangDAOImpl();
                LinhKienDAOImpl linhKienDAO = new LinhKienDAOImpl();
                NhanVienDAOImpl nhanVienDAO = new NhanVienDAOImpl();

                // Tạo và chạy một thread cho mỗi kết nối client
                new Thread(new HandlingClient(socket, khachHangDAO)).start();
                new Thread(new HandlingLinhKienClient(socket, linhKienDAO)).start();
                new Thread(new HandlingNhanVienClient(socket, nhanVienDAO)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
