package com.pb.shevchuk.hw11;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class PhoneBook {

    private static final Set<Contact> contacts = new HashSet<>();

    public static void main(String[] args) throws IOException {

        PhoneBook phoneBook = new PhoneBook();

        try (
                InputStreamReader inStream = new InputStreamReader(System.in);
                BufferedReader reader = new BufferedReader(inStream)
        ) {

            while (true) {

//                switch (reader.readLine().toLowerCase().trim()) {

//                    case "a":

                phoneBook.addContact(reader);
//                        break;
//                }

                System.out.println(contacts);
            }
        }
    }

    private void addContact(BufferedReader reader) throws IOException {

        Contact contact = new Contact(reader.readLine());
        contacts.add(contact);
    }

    public class Contact {

        private String name;
        private LocalDate birthDate;
        private String phone;
        private String address;
        private LocalDate edited;

        public Contact(String name) {

            this.name = name;
        }

        public Contact(String name, LocalDate birthDate, String phone, String address) {

            this.name = name;
            this.birthDate = birthDate;
            this.phone = phone;
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

        public String getPhone() {

            return phone;
        }

        public void setPhone(String phone) {

            this.phone = phone;
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
                    '}';
        }
    }
}
