package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 25000;
        ServerSocket serverSocket = new ServerSocket(port);

        Thread client = new Thread(new Client());
        client.start();

        Socket socket = serverSocket.accept();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println((LocalDate.now() + ", " + reader.readLine()));
            client.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
