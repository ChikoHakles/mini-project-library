package id.co.indivara.perpustakaan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "MST_readers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reader {
    @Id
    @Column(name = "reader_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer readerId;

    @Column(name = "reader_name")
    @NotBlank(message = "Reader Name must been filled")
    private String readerName;

    @Column(name = "reader_address")
    @NotBlank(message = "Reader Address must been filled")
    private String readerAddress;

    @Column(name = "reader_email")
    private String readerEmail;

    @Column(name = "reader_phone")
    private String readerPhone;

    @Transient
    private List<Book> readerWishlist;

    public Reader(String readerName, String readerAddress) {
        this.readerName = readerName;
        this.readerAddress = readerAddress;
    }
}