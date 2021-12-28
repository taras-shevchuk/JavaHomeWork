package com.pb.shevchuk.hw14;

import java.io.BufferedReader;
import java.io.IOException;

public class Print implements Runnable {
    private final BufferedReader reader;

    public Print(BufferedReader reader) {
        this.reader = reader;
    }

    public void run() {
        String message;

        try {
            while ((message = reader.readLine()) != null) {
                System.out.println("print");
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
