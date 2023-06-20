package id.co.indivara.perpustakaan.repositories;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.entities.Reader;
import id.co.indivara.perpustakaan.entities.Wishlist;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {
    ArrayList<Wishlist> findAllByBook(Book book);
    ArrayList<Wishlist> findAllByReader(Reader reader);
}
