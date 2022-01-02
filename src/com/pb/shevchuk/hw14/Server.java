package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 25000;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        Socket socket1 = serverSocket.accept();

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.submit(new Handler(socket));
        threadPool.submit(new Handler(socket1));
    }

    static class Handler implements Runnable {
        private final Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter writer = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                            true
                    );
            ) {
                String message;
                while ((message = reader.readLine()) != null) {
                    writer.println(LocalDateTime.now() + "  -----  " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
