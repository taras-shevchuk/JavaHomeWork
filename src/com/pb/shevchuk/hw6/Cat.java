package com.pb.shevchuk.hw6;

import java.util.Objects;

public class Cat extends Animal {
    private int lives;

    public Cat(String food, String location, int lives) {
        super(food, location);
        this.lives = lives;
    }

    @Override
    public void makeNoise() {
        System.out.println("Кіт нявкає");
    }

    @Override
    public void eat() {
        System.out.println("Кіт їсть " + getFood());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFood(), getLocation(), getLives());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(getLives(), cat.getLives())
                && Objects.equals(getFood(), cat.getFood())
                && Objects.equals(getLocation(), cat.getLocation());
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}