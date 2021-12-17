package com.pb.shevchuk.hw11;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Contact {
    private String name;
    @JsonSerialize
    private List<String> phones;
    private LocalDate birthDay;
    private String address;
    private LocalDateTime edited;

    public Contact() {

    }

    public Contact(String name, List<String> phones, LocalDate birthDay, String address) {
        this.name = name;
        this.phones = phones;
        this.birthDay = birthDay;
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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getBirthDay(String pattern) {
        return (birthDay != null) ?
                birthDay.format(DateTimeFormatter.ofPattern(pattern)) :
                "none";
    }

    public void setBirthDay(String date) throws DateTimeParseException {
        LocalDate newDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("d/MM/yyyy"));
        setBirthDay(newDate);
    }

    public void setBirthDay(LocalDate date) {
        this.birthDay = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (!address.trim().equals("")) ? address : "none";
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
                getEdited("d/MM/yyyy, H:mm")
        );
    }
}
