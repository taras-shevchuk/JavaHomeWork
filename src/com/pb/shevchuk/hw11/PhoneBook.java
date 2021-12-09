package com.pb.shevchuk.hw11;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();

        System.out.println("Вітаємо вас");

        while (contacts.isEmpty()) {
            System.out.println("У телефонній книзі поки немає контактів. Яку дію бажаєте виконати?");
            System.out.println("a - додати контакт");
            System.out.println("l - завантажити дані зі файлу");

            switch (reader.readLine().toLowerCase().trim()) {
                case "add":
                    try {
                        phoneBook.add();
                    } catch (IllegalArgumentException exception) {
                        System.out.println(exception.getMessage());
                    }

                    for (Contact contact : contacts) {
                        System.out.println(contact);
                    }

                    break;
                case "l":
                    break;
            }
        }

        while (true) {
            switch (reader.readLine().toLowerCase().trim()) {
                case "add":
                    phoneBook.add();
                    break;
                case "search":
                    phoneBook;
                case "remove":
                    phoneBook.remove("taras");
            }
        }
    }

    private void add() throws Exception {
        HashMap<String, String> data = new HashMap<>();
        List<String> phones = new ArrayList<>();

        data.put("name", "");
        data.put("birthDate", "");
        data.put("address", "");

        while (true) {
            if (data.get("name").equals("")) {
                System.out.println("Введіть, будь ласка, ваше ім'я");
                data.put("name", reader.readLine());                                              //reader.readLine();
            }

            do {
                if (!phones.isEmpty()) {
                    System.out.println("Поточний список номерів нового контакту:");
                    System.out.println(phones);
                }

                System.out.println("Введіть номер телефону");
                phones.add(reader.readLine());

                System.out.println("Бажаєте додати ще один номер телефону? (yes / no)");
            } while (!reader.readLine().trim().equalsIgnoreCase("no"));

            if (data.get("birthDate").equals("")) {
                System.out.println("Введіть, будь ласка, дату народження");
                data.put("birthDate", reader.readLine());
            }

            if (data.get("address").equals("")) {
                System.out.println("Введіть, будь ласка, вашу адресу");
                data.put("address", reader.readLine());
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

    public void search(String field, String value) {

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
