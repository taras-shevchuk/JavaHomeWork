package com.pb.shevchuk.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Anagram {
    public static void main(String[] args) throws IOException {
        String[] lines = new String[2];

        System.out.println("Введіть, будь ласка, по черзі два текстових рядки\n");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            lines[0] = reader.readLine();
            lines[1] = reader.readLine();
        }

        System.out.println();

        char[][] arrays = new char[2][];

        for (int i = 0; i < lines.length; i++) {
            arrays[i] = getCharArray(lines[i]);
        }

        if (arrays[0].length != arrays[1].length) {
            System.out.println("Рядки не є анаграмами");
            return;
        }

        for (char[] array : arrays) {
            Arrays.sort(array);
        }

        if (Arrays.equals(arrays[0], arrays[1])) {
            System.out.println("Рядки є анаграмами");
        } else {
            System.out.println("Рядки не є анаграмами");
        }
    }

    static char[] getCharArray(String text) {
        text = text.replaceAll("[^a-zA-Zа-яА-Я]", "");
        text = text.toLowerCase();
        return text.toCharArray();
    }
}