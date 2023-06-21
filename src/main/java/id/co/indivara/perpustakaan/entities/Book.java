package id.co.indivara.perpustakaan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "MST_books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    @Column(name = "book_title")
    @NotBlank(message = "Book Title must been filled")
    private String bookTitle;

    @Column(name = "book_author")
    @NotBlank(message = "Book Author must been filled")
    private String bookAuthor;

    @Column(name = "book_publisher")
    @NotBlank(message = "Book Publisher must been filled")
    private String bookPublisher;

    @Column(name = "book_isbn")
    @Size(min = 17, max = 17, message = "ISBN length must be 17 character (XXX-XXX-XXXX-XX-X)")
    private String bookIsbn;

    @Column(name = "book_description")
    @NotBlank(message = "Book Description must been filled")
    private String bookDescription;

    @Column(name = "book_pages")
    @NotNull(message = "Book Pages must been filled")
    @Min(value = 1, message = "Book Pages cannot be 0 or negative")
    private Integer bookPages;

    @Column(name = "book_copy")
    @NotNull(message = "Book Copy must been filled")
    @Min(value = 1, message = "Book Copy cannot be 0 or negative")
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

//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    @NotBlank(message = "Book Category must been filled")
//    private String categoryId;

    public Book(@NonNull String bookTitle, @NonNull String bookAuthor, @NonNull String bookPublisher, @NonNull String bookDescription, @NonNull Integer bookPages, @NonNull Integer bookCopy) {
        this.bookTitle = bookTitle;

        this.bookAuthor = bookAuthor;

        this.bookPublisher = bookPublisher;

        this.bookIsbn = null;

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
