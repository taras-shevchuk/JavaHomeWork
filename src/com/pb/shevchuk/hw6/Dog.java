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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAlias());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Dog dog = (Dog) o;
        return Objects.equals(alias, dog.getAlias());
    }

    @Override
    public String toString() {
        return "Собака {\n" +
                   "\tкличка: " + getAlias() + "\n" +
                   "\tїжа: " + getFood() + "\n" +
                   "\tмісцевість: " + getLocation() + "\n" +
               '}';
    }
}
