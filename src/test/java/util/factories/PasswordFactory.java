package util.factories;

import com.github.javafaker.Faker;

import java.util.Random;

public class PasswordFactory {
    private static final String SPECIAL_CHARS = "!@#$%^&*";
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" + SPECIAL_CHARS;
    private static final Random rnd = new Random();

    public static String getRandomValidPassword(int len) {
        StringBuilder password = new StringBuilder(len);
        if (len < 4) throw new IllegalArgumentException("Password length must be 4+");
        for (int i = 4; i < len; i++) {
            password.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        }
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(10)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(10, 36)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(36, 62)));
        password.insert(rnd.nextInt(password.length()), CHARS.charAt(rnd.nextInt(62, CHARS.length())));

        return password.toString();
    }

    public static void validatePassword(String pw) {
        boolean l, u, s, n;
        l = u = s = n = false;
        char c;
        for (int i = 0; i < pw.length(); i++) {
            c = pw.charAt(i);
            if (!u && Character.isUpperCase(c)) {
                u = true;
            } else if (!l && Character.isLowerCase(c)) {
                l = true;
            } else if (!n && Character.isDigit(c)) {
                n = true;
            } else if (!s && SPECIAL_CHARS.indexOf(c) != -1) {
                s = true;
            }
        }

        if (!l || !u || !s || !n) {
            throw new IllegalArgumentException("Invalid password format: " + pw);
        }
    }
}
