package com.pb.shevchuk.hw7;

public class Pants extends Clothes implements Clothes.ManClothes, Clothes.WomanClothes {

    public Pants(Size size, double price, String color) {
        super("штани", size, price, color);
    }

    public void dressMan() {
        dress();
    }

    public void dressWoman() {
        dress();
    }
}
