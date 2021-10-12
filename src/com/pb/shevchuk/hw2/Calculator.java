package com.pb.shevchuk.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Calculator {
    public static void main(String[] args) throws IOException {
        int operand1;
        int operand2;
        String sign;

        try(
                InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(in)
        ) {
            System.out.println("Введіть, будь ласка, ціле число");
            operand1 =  Integer.parseInt(reader.readLine());

            System.out.println("Вкажіть знак арифметичної операції: +, -, * або /");
            sign = reader.readLine();

            System.out.println("Тепер, будь ласка, друге ціле число");
            operand2 = Integer.parseInt(reader.readLine());
        }

        long result = 0L;
        double floatingResult = 0;

        switch (sign) {
            case "+":
                result = (long) operand1 + operand2;
                break;
            case "-":
                result = (long) operand1 - operand2;
                break;
            case "*":
                result = (long) operand1 * operand2;
                break;
            case "/":
                try {
                    if (operand1 % operand2 == 0) {
                        result = operand1 / operand2;
                    } else {
                        floatingResult = (double) operand1 / operand2;
                    }
                } catch (ArithmeticException exception) {
                    if (operand2 == 0) {
                        System.out.println("Помилка: ділення на нуль неможливе");
                        return;
                    }
                }

                break;
        }

        if (floatingResult != 0) {
            System.out.print("Результат: " + floatingResult);
        } else {
            System.out.print("Результат: " + result);
        }
    }
}
