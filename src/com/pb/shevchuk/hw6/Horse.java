package com.pb.shevchuk.hw6;

import java.util.Objects;

public class Horse extends Animal {
    private int speed;

    public Horse(String food, String location, int speed) {
        super(food, location);
        this.speed = speed;
    }

    @Override
    public void makeNoise() {
        System.out.println("Кінь ірже");
    }

    @Override
    public void eat() {
        System.out.println("Кінь їсть " + getFood());
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSpeed());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Horse horse = (Horse) o;
        return Objects.equals(speed, horse.getSpeed());
    }

    @Override
    public String toString() {
        return "Кінь {\n" +
                   "\tїжа: " + getFood() + "\n" +
                   "\tмісцевість: " + getLocation() + "\n" +
                   "\tшвидкість: " + getSpeed() + "\n" +
               '}';
    }
}
