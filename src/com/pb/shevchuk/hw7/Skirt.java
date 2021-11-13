package com.pb.shevchuk.hw7;

public class Skirt extends Clothes implements Clothes.WomanClothes {

    public Skirt(Size size, double price, String color) {
        super("спідниця", size, price, color);
    }

    public void dressWoman() {
        dress();
    }
}
