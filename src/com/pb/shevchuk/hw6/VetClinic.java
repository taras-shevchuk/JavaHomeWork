package com.pb.shevchuk.hw6;

import java.lang.reflect.Constructor;

public class VetClinic {
    public static void main(String[] args) throws Exception {
        Animal[] animals = {
                new Animal("трава", "увесь світ"),
                new Dog("м'ясо", "буда", "дикий"),
                new Cat("миші", "будинок", 9),
                new Horse("сіно", "стайня", 45)
        };

        Class<?> classVeterinarian = Class.forName("com.pb.shevchuk.hw6.Veterinarian");
        Constructor<?> constructor = classVeterinarian.getConstructor();

        Object o = constructor.newInstance();
        Veterinarian iHurt;

        if (o instanceof Veterinarian) {
            iHurt = (Veterinarian) o;

            for (Animal animal : animals) {
                iHurt.treatAnimal(animal);
            }
        }
    }
}
