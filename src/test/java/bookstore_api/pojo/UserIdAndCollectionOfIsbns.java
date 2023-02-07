package bookstore_api.pojo;

import java.util.List;
import java.util.Objects;

public class UserIdAndCollectionOfIsbns {
    private String userId;
    private List<Isbn> collectionOfIsbns;

    public UserIdAndCollectionOfIsbns(String userId, List<Isbn> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }

    public String getUserId() {
        return userId;
    }

    public List<Isbn> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserIdAndCollectionOfIsbns other)) return false;
        return Objects.equals(userId, other.userId) && Objects.equals(collectionOfIsbns, other.collectionOfIsbns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, collectionOfIsbns);
    }
}
