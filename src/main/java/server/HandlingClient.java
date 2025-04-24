package server;

import dao.KhachHangDAO;
import model.KhachHang;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HandlingClient implements Runnable {
    private final Socket socket;
    private final KhachHangDAO khachHangDAO;

    public HandlingClient(Socket socket, KhachHangDAO khachHangDAO) {
        this.socket = socket;
        this.khachHangDAO = khachHangDAO;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                String command = (String) in.readObject();

                switch (command) {
                    case "FIND_KHACHHANG" -> {
                        String maKH = (String) in.readObject();
                        KhachHang kh = khachHangDAO.findById(maKH);
                        out.writeObject(kh);
                        out.flush();
                    }

                    case "LIST_KHACHHANG" -> {
                        List<KhachHang> list = khachHangDAO.findAll();
                        out.writeObject(list);
                        out.flush();
                    }

                    case "ADD_KHACHHANG" -> {
                        KhachHang kh = (KhachHang) in.readObject();
                        boolean result = khachHangDAO.addKhachHang(kh);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "UPDATE_KHACHHANG" -> {
                        KhachHang kh = (KhachHang) in.readObject();
                        boolean result = khachHangDAO.updateKhachHang(kh);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "DELETE_KHACHHANG" -> {
                        String maKH = (String) in.readObject();
                        boolean result = khachHangDAO.deleteKhachHang(maKH);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "EXIT" -> {
                        System.out.println("Client disconnected.");
                        socket.close();
                        return;
                    }

                    default -> System.out.println("Invalid command: " + command);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
