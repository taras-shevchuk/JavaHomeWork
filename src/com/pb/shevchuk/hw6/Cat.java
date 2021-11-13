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

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLives());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cat cat = (Cat) o;
        return Objects.equals(lives, cat.getLives());
    }

    @Override
    public String toString() {
        return "Кіт {\n" +
                   "\tїжа: " + getFood() + "\n" +
                   "\tмісцевість: " + getLocation() + "\n" +
                   "\tкількість життів: " + getLives() + "\n" +
               '}';
    }
}
