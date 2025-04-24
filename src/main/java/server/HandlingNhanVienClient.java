package server;

import dao.NhanVienDAO;
import model.NhanVien;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HandlingNhanVienClient implements Runnable {
    private final Socket socket;
    private final NhanVienDAO nhanVienDAO;

    public HandlingNhanVienClient(Socket socket, NhanVienDAO nhanVienDAO) {
        this.socket = socket;
        this.nhanVienDAO = nhanVienDAO;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                String command = (String) in.readObject();

                switch (command) {
                    case "FIND_NHANVIEN" -> {
                        String maNV = (String) in.readObject();
                        NhanVien nhanVien = nhanVienDAO.findById(maNV);
                        out.writeObject(nhanVien);
                        out.flush();
                    }

                    case "LIST_NHANVIEN" -> {
                        List<NhanVien> list = nhanVienDAO.findAll();
                        out.writeObject(list);
                        out.flush();
                    }

                    case "ADD_NHANVIEN" -> {
                        NhanVien nhanVien = (NhanVien) in.readObject();
                        boolean result = nhanVienDAO.addNhanVien(nhanVien);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "UPDATE_NHANVIEN" -> {
                        NhanVien nhanVien = (NhanVien) in.readObject();
                        boolean result = nhanVienDAO.updateNhanVien(nhanVien);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "DELETE_NHANVIEN" -> {
                        String maNV = (String) in.readObject();
                        boolean result = nhanVienDAO.deleteNhanVien(maNV);
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
