package com.pb.shevchuk.hw9;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.logging.*;

public class FileNumbers {

    private static final Logger LOGGER = Logger.getLogger(FileNumbers.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        LOGGER.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new MyFormatter());

        LOGGER.addHandler(handler);
    }

    public static class MyFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getMessage() + System.lineSeparator();
        }
    }

    public static void main(String[] args) {

        LOGGER.entering(FileNumbers.class.getName(), "main");

        FileNumbers fn = new FileNumbers();
        fn.createNumbersFile();
        fn.createOddNumbersFile();

        LOGGER.exiting(FileNumbers.class.getName(), "main");
    }

    public void createNumbersFile() {
        try(
                BufferedWriter writer = Files.newBufferedWriter(
                        Paths.get("src/com/pb/shevchuk/hw9/numbers.txt"),
                        StandardCharsets.UTF_8
                )
        ) {

            Random random = new Random();

            LOGGER.log(Level.INFO, "створюємо пустий масив строк");
            String[] array = new String[10];

            LOGGER.log(Level.INFO, "Заповнюємо масив, об'єднуємо у строку, записуємо у файл");
            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < array.length; j++) {

                    String number = Integer.toString(1 + random.nextInt(99));
                    array[j] = (number.length() == 1) ? " " + number : number;
                }

                String line = String.join(" ", array);

                writer.write(line);
                writer.newLine();

                LOGGER.log(Level.FINER, "строка = " + (i + 1));
            }
        } catch (IOException exception) {

            LOGGER.throwing(getClass().getName(), "createNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "трапилась помилка");
        }
    }

    public void createOddNumbersFile() {
        try (
                BufferedWriter writer = Files.newBufferedWriter(
                        Paths.get("src/com/pb/shevchuk/hw9/odd-numbers.txt"),
                        StandardCharsets.UTF_8
                )
        ) {
            LOGGER.log(Level.INFO, "зчитуємо строки із випадковими числами");
            List<String> lines = Files.readAllLines(Paths.get("src/com/pb/shevchuk/hw9/numbers.txt"));

            LOGGER.log(Level.INFO, "заміняємо парні числа на нулі, записуємо у інший файл");
            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);
                String newLine = line.replaceAll("[1-9 ][02468](?!\\d)", " 0");

                lines.set(i, newLine);

                writer.write(lines.get(i));
                writer.newLine();
            }
        } catch (Exception exception) {
            LOGGER.throwing(getClass().getName(), "createOddNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "трапилась помилка");
        }
    }
}
