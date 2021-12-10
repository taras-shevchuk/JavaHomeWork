package com.pb.shevchuk.hw11;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final List<Contact> buffer = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();

        System.out.println("Вітаємо вас");

        while (contacts.isEmpty()) {
            System.out.println("У телефонній книзі поки немає контактів. Яку дію бажаєте виконати?");
            System.out.println("add - додати контакт");
            System.out.println("load - завантажити дані зі файлу");

            switch (reader.readLine().toLowerCase().trim()) {
                case "add":
                    try {
                        phoneBook.add("taras", Collections.singletonList("3700"), "29/08/1995", "ternopil");
                    } catch (IllegalArgumentException exception) {
                        System.out.println(exception.getMessage());
                    }

                    System.out.println(contacts);

                    break;
                case "l":
                    break;
            }
        }

        while (true) {
            System.out.println("Яку дію бажаєте виконати?");
            System.out.println("add - додати контакт");
            System.out.println("search - пошук контактів");
            System.out.println("remove - видалити контакт");

            switch (reader.readLine().toLowerCase().trim()) {
                case "add":
                    phoneBook.add("petro", Arrays.asList("7737", "911"), "29/06/2000", "ternopil");
                    System.out.println(contacts);
                    break;

                case "search":
                    System.out.println("Введіть будь ласка, критерій та дані за якими виконати пошук");
                    phoneBook.search(reader.readLine(), reader.readLine());
                    break;

                case "remove":
                    phoneBook.remove("taras");
            }
        }
    }

    private void add(String name, List<String> phone, String birth, String address) throws Exception {
        HashMap<String, String> data = new HashMap<>();
        List<String> phones = new ArrayList<>();

        data.put("name", "");
        data.put("birthDate", "");
        data.put("address", "");

        while (true) {
            if (data.get("name").equals("")) {
                System.out.println("Введіть, будь ласка, ваше ім'я");
                data.put("name", name);                   //reader.readLine();
            }

            do {
                System.out.println("Введіть номер телефону");
                phones.addAll(phone);

                if (!phones.isEmpty()) {
                    System.out.println("Поточні номери нового контакту:");
                    System.out.println(phones);
                }

                System.out.println("Бажаєте додати ще один номер телефону? (yes / no)");
            } while (!reader.readLine().trim().equalsIgnoreCase("no"));

            if (data.get("birthDate").equals("")) {
                System.out.println("Введіть, будь ласка, дату народження у форматі дд/мм/рррр");
                data.put("birthDate", birth);
            }

            if (data.get("address").equals("")) {
                System.out.println("Введіть, будь ласка, вашу адресу");
                data.put("address", address);
            }

            LocalDate birthDate = null;

            if (!data.get("birthDate").equals("")) {
                birthDate = LocalDate.parse(
                        data.get("birthDate"),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                );
            }

            if (data.get("name").equals("") || phones.isEmpty()) {
                throw new IllegalArgumentException(
                        "На жаль, не зазначено жодного номеру телефону або імені контакту - ці дані обов'язкові"
                );
            } else {
                Contact contact = new Contact(
                        data.get("name"),
                        birthDate,
                        phones,
                        data.get("address")
                );
                contacts.add(contact);
                break;
            }
        }
    }

    public void search(String fieldName, String value) throws IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = contacts.get(0).getClass();
        Field field;
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        for (Contact contact : contacts) {
            if (fieldName.equalsIgnoreCase("name") || fieldName.equalsIgnoreCase("address")) {
                if (field.get(contact).equals(value)) {
                    buffer.add(contact);
                    System.out.println(contact);
                }
            } else if (fieldName.equalsIgnoreCase("phones")) {
                if (((ArrayList<?>) field.get(contact)).contains(value)) {
                    buffer.add(contact);
                    System.out.println(contact);
                }
            } else if (fieldName.equalsIgnoreCase("birthDate")) {
                if (String.valueOf(contact.birthDate).equals(value)) {
                    buffer.add(contact);
                    System.out.println(contact);
                }
            }
        }
    }

    public void remove(String name) {
        Contact con = contacts.get(1);
        con.name = "dot";
        contacts.set(1, con);
        contacts.removeIf(c -> c.name.equals(name));
    }

    public class Contact {
        private String name;
        private LocalDate birthDate;
        private final List<String> phones;
        private String address;
        private LocalDate edited;

        public Contact(String name, LocalDate birthDate, List<String> phones, String address) {
            this.name = name;
            this.birthDate = birthDate;
            this.phones = phones;
            this.address = address;
            edited = LocalDate.now();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public List<String> getPhone() {
            return phones;
        }

        public void setPhone(List<String> phone) {
            phones.addAll(phone);
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public LocalDate getEdited() {
            return edited;
        }

        @Override
        public String toString() {
            return "Contact{" +
                    "name='" + name + '\'' +
                    ", birthDate=" + birthDate +
                    ", phones='" + phones + '\'' +
                    ", address='" + address + '\'' +
                    ", edited=" + edited +
                    '}';
        }
    }
}
