package com.pb.shevchuk.hw8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OnlineShop {
    static private int signInAttempts = 0;

    public static void main(String[] args) throws IOException {

        Auth form = new Auth();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            do {
                try {
                    System.out.println(
                            "Введіть, будь ласка, логін\n" +
                            "(від 5 до 20 символів - цифри та латинські літери"
                    );
                    String login = reader.readLine();
                    System.out.println();

                    if (form.getLogin() == null) {

                        System.out.println(
                                "Придумайте пароль\n" +
                                "від 5 символів - цифри, латинські літери, знак підкреслення \"_\""
                        );
                        String password = reader.readLine();
                        System.out.println();

                        System.out.println("Повторіть пароль для підтвердження");
                        String confirmPassword = reader.readLine();
                        System.out.println();

                        form.singUp(login, password, confirmPassword);
                        continue;
                    }

                    System.out.println("Введіть, будь ласка, ваш пароль, щоб увійти");

                    signInAttempts++;
                    form.signIn(login, reader.readLine());

                    signInAttempts = 0;
                    break;

                } catch(Auth.WrongLoginException | Auth.WrongPasswordException exception) {

                    System.out.print(exception.getMessage());

                    if (signInAttempts == 3) {

                        System.out.print("Акаунт заблоковано");
                    }

                    System.out.println();
                }
            } while (signInAttempts < 3);
        }
    }
}
