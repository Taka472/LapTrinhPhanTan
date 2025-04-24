package server;

import dao.LinhKienDAO;
import model.LinhKien;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class HandlingLinhKienClient implements Runnable {
    private final Socket socket;
    private final LinhKienDAO linhKienDAO;

    public HandlingLinhKienClient(Socket socket, LinhKienDAO linhKienDAO) {
        this.socket = socket;
        this.linhKienDAO = linhKienDAO;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            while (true) {
                String command = (String) in.readObject();

                switch (command) {
                    case "FIND_LINHKIEN" -> {
                        String maLK = (String) in.readObject();
                        LinhKien linhKien = linhKienDAO.findById(maLK);
                        out.writeObject(linhKien);
                        out.flush();
                    }

                    case "LIST_LINHKIEN" -> {
                        List<LinhKien> list = linhKienDAO.findAll();
                        out.writeObject(list);
                        out.flush();
                    }

                    case "ADD_LINHKIEN" -> {
                        LinhKien linhKien = (LinhKien) in.readObject();
                        boolean result = linhKienDAO.addLinhKien(linhKien);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "UPDATE_LINHKIEN" -> {
                        LinhKien linhKien = (LinhKien) in.readObject();
                        boolean result = linhKienDAO.updateLinhKien(linhKien);
                        out.writeBoolean(result);
                        out.flush();
                    }

                    case "DELETE_LINHKIEN" -> {
                        String maLK = (String) in.readObject();
                        boolean result = linhKienDAO.deleteLinhKien(maLK);
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
