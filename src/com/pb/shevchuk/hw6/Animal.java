package com.pb.shevchuk.hw6;

public class Animal {
    private String food;
    private String location;

    public Animal(String food, String location) {
        this.food = food;
        this.location = location;
    }

    public void makeNoise() {
        System.out.println("Тварина видає звуки");
    }

    public void eat() {
        System.out.println("Тварина їсть");
    }

    public void sleep() {
        System.out.println("Тварина спить");
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
