package com.pb.shevchuk.hw4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Anagram {
    public static void main(String[] args) throws IOException {
        String[] lines = new String[2];

//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            lines[0] = "Аз есмь строка, живу я, мерой остр";     // reader.readLine();
            lines[1] = "За семь морей ростка я вижу рост.";     // reader.readLine();
//        }

        char[][] arrays = new char[2][];

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceAll("[^a-zA-Zа-яА-Я]", "");
            lines[i] = lines[i].toLowerCase();
            arrays[i] = lines[i].toCharArray();
        }

        if (arrays[0].length != arrays[1].length) {
            System.out.println("diff");
        }

        for (char[] array : arrays) {
            Arrays.sort(array);
        }

        if (Arrays.equals(arrays[0], arrays[1])) {
            System.out.println("eqvls");
        }
    }
}