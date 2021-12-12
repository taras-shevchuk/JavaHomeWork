package com.pb.shevchuk.hw11;

import java.io.*;
import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    private static final List<Contact> buffer = new ArrayList<>();
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
            System.out.println(
                    "оберіть дію:\n" +
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

            switch ("print") {
                case "print":
                    System.out.println();
                    phoneBook.add("taras", "3700", "1995-08-29", "ternopil");
                    phoneBook.add("petro", "7737   911", "1990-08-29", "ternopil");
                    System.out.println(contacts);
                    System.out.println();

                    phoneBook.print();
                    break;

                case "remove":
                    phoneBook.remove();
                    break;
            }
//        }
    }

    public static void printBuffer() {
        for (int i = 0; i < buffer.size(); i++) {
            System.out.println((i + 1) + " - " + buffer.get(i));
        }
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

    public void search(String fieldName, String value) {
        System.out.println(
                "оберіть критерій пошуку:\n" +
                        "name - за іменем\n" +
                        "за номером телефону\n" +
                        "birthDate - за датою народження\n" +
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

                } else if (prop instanceof ArrayList<?>) {
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

    public void remove() {
        if (buffer.isEmpty()) {
            System.out.println("address, ternopil");
            System.out.println();
            this.search("address", "ternopil");
        }

        System.out.println("введіть номер контакту, який бажаєте видалити");
        System.out.println();
        int index = 1;

        contacts.remove(buffer.get(index - 1));
        buffer.remove(index - 1);
        System.out.println("контакт видалено");
    }

    public void print() {
        String field = "birthDate";

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
