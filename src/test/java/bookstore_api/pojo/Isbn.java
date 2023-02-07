package bookstore_api.pojo;

import java.util.Objects;

public class Isbn {
    private String isbn;

    public Isbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Isbn otherIsbn)) return false;
        return Objects.equals(isbn, otherIsbn.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
