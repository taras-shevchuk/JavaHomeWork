package com.pb.shevchuk.hw4;

import com.sun.xml.internal.ws.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CapitalLetter {
    public static void main(String[] args) throws IOException {
        String line;

        System.out.println("Введіть, будь ласк, рядок тексту\n");

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            line = reader.readLine();
        }

        System.out.println(capitalize(line));
    }

    static String capitalize(String text) {
        String[] words = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            words[i] = StringUtils.capitalize(words[i]);
        }

        return String.join(" ", words);
    }
}
