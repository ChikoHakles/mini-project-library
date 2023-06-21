package id.co.indivara.library.services;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Reader;
import id.co.indivara.library.entities.Wishlist;
import id.co.indivara.library.entities.WishlistDTO;

import java.util.ArrayList;

public interface WishlistService {
    ArrayList<Wishlist> findAllWishlist();
    ArrayList<Wishlist> findAllWishlistByBook(Book book);
    ArrayList<Wishlist> findAllWishlistByReader(Reader reader);
    Wishlist findWishlistById(Integer id);
    Wishlist saveWishlist(WishlistDTO wishlistDTO);
    void deleteWishlist(Integer id);
}
