package util.factories;

import bookstore_api.BookstoreAPI;
import com.github.javafaker.Faker;
import classes.User;

public class UserFactory {
    private static ThreadLocal<User> user = new ThreadLocal<>();
    private static final Faker faker = new Faker();

    public static User getExistingUser() {
        if (user.get() == null) {
            user.set(createNewUser());
        }
        return user.get();
    }

    public static User getExistingUser(String username, String password) {
        if (user.get() == null) {
            user.set(createNewUser(username, password));
        }
        return user.get();
    }

    public static void deleteUser(User u) {
        BookstoreAPI.deleteUser(u);
        user.remove();
    }

    private static User createNewUser() {
        return createNewUser(faker.rickAndMorty().character(), PasswordFactory.getRandomValidPassword(12));
    }

    private static User createNewUser(String username, String password) {
        if (username.isEmpty()) throw new IllegalArgumentException("Username must not be empty");
        PasswordFactory.validatePassword(password);
        String id = BookstoreAPI.createUserAndGetID(username, password);
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        return new User(id, username, password, firstname, lastname);
    }
}
