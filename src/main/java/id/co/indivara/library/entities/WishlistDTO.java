package id.co.indivara.library.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {
    private Integer wishlistId;
    private Integer bookId;
    private Integer readerId;

    public WishlistDTO(Integer bookId, Integer readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
    }
}
