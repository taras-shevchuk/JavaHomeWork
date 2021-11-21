package com.pb.shevchuk.hw9;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Random;

public class FileNumbers {

    public static void main(String[] args) throws Exception {

        FileNumbers fn = new FileNumbers();
        fn.createNumbersFile();
        fn.createOddNumbersFile();
    }

    public void createNumbersFile() throws Exception {

        final String FILE_NAME = "src/com/pb/shevchuk/hw9/numbers.txt";

        Path numbersFile;

        try {
            numbersFile = Files.createFile(Paths.get(FILE_NAME));

        } catch(FileAlreadyExistsException exception) {

            numbersFile = Paths.get(FILE_NAME);

        }

        try(BufferedWriter writer = Files.newBufferedWriter(numbersFile, StandardCharsets.UTF_8)) {

            Random random = new Random();

            for (int i = 0; i < 10; i++) {

                String[] array = new String[10];

                for (int j = 0; j < array.length; j++) {

                    String number = Integer.toString(1 + random.nextInt(99));
                    array[j] = (number.length() == 1) ? " " + number : number;
                }

                String line = String.join(" ", array);

                writer.write(line);
                writer.newLine();
            }
        }
    }

    public void createOddNumbersFile() throws Exception {

        final String FILE_NAME_NUMBERS = "src/com/pb/shevchuk/hw9/numbers.txt";
        final String FILE_NAME_ODD_NUMBERS = "src/com/pb/shevchuk/hw9/odd-numbers.txt";

        Path oddNumbers;

        try {
            oddNumbers = Files.createFile(Paths.get(FILE_NAME_ODD_NUMBERS));

        } catch(FileAlreadyExistsException exception) {

            oddNumbers = Paths.get(FILE_NAME_ODD_NUMBERS);

        }

        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME_NUMBERS));

        try (BufferedWriter writer = Files.newBufferedWriter(oddNumbers, StandardCharsets.UTF_8)) {

            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);
                String newLine = line.replaceAll("[1-9 ][02468](?!\\d)", " 0");

                lines.set(i, newLine);

                writer.write(lines.get(i));
                writer.newLine();
            }
        }
    }
}
