package com.pb.shevchuk.hw13;

import java.util.LinkedList;
import java.util.List;

public class Consumer {
    private static final List<Integer> buffer = new LinkedList<>();
    private static final int size = 5;
    private static final int times = 3;

    public static void main(String[] args) {
        Runnable producer = new Producer();
        Thread prodThread = new Thread(producer);
        prodThread.start();

        synchronized (buffer) {
            while (prodThread.isAlive()) {
                try {
                    buffer.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int j : buffer) {
                    try {
                        buffer.wait(500);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(j);
                }

                System.out.println();
                buffer.clear();
                buffer.notify();
            }
        }
    }

    public static class Producer implements Runnable {
        public void run() {
            synchronized (buffer) {
                for (int i = 0; i < times; i++) {
                    for (int j = 0; j < size; j++) {
                        try {
                            buffer.wait(500);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        buffer.add(j + 1);
                    }

                    buffer.notify();
                    if (i != times - 1) {
                        try {
                            buffer.wait();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
