package com.pb.shevchuk.hw10;

import java.util.Arrays;

public class NumBox<T extends Number> {

    private final T[] array;
    private int currLength = 0;

    @SuppressWarnings("unchecked")
    public NumBox(int length) {

        array = (T[]) new Number[length];
    }

    public void add(T num) throws ArrayIndexOutOfBoundsException {

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

        if (array[0] == null) {
            return null;
        }

        T max = array[0];

        for (int i = 1; i < currLength; i++) {

            if (array[i].doubleValue() > max.doubleValue()) {
                max = array[i];
            }
        }

        return max;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
