package com.pb.shevchuk.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Calculator {
    public static void main(String[] args) throws IOException {
        int x;
        int y;
        String sign;

        try(
                InputStreamReader inStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inStream)
        ) {
            System.out.println("Введіть, будь ласка, ціле число");
            x =  Integer.parseInt(reader.readLine());

            System.out.println("Вкажіть знак арифметичної дії: +, -, * або /");
            sign = reader.readLine();

            System.out.println("Тепер, будь ласка, друге ціле число");
            y = Integer.parseInt(reader.readLine());

            System.out.print("\n");
        }

        long result = 0L;
        double floatingResult = 0;

        switch (sign) {
            case "+":
                result = (long) x + y;
                break;
            case "-":
                result = (long) x - y;
                break;
            case "*":
                result = (long) x * y;
                break;
            case "/":
                try {
                    if (x % y == 0) {
                        result = x / y;
                    } else {
                        floatingResult = (double) x / y;
                    }
                } catch (ArithmeticException exception) {
                    if (y == 0) {
                        System.out.print("Помилка: ділення на нуль неможливе");
                        return;
                    }
                }

                break;
            default:
                System.out.print("Ви вказали помилковий арифметичний знак");
                return;
        }

        System.out.printf("%d %s %d = ", x, sign, y);

        if (floatingResult != 0) {
            System.out.print(floatingResult);
        } else {
            System.out.print(result);
        }
    }
}
