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
        //membuat array list yang berisi semua wishlist
        ArrayList<Wishlist> wishlists = new ArrayList<>((Collection<Wishlist>) wishlistRepository.findAll());
        //jika array list tersebut kosong, throw DataRelatedException
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        //output array list tersebut
        return wishlists;
    }

    @Override
    public ArrayList<Wishlist> findAllWishlistByBook(Book book) {
        //cari buku, bookService.findBookById() akan melempar DataRelatedException jika tidak ada buku dengan id dimaksud
        bookService.findBookById(book.getBookId());
        //buat array list yang menampung semua array list dari sebuah buku
        ArrayList<Wishlist> wishlists= wishlistRepository.findAllByBook(book);
        //jika array list tersebut kosong, throw DataRelatedException
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        //output array list tersebut
        return wishlists;
    }

    @Override
    public ArrayList<Wishlist> findAllWishlistByReader(Reader reader) {
        //cari pembaca, readerService.findReaderById() akan melempar DataRelatedException jika tidak ada pembaca dengan id dimaksud
        readerService.findReaderById(reader.getReaderId());
        //buat array list yang menampung semua array list dari seorang pembaca
        ArrayList<Wishlist> wishlists= wishlistRepository.findAllByReader(reader);
        //jika array list tersebut kosong, throw DataRelatedException
        if (wishlists.isEmpty()) {
            throw new DataRelatedException("No Wishlist Found");
        }
        //output array list tersebut
        return wishlists;
    }

    @Override
    public Wishlist findWishlistById(Integer id) {
        //cari wishlist berdasar id wishlist, jika tidak ada akan throw DataRelatedException
        return wishlistRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Wishlist Found")
        );
    }

    @Transactional
    @Override
    public Wishlist saveWishlist(WishlistDTO wishlistDTO) {
        //jika input null, throw DataRelatedException
        if (wishlistDTO == null) {
            throw new DataRelatedException("Must have a wishlist inputted");
        }
        //cari pembaca dan buku, akan throw DataRelatedException jika tidak ada
        Reader reader = readerService.findReaderById(wishlistDTO.getReaderId());
        Book book = bookService.findBookById(wishlistDTO.getBookId());
        //cari wishlist dengan buku dan pembaca tersebut, karena jika ada maka tidak bisa input lagi dan akan throw DataRelatedException
        if(findWishlistByBookAndReader(book, reader) != null) {
            throw new DataRelatedException("Wishlist for this book and reader have had set");
        }
        //buat object wishlist baru
        Wishlist createdWishlist = new Wishlist(book, reader);
        //value field wishedBy di buku tersebut + 1
        book.setBookWishedBy(book.getBookWishedBy() + 1);
        //output berupa save object wishlist yg baru dibuat ke repository (akan masuk database)
        return wishlistRepository.save(createdWishlist);
    }

    @Override
    public Wishlist findWishlistByBookAndReader(Book book, Reader reader) {
        //cari pembaca dan buku, akan throw DataRelatedException jika tidak ada
        bookService.findBookById(book.getBookId());
        readerService.findReaderById(reader.getReaderId());
        //output berupa wishlist dengan field buku & pembaca yang dimaksud
        return wishlistRepository.findByBookAndReader(book, reader);
    }

    @Override
    public void deleteWishlist(Integer id) {
        //cari buku yang terkait dengan wishlist yang akan dihapus
        Book book = findWishlistById(id).getBook();
        //value field wishedBy - 1 (karena wishlist nya dihapus)
        book.setBookWishedBy(book.getBookWishedBy() - 1);
        //hapus wishlist dari repository (dan database)
        wishlistRepository.deleteById(id);
    }

    @Override
    public void delete(Wishlist wishlist) {
        //cari buku yang terkait dengan wishlist yang akan dihapus
        Book book = wishlist.getBook();
        //value field wishedBy - 1 (karena wishlist nya dihapus)
        book.setBookWishedBy(book.getBookWishedBy() - 1);
        //hapus wishlist dari repository (dan database)
        wishlistRepository.delete(wishlist);
    }
}
