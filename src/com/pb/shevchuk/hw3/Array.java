package com.pb.shevchuk.hw3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Array {
    public static void main(String[] args) throws IOException {
        int[] array = new int[10];
        int sum = 0;
        int sumPositive = 0;
        StringBuilder arrayToString = new StringBuilder();

        System.out.printf("Пропонуємо ввести масив цілих числ, потрібна кількість елементів - %d\n", array.length);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            for (int i = 0; i < array.length; i++) {
                System.out.println("\nВведіть, будь ласка, число № " + (i + 1));
                int x = Integer.parseInt(reader.readLine());

                array[i] = x;
                sum += x;
                sumPositive += Math.max(x, 0);
                arrayToString.append((i == 0) ? x : (", " + x));
            }
        }

        System.out.printf("\nВи ввели наступний масив: [%s]\n", arrayToString);
        System.out.println("Сума усіх елементів масиву: " + sum);
        System.out.println("Сума позитивних елементів масиву: " + sumPositive);

        int n = array.length;
        StringBuilder sortedArray = new StringBuilder();

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

            sortedArray.insert(0, ", " + array[newN]);
            n = newN;
        } while (n > 1);

        System.out.printf("Відсортований масив: [%s]\n", sortedArray.insert(0, array[0]));
    }
}
