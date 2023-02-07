package bookstore_api.pojo;

import java.util.Objects;

public class UserIdAndOneIsbn {
    private String userId;
    private Isbn isbn;

    public UserIdAndOneIsbn(String userId, Isbn isbn) {
        this.userId = userId;
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserIdAndOneIsbn other)) return false;
        return Objects.equals(userId, other.userId) && Objects.equals(isbn, other.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, isbn);
    }
}
