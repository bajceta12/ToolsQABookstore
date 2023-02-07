package bookstore_api.pojo;

import java.util.Objects;

public class Book {
// "isbn": "9781449325862",
//            "title": "Git Pocket Guide",
//            "subTitle": "A Working Introduction",
//            "author": "Richard E. Silverman",
//            "publish_date": "2020-06-04T08:48:39.000Z",
//            "publisher": "O'Reilly Media",
//            "pages": 234,
//            "description": "This pocket guide is the perfect on-the-job companion to Git, the distributed version control system. It provides a compact, readable introduction to Git for new users, as well as a reference to common commands and procedures for those of you with Git exp",
//            "website": "http://chimera.labs.oreilly.com/books/1230000000561/index.html"
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String publish_date;
    private String publisher;
    private int pages;
    private String description;
    private String website;

    public Book() {

    }

    public Book(String isbn, String title, String subTitle, String author, String publish_date, String publisher, int pages, String description, String website) {
        this.isbn = isbn;
        this.title = title;
        this.subTitle = subTitle;
        this.author = author;
        this.publish_date = publish_date;
        this.publisher = publisher;
        this.pages = pages;
        this.description = description;
        this.website = website;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPages() {
        return pages;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book other)) return false;
        return pages == other.pages &&
                Objects.equals(isbn, other.isbn) &&
                Objects.equals(title, other.title) &&
                Objects.equals(subTitle, other.subTitle) &&
                Objects.equals(author, other.author) &&
                Objects.equals(publish_date, other.publish_date) &&
                Objects.equals(publisher, other.publisher) &&
                Objects.equals(description, other.description) &&
                Objects.equals(website, other.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, subTitle, author, publish_date, publisher, pages, description, website);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", author='" + author + '\'' +
                ", publish_date='" + publish_date + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pages=" + pages +
                ", description='" + description + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
