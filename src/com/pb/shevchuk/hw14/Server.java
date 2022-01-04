package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int port = 25000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        threadPool.submit(new Sender());

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Receiver receiver = new Receiver(clientSocket);
            Sender.receivers.add(receiver);
            threadPool.submit(receiver);
        }
    }

    static class Receiver implements Runnable {
        static private final ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

        private final String clientName;
        private final BufferedReader reader;
        private final PrintWriter writer;

        public Receiver(Socket socket) throws IOException {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
            );
            clientName = reader.readLine();
            writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                    true
            );
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    messages.add(String.format("%s  ----  %s: %s", LocalDateTime.now(), clientName, message));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Sender implements Runnable {
        private static final List<Receiver> receivers = new ArrayList<>();

        @Override
        public void run() {
            while (true) {
                if (!Receiver.messages.isEmpty()) {
                    String message = Receiver.messages.remove();
                    receivers.forEach(receiver -> receiver.writer.println(message));
                }
            }
        }
    }
}
