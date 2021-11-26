package com.pb.shevchuk.hw10;

public class Main {

    public static void main(String[] args) {

        {
            NumBox<Integer> integerBox = new NumBox<>(5);

            integerBox.add(5);
            integerBox.add(1);
            integerBox.add(7);
            integerBox.add(9);
            integerBox.add(-14);

            System.out.println(integerBox);
            System.out.println(integerBox.get(2));
            System.out.println(integerBox.length());
            System.out.println(integerBox.average());
            System.out.println(integerBox.sum());
            System.out.println(integerBox.max());
        }

        System.out.println();

        {
            NumBox<Float> floatBox = new NumBox<>(5);

            floatBox.add(5f);
            floatBox.add(1f);
            floatBox.add(7.4562f);
            floatBox.add(0f);
            floatBox.add(-.5f);

            System.out.println(floatBox);
            System.out.println(floatBox.get(2));
            System.out.println(floatBox.length());
            System.out.println(floatBox.average());
            System.out.println(floatBox.sum());
            System.out.println(floatBox.max());
        }
    }
}
