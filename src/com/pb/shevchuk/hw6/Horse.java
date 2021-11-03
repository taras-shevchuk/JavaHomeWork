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

    @Override
    public int hashCode() {
        return Objects.hash(getFood(), getLocation(), getSpeed());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Objects.equals(getSpeed(), horse.getSpeed())
                && Objects.equals(getFood(), horse.getFood())
                && Objects.equals(getLocation(), horse.getLocation());
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
