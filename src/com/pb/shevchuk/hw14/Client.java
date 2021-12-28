package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 25000;

        try (
            Socket socket = new Socket("localhost", port);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            Thread print = new Thread(new Print(server));
            print.start();

            System.out.println("client");
            String message;

            while ((message = console.readLine()) != null) {
                writer.println(message);
                System.out.println("send");
            }

            System.out.println("end client");
        }
    }
}
