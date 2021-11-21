package com.pb.shevchuk.hw9;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FileNumbers {

    public static void main(String[] args) throws IOException {

        FileNumbers FN = new FileNumbers();
        FN.createNumbersFile();
    }

    public void createNumbersFile() {

        final String FILE_NAME = "src/com/pb/shevchuk/hw9/numbers.txt";

        Path numbers = null;

        try {
            numbers = Files.createFile(Paths.get(FILE_NAME));

        } catch(FileAlreadyExistsException exception) {

            numbers = Paths.get(FILE_NAME);
        } catch (Exception exception) {
            System.out.println(exception);
        }

        try(BufferedWriter writer = Files.newBufferedWriter(numbers)) {
            Random random = new Random();

            for (int i = 0; i < 10; i++) {
                String[] arr = new String[10];

                for (int j = 0; j < arr.length; j++) {
                    String num = Integer.toString(1 + random.nextInt(99));
                    arr[j] = (num.length() == 1) ? " " + num : num;
                }

                String line = String.join(" ", arr);
                writer.write(line);
                writer.newLine();
            }
        } catch(Exception exception) {
            System.out.println(exception);
        }
    }
}
