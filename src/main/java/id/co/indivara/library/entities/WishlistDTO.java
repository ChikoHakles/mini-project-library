package id.co.indivara.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDTO {
    private Integer wishlistId;
    private Integer bookId;
    private Integer readerId;
}
