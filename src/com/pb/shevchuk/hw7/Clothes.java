package com.pb.shevchuk.hw7;

public abstract class Clothes {
    private final String title;
    private Size size;
    private double price;
    private String color;

    public Clothes(String title, Size size, double price, String color) {
        this.title = title;
        this.size = size;
        this.price = price;
        this.color = color;
    }

    protected void dress() {
        System.out.printf(
                "%s, %s, %s, ціна %.2f грн.\n",
                title,
                color,
                size,
                price
        );
    }

    public String getTitle() {
        return title;
    }

    public Size getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public interface ManClothes {
        void dressMan();
    }

    public interface WomanClothes {
        void dressWoman();
    }

    public enum Size {
        XXS("дитячий", 32),
        XS("дорослий", 34),
        S("дорослий", 36),
        M("дорослий", 38),
        L("дорослий", 40);

        private final String description;
        private final int euroSize;

        Size(String description, int euroSize) {
            this.description = description;
            this.euroSize = euroSize;
        }

        public String getDescription() {
            return description;
        }

        public int getEuroSize() {
            return euroSize;
        }

        @Override
        public String toString() {
            return String.format("розмір %d - %s", euroSize, description);
        }
    }
}
