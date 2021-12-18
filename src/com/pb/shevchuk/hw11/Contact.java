package com.pb.shevchuk.hw11;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Contact {
    private String name;
    @JsonSerialize
    private List<String> phones;
    private LocalDate birthday;
    private String address;
    private LocalDateTime edited;

    public Contact() { }

    public Contact(String name, List<String> phones, LocalDate birthday, String address) {
        this.name = name;
        this.phones = phones;
        this.birthday = birthday;
        this.address = address;
        edited = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate date) {
        this.birthday = date;
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

    public void setEdited(LocalDateTime dateTime) {
        this.edited = dateTime;
    }

    @Override
    public String toString() {
        return String.format(
                "%s, номери телефонів: %s, дата народження %s, адреса - %s, редаговано - %s",
                name,
                phones,
                getBirthday().format(DateTimeFormatter.ofPattern("d/MM/yyyy")),
                address,
                getEdited().format(DateTimeFormatter.ofPattern("d/MM/yyyy, H:mm"))
        );
    }
}
