package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final int port = 25000;
    private final String name;

    public Client(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        new Client((args.length == 0) ? "taras" : args[0]).write();
    }

    public void write() {
        try (
                Socket socket = new Socket("localhost", port);
                BufferedReader console = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
                BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                        true
                );
        ) {
            Thread print = new Thread(() -> {
                String message;
                try {
                    while ((message = server.readLine()) != null) {
                        System.out.println("\n " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            print.start();

            System.out.println(name + " приєднався до чату");
            String message;
            while ((message = console.readLine()) != null) {
                writer.println(name + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
