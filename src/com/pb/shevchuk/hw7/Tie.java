package com.pb.shevchuk.hw7;

public class Tie extends Clothes implements Clothes.ManClothes {

    public Tie(Size size, double price, String color) {
        super("краватка", size, price, color);
    }

    public void dressMan() {
        dress();
    }
}
