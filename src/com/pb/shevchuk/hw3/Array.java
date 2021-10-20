package com.pb.shevchuk.hw3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Array {
    public static void main(String[] args) throws IOException {
        int[] array = new int[10];
        int n = array.length;
        int sum = 0;
        int sumPositive = 0;

        System.out.printf("Пропонуємо вам ввести масив цілих числ, кількість елементів - %d\n", n);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            for (int i = 0; i < array.length; i++) {
                System.out.println("\nВведіть, будь ласка, число № " + (i + 1));
                int x = Integer.parseInt(reader.readLine());

                array[i] = x;
                sum += x;
                sumPositive += Math.max(x, 0);
            }
        }

        System.out.println("\nВи ввели наступний масив:" + Arrays.toString(array));
        System.out.println("Сума усіх елементів масиву: " + sum);
        System.out.println("Сума позитивних елементів масиву: " + sumPositive);

        do {
            int newN = 0;

            for (int i = 1; i < n; i++) {
                int x = array[i - 1];
                int y = array[i];

                if (x > y) {
                    array[i - 1] = y;
                    array[i] = x;
                    newN = i;
                }
            }

            n = newN;
        } while (n > 1);

        System.out.println("Відсортований масив: " + Arrays.toString(array));
    }
}
