package com.pb.shevchuk.hw11;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PhoneBook {
    private static final List<Contact> contacts = new ArrayList<>();
    private static final List<Contact> buffer = new ArrayList<>();
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Вітаємо вас! У телефонній книзі поки немає контактів");
        System.out.println();

//        while (contacts.isEmpty()) {
//        }

        while (true) {
            switch (getAction(Action.ADD, Action.SEARCH)) {
                case ADD:
                    phoneBook.add("taras", "3700", "29/08/1995", "ternopil");
                    System.out.println(contacts);

                    phoneBook.add("petro", "7737   911", "29/06/2000", "ternopil");
                    System.out.println(contacts);

                    break;

                case SEARCH:
                    System.out.println("Введіть будь ласка, критерій та дані за якими виконати пошук");
                    phoneBook.search("name", "taras");
                    break;
            }
        }
    }

    private static Action getAction(Action... actions) {
        while (true) {
            for (Action action : actions) {
                if (action.getDescription() != null) {
                    System.out.println(action);
                }
            }

            String line = "";

            try {
                line = reader.readLine().toLowerCase().trim();
                System.out.println();

                for (Action action : actions) {
                    if (line.equals(action.getName())) {
                        return action;
                    }
                }

                throw new IllegalArgumentException("введено некоректне значення");

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void add(String name, String phones, String birth, String address) {
        HashMap<String, String> data = new HashMap<>();
        data.put("name", "");
        data.put("birthDate", "");
        data.put("address", "");

        List<String> allPhones = new ArrayList<>();
        LocalDate birthDate = null;

        do {
            try {
                if (data.get("name").equals("")) {
                    System.out.println("Введіть, будь ласка, ваше ім'я");
                    data.put("name", name);
                    System.out.println(name);
                    System.out.println();
                }

                do {
                    System.out.println("введіть номери телефонів через пробіл");

                    if (phones.equals("")) {
                        throw new IllegalArgumentException("не введено жодного номеру телефону");
                    }

                    String[] phonesArray = phones.trim().split(" +");

                    for (String phone : phonesArray) {
                        if (!phone.matches("(\\+?)(\\d{3,15})")) {
                            System.out.printf("Номер %s має некоректний формат", phone);
                            continue;
                        }

                        allPhones.add(phone);
                        System.out.println(phone);
                        System.out.println();
                    }

                    System.out.println("поточні номери нового контакту:");
                    System.out.println(allPhones);
                    System.out.println();

                    System.out.println("бажаєте ввести ще один номер? (yes / no)");
                } while (getAction(Action.YES, Action.NO).equals(Action.NO));

                if (data.get("birthDate").equals("")) {
                    System.out.println("Введіть, будь ласка, дату народження у форматі дд/мм/рррр");
                    data.put("birthDate", birth);
                }

                if (data.get("address").equals("")) {
                    System.out.println("Введіть, будь ласка, вашу адресу");
                    data.put("address", address);
                }

                if (!data.get("birthDate").equals("")) {
                    birthDate = LocalDate.parse(
                            data.get("birthDate"),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    );
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (data.get("name").equals("") || data.get("phones").contains(""));

        Contact contact = new Contact(
                data.get("name"),
                birthDate,
                allPhones,
                data.get("address")
        );
        contacts.add(contact);
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
                }

            } else if (fieldName.equalsIgnoreCase("phones")) {
                if (((ArrayList<?>) field.get(contact)).contains(value)) {
                    buffer.add(contact);
                }

            } else if (fieldName.equalsIgnoreCase("birthDate")) {
                if (String.valueOf(contact.birthDate).equals(value)) {
                    buffer.add(contact);
                }
            }

            System.out.println(contact);
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

    private enum Action {
        YES("yes"),
        NO("no"),
        EXIT("exit"),

        ADD("add", "додати контакт"),
        REMOVE("remove", "видалити контакт"),
        SEARCH("search", "пошук контактів"),
        PRINT("print"),
        EDIT("etid"),
        WRITE("write"),
        LOAD("load", "завантажити дані зі файлу");

        private final String name;
        private String description;

        Action(String name) {
            this.name = name;
        }

        Action(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            if (description == null) {
                return getName();
            }

            return String.format("%s - %s" ,getName(), getDescription());
        }

    }
}
