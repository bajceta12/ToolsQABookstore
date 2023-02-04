package util.factories;

import bookstore_api.BookstoreAPI;
import com.github.javafaker.Faker;
import classes.User;
import java.util.HashSet;

public class UserFactory {
    private static final HashSet<User> allUsers = new HashSet<>();
    private static final Faker faker = new Faker();

    public static User createNewUser() {
        String username = faker.rickAndMorty().character();
        String password = PasswordFactory.getRandomValidPassword(8 + (int) (Math.random() * 8));
        String id = BookstoreAPI.createUserAndGetID(username, password);
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        return new User(id, username, password, firstname, lastname);
    }

}
