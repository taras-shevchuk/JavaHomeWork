package com.pb.shevchuk.hw10;

public class Main {

    public static void main(String[] args) {

        NumBox<Integer> nbI = new NumBox<>(5);

        nbI.add(5);
        nbI.add(1);
        nbI.add(7);
        nbI.add(9);

        System.out.println(nbI.get(2));
        System.out.println(nbI.length());
        System.out.println(nbI.average());
        System.out.println(nbI.sum());
        System.out.println(nbI.max());

        System.out.println();

        NumBox<Float> nbF = new NumBox<>(5);

        nbF.add(5f);
        nbF.add(1f);
        nbF.add(7.4562f);
        nbF.add(0f);

        System.out.println(nbF.get(2));
        System.out.println(nbF.length());
        System.out.println(nbF.average());
        System.out.println(nbF.sum());
        System.out.println(nbF.max());
    }
}
