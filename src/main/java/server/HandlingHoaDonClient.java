package server;

import dao.HoaDonDAO;
import model.HoaDon;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class HandlingHoaDonClient implements Runnable {

    private Socket socket;
    private HoaDonDAO hoaDonDAO;

    public HandlingHoaDonClient(Socket socket, HoaDonDAO hoaDonDAO) {
        this.socket = socket;
        this.hoaDonDAO = hoaDonDAO;
    }

    @Override
    public void run() {
        try (
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())
        ) {
            while (true) {
                String command = (String) ois.readObject();
                switch (command) {
                    case "addHoaDon":
                        HoaDon newHD = (HoaDon) ois.readObject();
                        boolean added = hoaDonDAO.addHoaDon(newHD);
                        oos.writeBoolean(added);
                        oos.flush();
                        break;

                    case "findHoaDon":
                        String maHD = (String) ois.readObject();
                        HoaDon hd = hoaDonDAO.findById(maHD);
                        oos.writeObject(hd);
                        oos.flush();
                        break;

                    case "updateHoaDon":
                        HoaDon updateHD = (HoaDon) ois.readObject();
                        boolean updated = hoaDonDAO.updateHoaDon(updateHD);
                        oos.writeBoolean(updated);
                        oos.flush();
                        break;

                    case "deleteHoaDon":
                        String deleteMaHD = (String) ois.readObject();
                        boolean deleted = hoaDonDAO.deleteHoaDon(deleteMaHD);
                        oos.writeBoolean(deleted);
                        oos.flush();
                        break;

                    case "exit":
                        socket.close();
                        return;

                    default:
                        oos.writeObject("Unknown command: " + command);
                        oos.flush();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
