package id.co.indivara.library.entities;

import id.co.indivara.library.utils.TransactionType;
import id.co.indivara.library.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TRX_borrows")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {
    @Id
    @Column(name = "borrow_id")
    private UUID borrowId;

    @Column(name = "borrow_code")
    private String borrowCode;

    @Column(name = "book_id")
    private Book book;

    @Column(name = "reader_id")
    private Reader reader;

    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    @Column(name = "borrow_estimated_return_date")
    private LocalDate returnDate;

    public Borrow(Book book, Reader reader) {
        this.borrowId = UUID.randomUUID();
        this.borrowDate = LocalDateTime.now();
        this.returnDate = borrowDate.plusDays(book.getBookMaxDayBorrowed()).toLocalDate();
        this.borrowCode = Utility.generateCode(TransactionType.BORROW, this.borrowDate);
        this.book = book;
        this.reader = reader;
    }
}