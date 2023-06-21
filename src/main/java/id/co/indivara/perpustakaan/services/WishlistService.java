package id.co.indivara.perpustakaan.services;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.entities.Reader;
import id.co.indivara.perpustakaan.entities.Wishlist;
import id.co.indivara.perpustakaan.entities.WishlistDTO;

import java.util.ArrayList;

public interface WishlistService {
    ArrayList<Wishlist> findAllWishlist();
    ArrayList<Wishlist> findAllWishlistByBook(Book book);
    ArrayList<Wishlist> findAllWishlistByReader(Reader reader);
    Wishlist findWishlistById(Integer id);
    Wishlist saveWishlist(WishlistDTO wishlistDTO);
    Wishlist updateWishlist(Integer id, Wishlist wishlistUpdate);
    void deleteWishlist(Integer id);
}
