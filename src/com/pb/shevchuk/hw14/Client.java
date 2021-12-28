package com.pb.shevchuk.hw14;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    public void run() {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int port = 25000;

        try (
            Socket socket = new Socket("localhost", port);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ) {
            System.out.println("write");
            writer.write(console.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
