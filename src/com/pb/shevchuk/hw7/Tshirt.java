package com.pb.shevchuk.hw7;

public class Tshirt extends Clothes implements Clothes.ManClothes, Clothes.WomanClothes {

    public Tshirt(Size size, double price, String color) {
        super("футболка", size, price, color);
    }

    public void dressMan() {
        System.out.println("Чоловічий одяг:");
        dress();
    }

    public void dressWoman() {
        System.out.println("Жіночий одяг:");
        dress();
    }
}
