package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Wishlist;
import id.co.indivara.library.entities.WishlistDTO;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.WishlistRepository;
import id.co.indivara.library.services.BookService;
import id.co.indivara.library.services.ReaderService;
import id.co.indivara.library.services.WishlistService;
import id.co.indivara.library.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class WishlistImplementation implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @Override
    public ArrayList<Wishlist> findAllWishlist() {
        ArrayList<Wishlist> wishlists = new ArrayList<>((Collection<Wishlist>) wishlistRepository.findAll());
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        return wishlists;
    }

    @Override
    public ArrayList<Wishlist> findAllWishlistByBook(Book book) {
        bookService.findBookById(book.getBookId());
        return wishlistRepository.findAllByBook(book);
    }

    @Override
    public ArrayList<Wishlist> findAllWishlistByReader(Reader reader) {
        readerService.findReaderById(reader.getReaderId());
        return wishlistRepository.findAllByReader(reader);
    }

    @Override
    public Wishlist findWishlistById(Integer id) {
        return wishlistRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Wishlist Found")
        );
    }

    @Transactional
    @Override
    public Wishlist saveWishlist(WishlistDTO wishlistDTO) {
        if (wishlistDTO == null) {
            throw new DataRelatedException("Must have a wishlist inputted");
        }
        Reader reader = readerService.findReaderById(wishlistDTO.getReaderId());
        Book book = bookService.findBookById(wishlistDTO.getBookId());
        Wishlist createdWishlist = new Wishlist(book, reader);
        return wishlistRepository.save(createdWishlist);
    }

    @Override
    public void deleteWishlist(Integer id) {
        findWishlistById(id);
        wishlistRepository.deleteById(id);
    }
}
