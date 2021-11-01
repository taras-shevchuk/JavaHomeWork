package com.pb.shevchuk.hw5;

public class Reader {
    private String name;
    private int ticketNumber;
    private String faculty;
    private String birthDate;
    private String phone;

    public Reader(String name, int ticketNumber, String faculty, String birthDate, String phone) {
        this.name = name;
        this.ticketNumber = ticketNumber;
        this.faculty = faculty;
        this.birthDate = birthDate;
        this.phone = phone;
    }

    public void takeBook(int amount) {
        System.out.println(name + " взяв книг - " + amount);
    }

    public void takeBook(String... titles) {
        System.out.println(name + " взяв наступні книги: " + String.join(", ", titles));
    }

    public void takeBook(Book... books) {
        System.out.println(name + " взяв наступні книги:");

        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void returnBook(int amount) {
        System.out.println(name + " повернув книг - " + amount);
    }

    @Override
    public String toString() {
        return String.format(
                "Читач: %s\n" +
                "Номер квитка: %d\n" +
                "Факультет: %s\n" +
                "Дата народження: %s\n" +
                "Телефон: %s;\n",
                getName(),
                getTicketNumber(),
                getFaculty(),
                getBirthDate(),
                getPhone()
        );
    }

    public void returnBook(String... titles) {
        System.out.println(name + " повернув наступні книги: " + String.join(", ", titles));
    }

    public void returnBook(Book... books) {
        System.out.println(name + " повернув наступні книги:");

        for (Book book : books) {
            System.out.println(book);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
