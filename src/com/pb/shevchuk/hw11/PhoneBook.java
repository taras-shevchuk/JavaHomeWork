package com.pb.shevchuk.hw11;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();

        try (
                InputStreamReader inStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inStream)
        ) {
            System.out.println("Вітаємо вас");

            if (contacts.isEmpty()) {
                System.out.println("У телефонній книзі поки немає контактів. Яку дію бажаєте виконати?");
                System.out.println("a - додати контакт");
                System.out.println("l - завантажити дані зі файлу");

                switch (reader.readLine().toLowerCase().trim()) {
                    case "a":
                        phoneBook.addContact(reader);

                        for (Contact contact : contacts) {
                            System.out.println(contact);
                        }

                        break;
                    case "l":
                        phoneBook.removeContact("taras");
                }
            }

//            while (true) {
//                switch (reader.readLine().toLowerCase().trim()) {
//                    case "a":
//                        phoneBook.addContact(reader);
//                        break;
//                    case "r":
//                        phoneBook.removeContact("taras");
//                }
//            }
        }
    }

    private void addContact(BufferedReader reader) throws Exception {
        HashMap<String, String> data = new HashMap<>();
        String name = "";
        List<String> phones = new ArrayList<>();

        while (true) {
            if (name.equals("")) {
                System.out.println("Введіть, будь ласка, ваше ім'я");
                name = reader.readLine();
                data.put("name", name);                                              //reader.readLine();
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

            data.put("birthDate", reader.readLine());
            data.put("address", reader.readLine());
            LocalDate birthDate = LocalDate.parse(
                    data.get("birthDate"),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
            );

            if (name.equals("") || phones.isEmpty()) {
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

    public void removeContact(String name) {
        Contact con = contacts.get(1);
        con.name = "dot";
        contacts.set(1, con);
        contacts.removeIf(c -> c.name.equals(name));
    }

    public class Contact {
        private String name;
        private LocalDate birthDate;
        private List<String> phones;
        private String address;
        private LocalDate edited;

        public Contact(String name, LocalDate birthDate, List<String> phones, String address) {
            this.name = name;
            this.birthDate = birthDate;
            this.phones = phones;
            this.address = address;
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
