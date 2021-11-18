package com.pb.shevchuk.hw8;

public class Auth {

    private String login;
    private String password;

    //------------------- methods -----------------------

    public void singUp(
            String login,
            String password,
            String confirmPassword

    ) throws WrongLoginException, WrongPasswordException {

        StringBuilder message = new StringBuilder();
        String invalidSymbols = "(.*)([^0-9a-zA-Z]+)(.*)";

        if (!login.matches("[0-9a-zA-Z]{5,20}")) {

            if (login.length() < 5 || login.length() > 20) {

                message.append("Логін повинен містити від 5 до 20 символів\n");
                message.append("Поточна кількість - ");
                message.append(login.length());
                message.append("\n");
            }

            if (login.matches(invalidSymbols)) {

                message.append("Логін повинен містити лише латинські літери та цифри\n");
            }

            throw new WrongLoginException(message.toString());
        }

        if (
                !(password.matches("[0-9a-zA-Z_]{5,}")
                        && password.equals(confirmPassword))
        ) {
            if (password.length() < 5) {

                message.append("Пароль повинен містити більше 5 символів\n");
                message.append("Поточна кількість - перший пароль = ");
                message.append(password.length());
                message.append(", другий = ");
                message.append(confirmPassword.length());
                message.append("\n");
            }

            if (password.matches(invalidSymbols) || confirmPassword.matches(invalidSymbols)) {

                message.append("Пароль повинен містити лише латинські літери, цифри або знак підкреслення \"_\"\n");
            }

            if (!password.equals(confirmPassword)) {

                message.append("Введені паролі відрізняються\n");
            }

            throw new WrongPasswordException(message.toString());
        }

        this.login = login;
        this.password = password;

        System.out.println("Дані збережено");
    }

    public void signIn(String login, String password) throws WrongLoginException {

        System.out.println();

        if (
                !(this.login.equals(login)
                        && this.password.equals(password))
        ) {

            throw new WrongLoginException("На жаль, у логіні або паролі допущена помилка:(\n");
        }

        System.out.println("Вітаємо " + login);
    }

    public String getLogin() {
        return login;
    }

    //------------------- Exceptions -----------------------

    public static class WrongLoginException extends Exception {

        public WrongLoginException() {
            super();
        }

        public WrongLoginException(String message) {
            super(message);
        }
    }

    public static class WrongPasswordException extends Exception {

        public WrongPasswordException() {
            super();
        }

        public WrongPasswordException(String message) {
            super(message);
        }
    }
}
