package util.factories;

import com.github.javafaker.Faker;

import java.util.Random;

public class PasswordFactory {
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@#$%^&+=";
    private static final Random rnd = new Random();

    public static String getRandomValidPassword(int len) {
        StringBuilder password = new StringBuilder(len);
        for (int i = 4; i < len; i++) {
            password.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        }
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(10)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(10, 36)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(36, 62)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(62, 70)));

        return password.toString();
    }
}
