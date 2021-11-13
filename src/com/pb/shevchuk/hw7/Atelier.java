package com.pb.shevchuk.hw7;

public class Atelier {
    public static void main(String[] args) {
        Clothes[] clothes = {
                new Tie(Clothes.Size.M, 399, "зелена"),
                new Skirt(Clothes.Size.S, 799, "сіра"),
                new Tshirt(Clothes.Size.XXS, 199, "жовта"),
                new Pants(Clothes.Size.XS, 599, "чорні")
        };

        dressMan(clothes);
        System.out.println();
        dressWoman(clothes);
    }

    static void dressMan(Clothes[] allClothes) {
        System.out.println("Чоловічий одяг:");

        for (Clothes clothes : allClothes) {
            if (clothes instanceof Clothes.ManClothes) {
                ((Clothes.ManClothes) clothes).dressMan();
            }
        }
    }

    static void dressWoman(Clothes[] allClothes) {
        System.out.println("Жіночий одяг:");

        for (Clothes clothes : allClothes) {
            if (clothes instanceof Clothes.WomanClothes) {
                ((Clothes.WomanClothes) clothes).dressWoman();
            }
        }
    }
}
