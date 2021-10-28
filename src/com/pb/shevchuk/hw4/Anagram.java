package com.pb.shevchuk.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Anagram {
    public static void main(String[] args) throws IOException {
        String line1;
        String line2;

        System.out.println("Введіть, будь ласка, по черзі два текстових рядки\n");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            line1 = reader.readLine();
            line2 = reader.readLine();
        }

        System.out.println();

        if (isAnagram(line1, line2)) {
            System.out.println("Рядки є анаграмами");
        } else {
            System.out.println("Рядки не є анаграмами");
        }
    }

    static boolean isAnagram(String text1, String text2) {
        String[] texts = {text1, text2};

        for (int i = 0; i < texts.length; i++) {
            texts[i] = texts[i].replaceAll("[^a-zA-Zа-яА-Я]", "");
            texts[i] = texts[i].toLowerCase();
        }

        if (texts[0].length() != texts[1].length()) {
            return false;
        }

        StringBuilder letters = new StringBuilder(texts[0]);

        for (int i = 0; i < texts[1].length(); i++) {
            String letter = texts[1].substring(i, i + 1);

            if (letters.indexOf(letter) != -1) {
                letters.insert(i, "");
            } else {
                return false;
            }
        }

        return true;
    }
}
