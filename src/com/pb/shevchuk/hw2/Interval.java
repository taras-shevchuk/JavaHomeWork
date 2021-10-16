package com.pb.shevchuk.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Interval {
    public static void main(String[] args) throws IOException {
        int[] breakpoints = {0, 15, 36, 51, 100};
        int x;

        try(
                InputStreamReader inStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inStream)
        ) {
            System.out.println("Введіть, будь ласка, ціле число від 0 до 100");

            try {
                x = Integer.parseInt(reader.readLine());

                if (x < breakpoints[0] || x > breakpoints[breakpoints.length - 1]) {
                    throw new NumberFormatException();
                }

                System.out.print("\n");
            } catch (NumberFormatException exception) {
                System.out.print("\n");
                System.out.print("Ви ввели значення, що не входить у жоден із очікуваних проміжків числ");
                return;
            }
        }

        int start = 0;
        int end = 0;

        for (int i = 1; i < breakpoints.length - 1; i++) {
            if (x < breakpoints[i]) {
                start = breakpoints[i - 1];
                end = breakpoints[i] - 1;
                break;
            } else {
                start = breakpoints[breakpoints.length - 2];
                end = breakpoints[breakpoints.length - 1];
            }
        }

        System.out.printf("Ви ввели число із проміжку числ від %d до %d", start, end);
    }
}
