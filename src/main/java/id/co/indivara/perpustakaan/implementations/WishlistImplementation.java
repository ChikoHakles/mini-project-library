package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.entities.Reader;
import id.co.indivara.perpustakaan.entities.Wishlist;
import id.co.indivara.perpustakaan.exceptions.DataRelatedException;
import id.co.indivara.perpustakaan.repositories.WishlistRepository;
import id.co.indivara.perpustakaan.services.BookService;
import id.co.indivara.perpustakaan.services.ReaderService;
import id.co.indivara.perpustakaan.services.WishlistService;
import id.co.indivara.perpustakaan.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

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
        ArrayList<Wishlist> wishlists = wishlistRepository.findAllByBook(book);
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        return wishlists;
    }

    @Override
    public ArrayList<Wishlist> findAllWishlistByReader(Reader reader) {
        ArrayList<Wishlist> wishlists = wishlistRepository.findAllByReader(reader);
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        return wishlists;
    }

    @Override
    public Wishlist findByWishlistId(Integer id) {
        return wishlistRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Wishlist Found")
        );
    }

    @Transactional
    @Override
    public Wishlist saveWishlist(Wishlist wishlist) {
        if (wishlist == null) {
            throw new DataRelatedException("Must have a wishlist inputted");
        }
//        readerService.findReaderById(wishlist.getReader().getReaderId());
//        bookService.findBookById(wishlist.getBook().getBookId());
        Wishlist createdWishlist = new Wishlist();
        Utility.copyNonNullField(wishlist, createdWishlist);
        return wishlistRepository.save(createdWishlist);
    }

    @Override
    public Wishlist updateWishlist(Integer id, Wishlist wishlistUpdate) {
        if(wishlistUpdate == null) {
            throw new DataRelatedException("Must have a wishlist inputted");
        }
        Wishlist oldWishlist = findByWishlistId(id);
        Utility.copyNonNullField(wishlistUpdate, oldWishlist);
        return wishlistRepository.save(oldWishlist);
    }

    @Override
    public void deleteWishlist(Integer id) {
        findByWishlistId(id);
        wishlistRepository.deleteById(id);
    }
}
