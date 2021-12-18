package com.pb.shevchuk.hw11;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private final List<Contact> contacts = new ArrayList<>();
    private final List<Contact> buffer = new ArrayList<>();
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final File FILE = Paths.get("src/com/pb/shevchuk/hw11/contacts.json").toFile();

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас!");

        while (true) {
            while (phoneBook.contacts.isEmpty()) {
                System.out.println("У телефонній книзі поки немає контактів");
                System.out.println();

                System.out.println(
                        "яку дію із контактами бажаєте виконати?\n" +
                                "\tadd\n" +
                                "\tload\n" +
                                "\texit"
                );

                switch (reader.readLine().trim().toLowerCase()) {
                    case "add":
                        System.out.println();
                        phoneBook.add();
                        break;

                    case "load":
                        System.out.println();
                        phoneBook.load();
                        break;

                    case "exit":
                        return;

                    default:
                        System.out.println("введено некоректну команду");
                }
            }

            System.out.println(
                "яку дію із контактами бажаєте виконати?\n" +
                        String.join("\n\t", "\tadd", "load", "sort", "search", "edit", "remove", "write", "exit")
            );

            switch (reader.readLine().trim().toLowerCase()) {
                case "add":
                    System.out.println();
                    phoneBook.add();
                    break;

                case "load":
                    System.out.println();
                    phoneBook.load();
                    break;

                case "sort":
                    System.out.println();
                    phoneBook.sort();
                    break;

                case "search":
                    System.out.println();
                    phoneBook.search();
                    break;

                case "edit":
                    System.out.println();
                    phoneBook.edit();
                    break;

                case "remove":
                    System.out.println();
                    phoneBook.remove();
                    break;

                case "write":
                    System.out.println();
                    phoneBook.write();
                    break;

                case "exit":
                    System.out.println();
                    System.out.println("До побачення");
                    return;

                default:
                    System.out.println("помилкова команда");
            }
        }
    }

    private void printBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            System.out.printf("%d - %s\n", (i + 1), buffer.get(i));
        }
    }

    public void add() throws Exception {
        System.out.println("введіть ваше ім'я");
        String name = reader.readLine().trim();
        System.out.println();

        System.out.println("введіть номер телефону");
        List<String> phones = new ArrayList<>();
        String phone;

        while (!(phone = reader.readLine().trim().toLowerCase()).equals("next")) {
            System.out.println();

            if (phone.matches("\\d{3,12}")) {
                phones.add(phone);
            } else {
                System.out.println("помилковий формат");
            }

            System.out.println("поточні номери контакту:");
            System.out.println("\t" + String.join("\n\t", phones));
            System.out.println();
            System.out.println("введіть номер телефону або next, щоб продовжити");

        }

        System.out.println();
        System.out.println("введіть дату народження (формат дд/мм/рррр)");
        LocalDate birthday = LocalDate.parse(reader.readLine().trim(), DateTimeFormatter.ofPattern("d/MM/yyyy"));
        System.out.println();

        System.out.println("введіть вашу адресу проживання");
        String address = reader.readLine().trim();
        System.out.println();

        Contact contact = new Contact(name, phones, birthday, address);
        contacts.add(contact);
        System.out.println("контакт додано у телефону книгу");
        System.out.println();
    }

    public void load() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        mapper.registerModule(module);

        List<Contact> newContacts = mapper.readValue(FILE, new TypeReference<List<Contact>>() { });
        contacts.addAll(newContacts);
        System.out.println("контакти завантажено");
        System.out.println();
    }

    public void sort() throws IOException {
        buffer.clear();

        System.out.println(
                "введіть критерій для сортування:\n" +
                "\tname - за іменем\n" +
                "\tbirthDate - за датою народження"
        );
        String fieldName = reader.readLine().trim().toLowerCase();

        if (fieldName.equals("name")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else if (fieldName.equals("birthday")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.getBirthday().compareTo(o2.getBirthday());
                }
            });
        } else {
            System.out.println("введено некоректний критерій");
            return;
        }

        buffer.addAll(contacts);
        printBuffer();
        System.out.println();
    }

    public void search() throws IOException, IllegalAccessException {
        buffer.clear();

        System.out.println("введіть дані для пошуку контактів");
        String line = reader.readLine().trim().toLowerCase();
        System.out.println();

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
            System.out.println("контактів не знайдено");

        } else {
            System.out.println("результати пошуку:");
            printBuffer();
            System.out.println();
        }
    }

    public void edit() throws IOException, IllegalAccessException {
        if (buffer.isEmpty()) {
            search();

            if (buffer.isEmpty()) return;
        } else {
            printBuffer();
        }

        System.out.println("оберіть контакт для редагування");
        Contact contact = buffer.get(Integer.parseInt(reader.readLine().trim()) - 1);
        System.out.println();

        System.out.println(
                "оберіть поле для редагування\n" +
                String.join("\n\t", Arrays.asList("\tname", "phone", "birthday", "address"))
        );
        String fieldName = reader.readLine().trim().toLowerCase();
        System.out.println();

        switch (fieldName) {
            case "name":
                System.out.println("введіть нове ім'я");
                contact.setName(reader.readLine().trim());
                break;

            case "phone":
                System.out.println("який номер бажаєте редагувати?");
                List<String> phones = contact.getPhones();

                for (int i = 0; i < phones.size(); i++) {
                    System.out.printf("%d - %s\n", (i + 1), phones.get(i));
                }

                int index = Integer.parseInt(reader.readLine()) - 1;
                System.out.println();

                System.out.println("введіть новий номер телефону");
                phones.set(index, reader.readLine().trim());
                contact.setPhones(phones);
                break;

            case "birthday":
                System.out.println("введіть нову дату народження");
                contact.setBirthday(LocalDate.parse(reader.readLine().trim(), DateTimeFormatter.ofPattern("d/MM/yyyy")));
                break;

            case "address":
                System.out.println("введіть нову адресу");
                contact.setAddress(reader.readLine().trim());
                break;

            default:
                System.out.println("поле для редагування введено помилково");
        }

        System.out.println();

        contact.setEdited(LocalDateTime.now());
        System.out.println("дані редаговано");
        System.out.println(contact);
        System.out.println();
    }

    public void write() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(module);

        mapper.writeValue(FILE, contacts);
        System.out.println("контакти записано у файл");
        System.out.println();
    }

    public void remove() throws IOException, IllegalAccessException {
        if (buffer.isEmpty()) {
            this.search();

            if (buffer.isEmpty()) return;

        } else {
            printBuffer();
            System.out.println();
        }

        System.out.println("введіть номер контакту, який бажаєте видалити");
        int i = Integer.parseInt(reader.readLine().trim()) - 1;
        contacts.remove(buffer.get(i));
        buffer.remove(i);
        System.out.println("контакт видалено");
        System.out.println();

        printBuffer();
        System.out.println();
    }
}

