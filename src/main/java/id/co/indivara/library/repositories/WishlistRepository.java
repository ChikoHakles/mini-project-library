package id.co.indivara.library.repositories;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    ArrayList<Wishlist> findAllByBook(Book book);
    ArrayList<Wishlist> findAllByReader(Reader reader);
}
