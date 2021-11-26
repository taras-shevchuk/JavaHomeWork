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
            System.out.printf("елемент № %d = %d\n", 2, integerBox.get(2));
            System.out.println("довжина масиву = " + integerBox.length());
            System.out.println("середнє значення = " + integerBox.average());
            System.out.println("сума усіх елементів = " + (int) integerBox.sum());
            System.out.println("максимальне знаяення = " + integerBox.max());
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
            System.out.printf("елемент № %d = %.4f\n", 3, floatBox.get(3));
            System.out.println("довжина масиву = " + floatBox.length());
            System.out.printf("середнє значення = %.4f\n", floatBox.average());
            System.out.printf("сума усіх елементів = %.4f\n", floatBox.sum());
            System.out.printf("максимальне знаяення = %.4f\n", floatBox.max());
        }
    }
}
