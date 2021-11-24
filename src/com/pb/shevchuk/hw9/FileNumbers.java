package com.pb.shevchuk.hw9;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileAlreadyExistsException;
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

        FileNumbers fn = new FileNumbers();

        fn.createNumbersFile();
        fn.createOddNumbersFile();
    }

    public void createNumbersFile() {

        final String FILE_NAME = "src/com/pb/shevchuk/hw9/numbers.txt";

        Path numbersFile;

        try {
            LOGGER.log(Level.INFO, "Створюємо файл numbersFile");
            numbersFile = Files.createFile(Paths.get(FILE_NAME));

        } catch(FileAlreadyExistsException exception) {

            LOGGER.throwing(getClass().getName(), "createNubersFile", exception);

            LOGGER.log(Level.WARNING, "Файл уже існує. Створюємо об'єкт класу Path");
            numbersFile = Paths.get(FILE_NAME);
        } catch (Exception exception) {
            LOGGER.throwing(getClass().getName(), "createNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "Трапилась помилка");
            return;
        }

        try(BufferedWriter writer = Files.newBufferedWriter(numbersFile, StandardCharsets.UTF_8)) {

            Random random = new Random();
            String[] array = new String[10];

            LOGGER.log(Level.INFO, "Створюємо пустий масив строк");
            LOGGER.log(Level.INFO, "Заповнюємо масив випадковими числами");
            LOGGER.log(Level.INFO, "Об'єднуємо масив у строку");
            LOGGER.log(Level.INFO, "Записуємо строку у файл");

            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < array.length; j++) {

                    String number = Integer.toString(1 + random.nextInt(99));
                    array[j] = (number.length() == 1) ? " " + number : number;
                }

                String line = String.join(" ", array);

                writer.write(line);
                writer.newLine();
            }
        } catch (IOException exception) {
            LOGGER.throwing(getClass().getName(), "createNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "Трапилась помилка");
        }
    }

    public void createOddNumbersFile() {

        final String FILE_NAME_NUMBERS = "src/com/pb/shevchuk/hw9/numbers.txt";
        final String FILE_NAME_ODD_NUMBERS = "src/com/pb/shevchuk/hw9/odd-numbers.txt";

        Path oddNumbersFile;

        try {
            LOGGER.log(Level.INFO, "Створюємо файл oddNumbersFile");
            oddNumbersFile = Files.createFile(Paths.get(FILE_NAME_ODD_NUMBERS));

        } catch (FileAlreadyExistsException exception) {

            LOGGER.throwing(getClass().getName(), "createNubersFile", exception);

            LOGGER.log(Level.WARNING, "Файл уже існує. Створюємо об'єкт класу Path");
            oddNumbersFile = Paths.get(FILE_NAME_ODD_NUMBERS);
        } catch (Exception exception) {
            LOGGER.throwing(getClass().getName(), "createOddNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "Трапилась помилка");
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(oddNumbersFile, StandardCharsets.UTF_8)) {

            LOGGER.log(Level.INFO, "Зчитуємо усі строки файлу із випадковими числами");
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME_NUMBERS));

            LOGGER.log(Level.INFO, "Перебираємо строки, заміняємо у них парні числа на нулі, записуємо у інший файл");
            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);
                String newLine = line.replaceAll("[1-9 ][02468](?!\\d)", " 0");

                lines.set(i, newLine);

                writer.write(lines.get(i));
                writer.newLine();
            }
        } catch (Exception exception) {
            LOGGER.throwing(getClass().getName(), "createOddNumbersFile", exception);
            LOGGER.log(Level.SEVERE, "Трапилась помилка");
        }
    }
}
