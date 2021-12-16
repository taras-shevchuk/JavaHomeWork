package com.pb.shevchuk.hw11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class PhoneBook {
    private final List<Contact> contacts = new ArrayList<>();
    private final List<Contact> buffer = new ArrayList<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас!");

        while (phoneBook.isEmpty()) {
            System.out.println("У телефонній книзі поки немає контактів");
            System.out.println();

            System.out.println(
                "яку дію із контактами бажаєте виконати?\n" +
                    "\tadd\n" +
                    "\tload"
            );
            String command = "add";
            System.out.println(command);
            System.out.println();

            switch (command) {
                case "add":
                    phoneBook.add("taras", "3700", "29/08/1995", "ternopil");
                    phoneBook.add("petro", "7737", "29/06/2000", "ternopil");

                    break;

                case "load":
//                    load();
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

        String line = "edit";
        System.out.println(line);
        System.out.println();
        switch (line) {
            case "add":
//                phoneBook.add();
                break;

            case "load":
//                phoneBook.load();
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

    public boolean isEmpty() {
        return contacts.isEmpty();
    }

    public String readLine() throws IOException {
        return reader.readLine().trim().toLowerCase();
    }

    private void printBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            System.out.printf("%d - %s\n", (i + 1), buffer.get(i));
        }
    }

    public void add(String name, String phone, String date, String address) throws Exception {
        do {
            System.out.println("введіть, будь ласка, ваше ім'я");
            System.out.println(name);
            System.out.println();

        } while (name.trim().equals(""));

        List<String> phones = new ArrayList<>();
        System.out.println("введіть номер телефону");
//        String phone;

        do {
            System.out.println(phone);
            System.out.println();

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
                System.out.println("next");
                System.out.println();

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("введіть номер телефону");
                System.out.println();
            }
        } while (phones.isEmpty());
//        } while (!((phone = readLine()).equals("next") && !phones.isEmpty()));

        LocalDate birthDay = null;

        try {
            System.out.println("введіть дату народження (формат дд/мм/рррр)");
            birthDay = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
            System.out.println(birthDay.format(DateTimeFormatter.ofPattern("d/MM/yyyy")));
            System.out.println();

        } catch (DateTimeParseException e) {
            System.out.println("некоректний формат, дату не збережено");
        }

        System.out.println("введіть вашу адресу проживання");
        System.out.println(address);
        System.out.println();

        Contact contact = new Contact(name, birthDay, phones, address);
        contacts.add(contact);
        System.out.println("контакт додано у телефону книгу");
        System.out.println();
    }

    public void sort() {
        System.out.println(
                "введіть критерій для сортування:\n" +
                "\tname - за іменем\n" +
                "\tbirthDate - за датою народження"
        );
        String field = "name";
        System.out.println(field);
        System.out.println();

        if (field.equals("name")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.name.compareTo(o2.name);
                }
            });
        } else if (field.equals("birthDay")) {
            contacts.sort(new Comparator<Contact>() {
                @Override
                public int compare(Contact o1, Contact o2) {
                    return o1.birthDay.compareTo(o2.birthDay);
                }
            });
        } else {
            System.out.println("введено некоректний критерій");
            return;
        }

        buffer.clear();
        buffer.addAll(contacts);
        printBuffer();
    }

    public void search() {
        buffer.clear();

        System.out.println("введіть дані для пошуку контактів");
        String line = "ternopil";
        System.out.println(line);
        System.out.println();

        try {
            if (line.equals("")) {
                throw new IllegalArgumentException("дані не введені");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        Class<Contact> clazz = Contact.class;

        for (Contact contact : contacts) {
            List<String> values = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object prop;

                try {
                    prop = field.get(contact);

                } catch (IllegalAccessException e) {
                    continue;
                }

                if (prop instanceof String) {
                    values.add((String) prop);

                } else if (prop instanceof List<?>) {
                    values.addAll(contact.getPhones());

                } else if (prop instanceof LocalDate) {
                    values.add(contact.getBirthDay("d/MM/yyyy"));

                } else if (prop instanceof LocalDateTime) {
                    values.add(contact.getEdited("d/MM/yyyy о H:mm"));
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

    public void edit() {
        if (buffer.isEmpty()) {
            search();

            if (buffer.isEmpty()) return;
        }

        System.out.println("оберіть контакт для редагування");
        int index = 1;
        Contact contact;

        try {
            contact = buffer.get(index);
            System.out.println(contact);
            System.out.println();

            System.out.println(
                    "оберіть поле для редагування\n" +
                    String.join("\n\t", Arrays.asList("\tname", "birthDate", "phone", "address"))
            );
            String fieldName = "phone";
            System.out.println(fieldName);
            System.out.println();

            String newValue = "579";

            if (fieldName.equals("name")) {
                contact.setName(newValue);

            } else if (fieldName.equals("phone")) {
                System.out.println("який номер бажаєте редагувати?");

                for (int i = 0; i < contact.phones.size(); i++) {
                    System.out.printf("%d - %s\n", (i + 1), contact.phones.get(i));
                    System.out.println();
                }

                int i = 1;
                contact.setPhone(i, newValue);

            } else if (fieldName.equals("birthday")) {
                contact.setBirthDay(newValue);

            } else if (fieldName.equals("address")) {
                contact.setAddress(newValue);

            } else {
                throw new IllegalArgumentException("поле для редагування введено помилково");
            }

            System.out.println(newValue);
            System.out.println();

            contact.edited = LocalDateTime.now();
            System.out.println("дані редаговано");
            System.out.println(contact);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("введено помилковий порядковий номер");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

//    public void write() {
//
//    }

//    public void remove() {
//        if (buffer.isEmpty()) {
//            System.out.println("address, ternopil");
//            System.out.println();
//            this.search();
//        }
//
//        System.out.println("введіть номер контакту, який бажаєте видалити");
//        System.out.println();
//        int index = 3;
//
//        try {
//            contacts.remove(buffer.get(index - 1));
//            buffer.remove(index - 1);
//            System.out.println("контакт видалено");
//
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("введено помилковий порядковий номер");
//        }
//    }

    private class Contact {
        private String name;
        private LocalDate birthDay;
        private final List<String> phones;
        private String address;
        private LocalDateTime edited;

        public Contact(String name, LocalDate birthDay, List<String> phones, String address) {
            this.name = name;
            this.birthDay = birthDay;
            this.phones = phones;
            this.address = address;
            edited = LocalDateTime.now();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) throws IllegalArgumentException {
            if (name.equals("")) {
                throw new IllegalArgumentException("ім'я не введено");
            }

            this.name = name;
        }

        public LocalDate getBirthDay() {
            return birthDay;
        }

        public String getBirthDay(String pattern) {
            return (birthDay != null) ?
                    birthDay.format(DateTimeFormatter.ofPattern(pattern)) :
                    "none";
        }

        public void setBirthDay(String date) throws DateTimeParseException {
            this.birthDay = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        }

        private List<String> getPhones() {
            return phones;
        }

        private void addPhone(String phone) {
            int index = getPhones().size();

            setPhone(index, phone);
        }

        private void setPhone(int index, String phone) throws IllegalArgumentException {
            if (!phone.matches("\\d{3,12}")) {
                throw new IllegalArgumentException("введено некоректні дані. Номер має містити від 3 до 12 цифр");
            }

            for (Contact contact : contacts) {
                if (contact.phones.contains(phone)) {
                    throw new IllegalArgumentException("такий номер уже зареєстрований");
                }
            }

            phones.set(index, phone);
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

        public String getEdited(String pattern) {
            return (edited != null) ?
                    edited.format(DateTimeFormatter.ofPattern(pattern)) :
                    "none";
        }

        @Override
        public String toString() {
            return String.format(
                    "%s, номери телефонів: %s, дата народження %s, адреса - %s, редаговано - %s",
                    name,
                    phones,
                    getBirthDay("d/MM/yyyy"),
                    address,
                    getEdited("d/MM/yyyy о H:mm")
            );
        }
    }
}
