package com.pb.shevchuk.hw7;

public class Tshirt extends Clothes implements Clothes.ManClothes, Clothes.WomanClothes {

    public Tshirt(Size size, double price, String color) {
        super("футболка", size, price, color);
    }

    public void dressMan() {
        dress();
    }

    public void dressWoman() {
        dress();
    }
}
