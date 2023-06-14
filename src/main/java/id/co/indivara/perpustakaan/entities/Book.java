package id.co.indivara.perpustakaan.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.AllArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(name = "book_title")
    @NonNull
    private String bookTitle;

    @Column(name = "book_author")
    @NonNull
    private String bookAuthor;

    @Column(name = "book_publisher")
    @NonNull
    private String bookPublisher;

    @Column(name = "book_isbn")
    private String bookIsbn;

    @Column(name = "book_description")
    @NonNull
    private String bookDescription;

    @Column(name = "book_pages")
    @NonNull
    private Integer bookPages;

    @Column(name = "book_copy")
    @NonNull
    private Integer bookCopy;

    @Column(name = "book_ready")
    private Integer bookReady;

    @Column(name = "book_unreturned")
    private Integer bookUnreturned;

    @Column(name = "book_max_day_borrowed")
    private Integer bookMaxDayBorrowed;

    @Column(name = "book_wished_by")
    private Integer bookWishedBy;

    @Column(name = "book_number_of_reading")
    private Integer bookNumberOfReading;

    public Book() {}

    public Book(@NonNull String bookTitle, @NonNull String bookAuthor, @NonNull String bookPublisher, @NonNull String bookDescription, @NonNull Integer bookPages, @NonNull Integer bookCopy) {
        this.bookTitle = bookTitle;

        this.bookAuthor = bookAuthor;

        this.bookPublisher = bookPublisher;

        this.bookIsbn = "";

        this.bookDescription = bookDescription;

        this.bookPages = bookPages;

        this.bookCopy = bookCopy;

        this.bookReady = bookCopy;

        this.bookUnreturned = 0;

        this.bookMaxDayBorrowed = countMaxDayBorrowed(bookPages);

        this.bookWishedBy = 0;

        this.bookNumberOfReading = 0;
    }

    private Integer countMaxDayBorrowed(Integer bookPages) {
        Integer maxDayBorrowed = 1;
        while(bookPages > 100) {
            maxDayBorrowed++;
            bookPages -= 100;
        }
        return maxDayBorrowed;
    }
}
