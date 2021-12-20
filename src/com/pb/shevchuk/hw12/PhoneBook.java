package com.pb.shevchuk.hw12;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pb.shevchuk.hw11.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

public class PhoneBook {
    private final List<com.pb.shevchuk.hw11.Contact> contacts = new ArrayList<>();
    private final List<Contact> buffer = new ArrayList<>();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final File FILE = Paths.get("src/com/pb/shevchuk/hw12/contacts.json").toFile();

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас!");

        while (true) {
            while (phoneBook.contacts.isEmpty()) {
                System.out.println("У телефонній книзі поки немає контактів");

                System.out.println(
                    "\nяку дію із контактами бажаєте виконати?\n" +
                    String.join("\n\t", "\tadd", "load", "exit")
                );

                switch (readLine()) {
                    case "add":
                        phoneBook.add();
                        break;

                    case "load":
                        phoneBook.load();
                        break;

                    case "exit":
                        System.out.println("\nДо побачення");
                        return;

                    default:
                        System.out.println("введено некоректну команду");
                }
            }

            System.out.println(
                "\nяку дію із контактами бажаєте виконати?\n" +
                String.join("\n\t", "\tadd", "load", "sort", "search", "edit", "remove", "write", "exit")
            );

            switch (readLine()) {
                case "add":
                    phoneBook.add();
                    break;

                case "load":
                    phoneBook.load();
                    break;

                case "sort":
                    phoneBook.sort();
                    break;

                case "search":
                    phoneBook.search();
                    break;

                case "edit":
                    phoneBook.edit();
                    break;

                case "remove":
                    phoneBook.remove();
                    break;

                case "write":
                    phoneBook.write();
                    break;

                case "exit":
                    System.out.println("\nДо побачення");
                    return;

                default:
                    System.out.println("помилкова команда");
            }
        }
    }

    public static String readLine() throws IOException {
        return reader.readLine().trim().toLowerCase();
    }

    private void print(List<?> list) {
        IntStream.range(1, (list.size() + 1))
                 .forEach(
                     i -> System.out.printf("%d - %s\n", i, list.get(i - 1))
                 );
    }

    public void add() throws Exception {
        System.out.println("\nвведіть ваше ім'я");
        String name = reader.readLine().trim();

        System.out.println("\nвведіть номер телефону");
        List<String> phones = new ArrayList<>();
        String phone;

        while (!(phone = readLine()).equals("next")) {
            if (phone.matches("\\d{3,12}")) {
                phones.add(phone);
            } else {
                System.out.println("помилковий формат");
            }

            System.out.println("\nпоточні номери контакту:");
            System.out.println("\t" + String.join("\n\t", phones));

            System.out.println("\nвведіть номер телефону або next, щоб продовжити");
        }

        System.out.println("\nвведіть дату народження (формат дд/мм/рррр)");
        LocalDate birthday = LocalDate.parse(readLine(), DateTimeFormatter.ofPattern("d/MM/yyyy"));

        System.out.println("\nвведіть вашу адресу проживання");
        String address = reader.readLine().trim();

        Contact contact = new Contact(name, phones, birthday, address);
        contacts.add(contact);
        System.out.println("\nконтакт додано у телефону книгу");
    }

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);

        List<Contact> newContacts = mapper.readValue(FILE, new TypeReference<List<Contact>>() { });
        contacts.addAll(newContacts);
        System.out.println("\nконтакти завантажено");
    }

    public void sort() throws IOException {
        buffer.clear();

        System.out.println(
            "\nвведіть критерій для сортування:\n" +
                    "\tname - за іменем\n" +
                    "\tbirthday - за датою народження"
        );
        String fieldName = readLine();

        if (fieldName.equals("name")) {
            contacts.sort(Comparator.comparing(Contact::getName));

        } else if (fieldName.equals("birthday")) {
            contacts.sort(Comparator.comparing(Contact::getBirthday));

        } else {
            System.out.println("введено некоректний критерій");
            return;
        }

        buffer.addAll(contacts);
        System.out.println();
        print(buffer);
    }

    public void search() throws IOException, IllegalAccessException {
        buffer.clear();

        System.out.println("\nвведіть дані для пошуку контактів");
        String line = readLine();

        if (line.equals("")) {
            System.out.println("дані для пошуку не введено");
            return;
        }

        Class<Contact> clazz = Contact.class;

        for (Contact contact : contacts) {
            Set<String> values = new HashSet<>();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object prop = field.get(contact);

                if (prop instanceof String) {
                    values.add((String) prop);

                } else if (prop instanceof List<?>) {
                    values.addAll(contact.getPhones());

                } else if (prop instanceof LocalDate) {
                    values.add(contact.getBirthday().toString());

                } else if (prop instanceof LocalDateTime) {
                    values.add(contact.getEdited().toString());
                }
            }

            for (String value : values) {
                if (value.contains(line)) {
                    buffer.add(contact);
                    break;
                }
            }
        }

        if (buffer.isEmpty()) {
            System.out.println("\nконтактів не знайдено");

        } else {
            System.out.println("\nрезультати пошуку:");
            print(buffer);
        }
    }

    public void edit() throws IOException {
        System.out.println();
        print(contacts);

        System.out.println("\nоберіть контакт для редагування");
        Contact contact = contacts.get(Integer.parseInt(readLine()) - 1);

        System.out.println(
            "\nоберіть поле для редагування\n" +
            String.join("\n\t", Arrays.asList("\tname", "phone", "birthday", "address"))
        );
        String fieldName = readLine();

        switch (fieldName) {
            case "name":
                System.out.println("\nвведіть нове ім'я");
                contact.setName(reader.readLine().trim());
                break;

            case "phone":
                List<String> phones = contact.getPhones();
                System.out.println("\nякий номер бажаєте редагувати?");
                print(phones);
                int index = Integer.parseInt(reader.readLine()) - 1;

                System.out.println("\nвведіть новий номер телефону");
                phones.set(index, readLine());
                contact.setPhones(phones);
                break;

            case "birthday":
                System.out.println("\nвведіть нову дату народження");
                contact.setBirthday(LocalDate.parse(readLine(), DateTimeFormatter.ofPattern("d/MM/yyyy")));
                break;

            case "address":
                System.out.println("\nвведіть нову адресу");
                contact.setAddress(reader.readLine().trim());
                break;

            default:
                System.out.println("поле для редагування введено помилково");
        }

        contact.setEdited(LocalDateTime.now());
        System.out.println("\nдані редаговано");
        System.out.println(contact);
    }

    public void write() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);

        mapper.writeValue(FILE, contacts);
        System.out.println("\nконтакти записано у файл");
    }

    public void remove() throws IOException {
        System.out.println();
        print(contacts);

        System.out.println("\nвведіть номер контакту, який бажаєте видалити");
        contacts.remove(Integer.parseInt(readLine()) - 1);
        System.out.println("\nконтакт видалено");
    }
}
