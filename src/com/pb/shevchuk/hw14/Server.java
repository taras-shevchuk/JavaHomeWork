package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 25000;
        ServerSocket serverSocket = new ServerSocket(port);

        try (
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("server");
            String message;

            while ((message = reader.readLine()) != null) {
                writer.println((LocalDate.now() + ", " + message));
                System.out.println("get");
            }

            System.out.println("end server");
        }
    }
}
