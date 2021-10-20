package com.pb.shevchuk.hw3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bingo {
    public static void main(String[] args) throws IOException {
        int x = (int) Math.floor(Math.random() * 101);

        System.out.println("Спробуй вгадати загадане ціле число від 0 до 100.");
        System.out.println("Якщо бажаєте припинити гру, введіть \"stop\"");

        String answer;
        int attempts = 0;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println();

                answer = reader.readLine();
                attempts++;

                if (answer.equals("stop")) {
                    System.out.println("Гру завершено");
                    return;
                }

                int y;

                try {
                    y = Integer.parseInt(answer);
                } catch (NumberFormatException exception) {
                    System.out.println("Введено некоректне значення, спробуйте ще раз");
                    continue;
                }

                if (y > x) {
                    System.out.println("Загадане число є меншим від введеного вами");
                } else if (y < x) {
                    System.out.println("Загадане число є більшим від введеного вами");
                } else {
                    System.out.println("Bingo! Ви вгадали число");
                    System.out.println("Кількість використаних спроб - " + attempts);
                    break;
                }
            }
        }

    }
}
