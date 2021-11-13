package com.pb.shevchuk.hw6;

import java.util.Objects;

public class Dog extends Animal {
    private String alias;

    public Dog(String food, String location, String alias) {
        super(food, location);
        this.alias = alias;
    }

    @Override
    public void makeNoise() {
        System.out.println("Собака гавкає");
    }

    @Override
    public void eat() {
        System.out.println("Собака їсть " + getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFood(), getLocation(), getAlias());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;

        Dog dog = (Dog) o;

        return Objects.equals(getAlias(), dog.getAlias())
            && Objects.equals(getFood(), dog.getFood())
            && Objects.equals(getLocation(), dog.getLocation());
    }

    @Override
    public String toString() {
        return "Собака {\n" +
                   "\tкличка: " + getAlias() + "\n" +
                   "\tїжа: " + getFood() + "\n" +
                   "\tмісцевість: " + getLocation() + "\n" +
               '}';
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
