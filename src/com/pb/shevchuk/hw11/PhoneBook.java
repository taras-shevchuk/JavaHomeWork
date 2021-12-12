package com.pb.shevchuk.hw11;

import java.io.*;
import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    private static final Map<Integer, Contact> buffer = new HashMap<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас!");

//      while ()
        if (contacts.isEmpty()) {
            System.out.println("У телефонній книзі поки немає контактів");
            System.out.println();

//            System.out.println("оберіть дію:");
//            System.out.println(
//                    "add - додати контакт\n" +
//                    "load - завантажити дані зі файлу\n"
//            );
//            System.out.println();
        }

//        while (true) {
            System.out.println("оберіть дію:");
            System.out.println(
                    "add - додати контакт\n" +
                    "load - завантажити дані зі файлу\n" +
                    "search - пошук контактів\n" +
                    "remove - видалити контакт\n" +
                    "print - \n" +
                    "etid - \n" +
                    "write - \n" +
                    "exit - "
            );

//            switch (reader.readLine().toLowerCase().trim()) {
//                case "add":
//
//                    break;

            switch ("search") {
                case "search":
                    System.out.println();
                    phoneBook.add("taras", "3700", "1995-08-29", "ternopil");
                    phoneBook.add("petro", "7737   911", "2000-06-29", "ternopil");
                    System.out.println(contacts);
                    System.out.println();

                    System.out.println("Введіть критерій та дані за якими виконати пошук");
                    System.out.println("3700");
                    phoneBook.search("edited", "2021-12-12");
                    System.out.println();
                    System.out.println(buffer);

                    break;
            }
//        }
    }

    private void add(String name, String phones, String birth, String address) {
        do {
            try {
                System.out.println("введіть, будь ласка, ваше ім'я");

                if (name.trim().equals("")) {
                    throw new IllegalArgumentException("ім'я не було введено");
                }

                System.out.println(name);
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (name.trim().equals(""));

        List<String> phonesList = new ArrayList<>();

        do {
            try {
                System.out.println("введіть номери телефонів через пробіл");

                if (phones.equals("")) {
                    throw new IllegalArgumentException("не введено жодного номеру телефону");
                }

                for (String phone : phones.trim().split(" +")) {
                    if (phone.matches("(\\+?)(\\d{3,15})")) {
                        phonesList.add(phone);;
                        continue;
                    }

                    System.out.printf("Номер %s має некоректний формат\n", phone);
                }

                System.out.println(String.join(", ", phones.trim().split(" +")));
                System.out.println();

                System.out.println("поточні номери нового контакту:");
                System.out.println(phonesList);
                System.out.println();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("бажаєте ввести ще один номер? (для згоди введіть yes)");
            System.out.println();

//        } while (reader.readLine().trim().equalsIgnoreCase("yes"));
        } while (false);

        LocalDate birthDate = null;

        try {
            System.out.println("введіть дату народження у форматі \"рррр-мм-дд\n");

            try {
                birthDate = LocalDate.parse(birth);
                System.out.println(birthDate);
                System.out.println();

            } catch (DateTimeException e) {
                System.out.println("некоректний формат дати");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        {
            System.out.println("введіть вашу адресу проживання");
            System.out.println(address);
            System.out.println();
        }

        Contact contact = new Contact(name, birthDate, phonesList, address);
        contacts.add(contact);
    }

    public void search(String fieldName, String value) throws IllegalAccessException, NoSuchFieldException {
        Class<?> clazz = contacts.get(0).getClass();
        Field field;
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        buffer.clear();

        for (Contact contact : contacts) {
            Object prop = field.get(contact);

            if (prop instanceof String) {
                if (prop.equals(value)) {
                    buffer.put(buffer.size() + 1, contact);
                }

            } else if (prop instanceof ArrayList<?>) {
                if (((ArrayList<?>) prop).contains(value)) {
                    buffer.put(buffer.size() + 1, contact);
                }

            } else if (prop instanceof LocalDate) {
                if (String.valueOf(prop).equals(value)) {
                    buffer.put(buffer.size() + 1, contact);
                }

            } else if (prop instanceof LocalDateTime) {
                String date = ((LocalDateTime) prop).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                if (date.equals(value)) {
                    buffer.put(buffer.size() + 1, contact);
                }
            }
        }
    }



//    public void remove(String name) {
//        Contact con = contacts.get(1);
//        con.name = "dot";
//        contacts.set(1, con);
//        contacts.removeIf(c -> c.name.equals(name));
//    }

    public class Contact {
        private String name;
        private LocalDate birthDate;
        private final List<String> phones;
        private String address;
        private LocalDateTime edited;

        public Contact(String name, LocalDate birthDate, List<String> phones, String address) {
            this.name = name;
            this.birthDate = birthDate;
            this.phones = phones;
            this.address = address;
            edited = LocalDateTime.now();
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

        public LocalDateTime getEdited() {
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
