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

            demo(integerBox);
        }

        System.out.println();

        {
            NumBox<Float> floatBox = new NumBox<>(5);

            floatBox.add(5f);
            floatBox.add(1f);
            floatBox.add(7.4562f);
            floatBox.add(0f);
            floatBox.add(-.5f);

            demo(floatBox);
        }
    }

    public static void demo(NumBox<?> numBox) {

        System.out.println(numBox);

        int i = 2;
        System.out.printf("елемент № %d = " + numBox.get(i) + "\n", i);

        System.out.println("довжина масиву = " + numBox.length());
        System.out.println("середнє значення = " + numBox.average());
        System.out.println("сума усіх елементів = " + numBox.sum());
        System.out.println("максимальне знаяення = " + numBox.max());
    }
}
