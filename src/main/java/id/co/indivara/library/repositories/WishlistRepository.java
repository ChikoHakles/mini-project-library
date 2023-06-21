package id.co.indivara.library.repositories;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    ArrayList<Wishlist> findAllByBook(Book book);
    ArrayList<Wishlist> findAllByReader(Reader reader);
}
