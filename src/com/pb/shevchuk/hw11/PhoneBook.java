package com.pb.shevchuk.hw11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    private static final List<Contact> buffer = new ArrayList<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас!");

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

        System.out.println(
                "яку дію бажаєте виконати із контактами?\n" +
                "\t" + String.join("\n\t", "add", "load", "sort", "search", "etid", "remove", "write", "exit")
        );

        switch ("add") {
            case "add":
                System.out.println();
                phoneBook.add("taras", "3700", "1995-08-29", "ternopil");
                phoneBook.add("petro", "7737", "1990-08-29", "ternopil");
                System.out.println(contacts);
                System.out.println();

//                phoneBook.add();
                break;

            case "load":
//                phoneBook.load();
                break;

            case "sort":
                phoneBook.sort();
                break;

            case "search":
//                phoneBook.search();
                break;

            case "edit":
                phoneBook.edit();
                break;

            case "remove":
//                phoneBook.remove();
                break;

            case "write":
//                phoneBook.write();
                break;

            case "exit":
                return;

            default:
                System.out.println("введено некоректну команду");
        }

//      while ()

//      while (true) {
//          switch (reader.readLine().toLowerCase().trim()) {
//              case "add":
//
//              break;
//        }
    }

    public static void printBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            System.out.printf("%d - %s\n", (i + 1), buffer.get(i));
        }
    }

    private void add(String name, String phone, String date, String address) throws Exception {
        do {
            System.out.println("введіть, будь ласка, ваше ім'я");
            System.out.println(name);
            System.out.println();

        } while (name.trim().equals(""));

        List<String> phones = new ArrayList<>();
//        String phone;

        do {
            if (phones.isEmpty()) {
                System.out.println("введіть номер телефону");
                System.out.println(phone);
                System.out.println();
            }

            try {
                if (!phone.matches("\\d{3,12}")) {
                    throw new IllegalArgumentException("введено некоректні дані. Номер має містити від 3 до 12 цифр");
                }

                for (Contact contact : contacts) {
                    if (contact.phones.contains(phone)) {
                        throw new IllegalArgumentException("такий номер уже зареєстрований");
                    }
                }

                phones.add(phone);

                System.out.println("поточні номери контакту:");
                System.out.println("\t" + String.join("\n\t", phones));
                System.out.println();

                System.out.println("введіть ще один номер телефону або next, щоб продовжити");

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!((phone = reader.readLine()).equals("next") && !phones.isEmpty()));

        LocalDate birthDay = null;

        try {
            System.out.println("введіть дату народження (формат рррр-мм-дд)");
            birthDay = LocalDate.parse(date);
            System.out.println(birthDay);
            System.out.println();

        } catch (IllegalArgumentException e) {
            System.out.println("дату не збережено, некоректний формат");
        }

        System.out.println("введіть вашу адресу проживання");
        System.out.println(address);
        System.out.println();

        Contact contact = new Contact(name, birthDay, phones, address);
        contacts.add(contact);
    }

    public void sort() {
        System.out.println(
                "введіть критерій для сортування:\n" +
                "name - за іменем\n" +
                "birthDate - за датою народження"
        );
        String field = "birthDate";
        System.out.println();

        if (field.equals("name")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        } else if (field.equals("birthDate")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.birthDate.compareTo(o2.birthDate);
                }
            });
        } else {
            System.out.println("введено некоректне поле");
            return;
        }

        buffer.clear();
        buffer.addAll(contacts);
        printBuffer();
    }

    public void search(String fieldName, String value) {
        System.out.println(
                "оберіть критерій пошуку:\n" +
                    "name - за іменем\n" +
                    "phones - за номером телефону\n" +
                    "bdayDate - за датою народження\n" +
                    "address - за адресою\n" +
                    "edited - за датою редагування\n"
        );

        System.out.println("введіть дані для пошуку");
        System.out.println();

        Class<Contact> clazz = Contact.class;

        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            buffer.clear();

            for (Contact contact : contacts) {
                Object prop = field.get(contact);

                if (prop instanceof String) {
                    if (prop.equals(value)) {
                        buffer.add(contact);
                    }

                } else if (prop instanceof List<?>) {
                    if (((ArrayList<?>) prop).contains(value)) {
                        buffer.add(contact);
                    }

                } else if (prop instanceof LocalDate) {
                    if (String.valueOf(prop).equals(value)) {
                        buffer.add(contact);
                    }

                } else if (prop instanceof LocalDateTime) {
                    String date = ((LocalDateTime) prop).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    if (date.equals(value)) {
                        buffer.add(contact);
                    }
                }
            }

            if (!buffer.isEmpty()) {
                System.out.println("результати пошуку:");

                printBuffer();
                System.out.println();
            }
        } catch (NoSuchFieldException e) {
            System.out.println("введено помикловий критерій пошуку");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void edit() throws NoSuchFieldException, IllegalAccessException {
        if (buffer.isEmpty()) {
            System.out.println("address, ternopil");
            System.out.println();
            this.search("address", "ternopil");
        }

        System.out.println("оберіть контакт для редагування");
        int index = 0;

        try {
            Contact contact = contacts.get(index);

            System.out.println("які дані бажаєте редагувати?");
            System.out.println(contact);

            String fieldName = "phones";
            String value = "777";

            Class<Contact> clazz = Contact.class;
            Field field = clazz.getDeclaredField(fieldName);

            if (!fieldName.equals("edited")) {
                field.setAccessible(true);
            }

            Object prop = field.get(contact);

            if (prop instanceof String) {
                field.set(contact, value);

            } else if (prop instanceof LocalDate) {
                LocalDate newDate = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                field.set(contact, newDate);

            } else if (prop instanceof List<?>) {
                ArrayList<String> phones = (ArrayList<String>) prop;
                System.out.println("який номер бажаєте редагувати?");

                for (int i = 0; i < phones.size(); i++) {
                    System.out.printf("%d - %s", (i + 1), phones.get(i));
                    System.out.println();
                }

                phones.set(index, value);
            }

            System.out.println(contact);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("введено помилковий порядковий номер");
        }
    }

//    public void write() {
//
//    }

    public void remove() {
        if (buffer.isEmpty()) {
            System.out.println("address, ternopil");
            System.out.println();
            this.search("address", "ternopil");
        }

        System.out.println("введіть номер контакту, який бажаєте видалити");
        System.out.println();
        int index = 3;

        try {
            contacts.remove(buffer.get(index - 1));
            buffer.remove(index - 1);
            System.out.println("контакт видалено");

        } catch (IndexOutOfBoundsException e) {
            System.out.println("введено помилковий порядковий номер");
        }
    }

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
            return "ім'я=" + name +
                   ", дата народження=" + birthDate +
                   ", номер=" + phones +
                   ", адреса=" + address +
                   ", редаговано=" + edited;
        }
    }
}
