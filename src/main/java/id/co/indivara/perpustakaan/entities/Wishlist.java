package id.co.indivara.perpustakaan.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TRX_wishlist")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    @Column(name = "wishlist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishListId;

    @ManyToOne
    @Column(name = "book_id")
    private Book book;

    @ManyToOne
    @Column(name = "reader_id")
    private Reader reader;

    public Wishlist(Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
    }
}
