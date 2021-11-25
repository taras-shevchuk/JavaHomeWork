package com.pb.shevchuk.hw10;

public class NumBox<T extends Number> {

    private final T[] array;
    private int currLength = 0;

    @SuppressWarnings("unchecked")
    public NumBox(int length) {

        array = (T[]) new Number[length];
    }

    public void add(T num) {

        if (currLength == array.length) {
            throw new ArrayIndexOutOfBoundsException("Об'єкт заповненний");
        }

        array[currLength] = num;
        currLength++;
    }

    public T get(int index) {

        return array[index];
    }

    public int length() {

        return currLength;
    }

    public double average() {

        return sum() / currLength;
    }

    public double sum() {

        double sum = 0;

        for (int i = 0; i < currLength; i++) {

            sum += array[i].doubleValue();
        }

        return sum;
    }

    public T max() {

        T max = null;

        for (int i = 1; i < currLength; i++) {

            T prev = array[i - 1];
            T curr = array[i];

            max = (prev.doubleValue() < curr.doubleValue()) ? curr : prev;
        }

        return max;
    }
}
