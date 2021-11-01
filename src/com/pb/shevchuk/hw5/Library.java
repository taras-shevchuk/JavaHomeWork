package com.pb.shevchuk.hw5;

public class Library {
    public static void main(String[] args) {
        Book[] books = {
                new Book("Пригоди", "Іванов І. І.", 2000),
                new Book("Словник", "Сидоров А. В.", 1980),
                new Book("Енциклопедія", "Гусєв К. В.", 2010),
        };

        Reader[] readers = {
                new Reader("Петров В. В.", 1, "Фінанси", "12/7/1993", "+380678978996"),
                new Reader("Іванов І. І.", 13, "Кібернетика", "3/4/1991", "+380968873251"),
                new Reader("Гусєв К. В.", 175, "Іноземні мови", "29/12/1994", "+380735672411"),
        };

        System.out.println("Перелік книг:");

        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("\nСписок читачів:");

        for (Reader reader : readers) {
            System.out.println(reader);
        }

        System.out.println();

        readers[0].takeBook(3);
        readers[0].returnBook(3);

        System.out.println();

        readers[1].takeBook(books[0].getTitle(), books[1].getTitle(), books[2].getTitle());
        readers[1].returnBook(books[0].getTitle(), books[1].getTitle(), books[2].getTitle());

        System.out.println();

        readers[2].takeBook(books);
        System.out.println();
        readers[2].returnBook(books);
    }
}
