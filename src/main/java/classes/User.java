package classes;

import java.util.Objects;

public class User {
    private final String userID;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;

    public User(String userID, String username, String password, String firstName, String lastName) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userID);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return false;
        if (!(o instanceof User u)) return false;
        return Objects.equals(this.userID, u.userID);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
