package com.pb.shevchuk.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interval {
    public static void main(String[] args) throws IOException {
        byte x;

        try(
                InputStreamReader inStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inStream)
        ) {
            System.out.println("Введіть, будь ласка, ціле число");

            try {
                x = Byte.parseByte(reader.readLine());

                if (x < 0 || x > 100) {
                    throw new NumberFormatException();
                }

                System.out.print("\n");
            } catch (NumberFormatException exception) {
                System.out.println("\nВи ввели значення, що не підпадає у жоден із очікуваних проміжків числ");
                return;
            }
        }

        int[] breakpoints = {0, 15, 36, 51, 100};
        int start;
        int end;

        if (x >= breakpoints[0] && x < breakpoints[1]) {
            start = breakpoints[0];
            end = breakpoints[1] - 1;
        } else if (x >= breakpoints[1] && x < breakpoints[2]) {
            start = breakpoints[1];
            end = breakpoints[2] - 1;
        } else if (x >= breakpoints[2] && x < breakpoints[3]) {
            start = breakpoints[2];
            end = breakpoints[3] - 1;
        } else {
            start = breakpoints[3];
            end = breakpoints[4];
        }

        System.out.printf("Ви ввели число із проміжку числ від %d до %d", start, end);
    }
}
