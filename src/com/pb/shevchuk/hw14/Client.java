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
        try (BufferedReader console = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            System.out.println("Вітаємо у чаті, введіть ваше ім'я");
            new Client(console.readLine()).chat(console);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chat(BufferedReader reader) {
        try (
            Socket socket = new Socket("localhost", port);
            BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                true
            )
        ) {
            writer.println(name);
            new Thread(() -> print(server)).start();
            System.out.println(name + " у чаті");

            System.out.println("\nрозпочніть спілкування");
            String message;
            while ((message = reader.readLine()) != null) {
                writer.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(BufferedReader reader) {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
