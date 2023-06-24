package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.*;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.BorrowRepository;
import id.co.indivara.library.repositories.ReturnRepository;
import id.co.indivara.library.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class BorrowImplementation implements BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private ReturnRepository returnRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private WishlistService wishlistService;

    @Override
    public ArrayList<Borrow> findAllBorrow() {
        ArrayList<Borrow> borrows = new ArrayList<>((Collection<Borrow>) borrowRepository.findAll());
        if(borrows.isEmpty()) {
            throw new DataRelatedException("No Borrow Found");
        }
        return borrows;
    }

    @Override
    public ArrayList<Borrow> findAllBorrowByReader(Reader reader) {
        return new ArrayList<>(borrowRepository.findAllByReader(readerService.findReaderById(reader.getReaderId())));
    }

    @Override
    public ArrayList<Borrow> findAllBorrowByBook(Book book) {
        return new ArrayList<>(borrowRepository.findAllByBook(bookService.findBookById(book.getBookId())));
    }

    @Override
    public Borrow findBorrowByCode(String code) {
        Borrow borrow = borrowRepository.findByBorrowCode(code);
        if (borrow == null) {
            throw new DataRelatedException("No Borrow Found");
        }
        return borrow;
    }

    @Override
    public Borrow findBorrowById(UUID id) {
        return borrowRepository.findById(id).orElseThrow(
                () -> new DataRelatedException("No Borrow Found")
        );
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrow() {
        ArrayList<Borrow> pool = findAllBorrow();
        ArrayList<Borrow> returneds = new ArrayList<>();
        returnRepository.findAll().forEach(ret -> returneds.add(ret.getBorrow()));
        pool.removeIf(returneds::contains);
        return pool;
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrowByReader(Reader reader) {
        ArrayList<Borrow> pool = findAllBorrowByReader(reader);
        ArrayList<Borrow> unreturneds = new ArrayList<>();
        for (Borrow b: pool) {
            Return ret = returnRepository.findByBorrow(b);
            if(ret == null) {
                unreturneds.add(b);
            }
        }
        return unreturneds;
    }

    @Override
    public ArrayList<Borrow> findUnreturnedBorrowByBook(Book book) {
        ArrayList<Borrow> pool = findAllBorrowByBook(book);
        ArrayList<Borrow> unreturneds = new ArrayList<>();
        for (Borrow b: pool) {
            Return ret = returnRepository.findByBorrow(b);
            if(ret == null) {
                unreturneds.add(b);
            }
        }
        return unreturneds;
    }

    @Transactional
    @Override
    public Borrow saveBorrow(BorrowDTO borrowDTO) {
        if (borrowDTO == null) {
            throw new DataRelatedException("Must have a borrow inputted");
        }
        Book book = bookService.findBookById(borrowDTO.getBookId());
        Reader reader = readerService.findReaderById(borrowDTO.getReaderId());
        if (book.getBookReady() == 0) {
            wishlistService.saveWishlist(new WishlistDTO(borrowDTO.getBookId(), borrowDTO.getReaderId()));
            return null;
        }
        Wishlist wishlist = wishlistService.findWishlistByBookAndReader(book, reader);
        if(wishlist != null) {
            wishlistService.delete(wishlist);
        }
        book.setBookReady(book.getBookReady() - 1);
        book.setBookUnreturned(book.getBookUnreturned() + 1);
        book.setBookNumberOfReading(book.getBookNumberOfReading() + 1);
        Borrow createdBorrow = new Borrow(book, reader);
        return borrowRepository.save(createdBorrow);
    }

    @Transactional
    @Override
    public void deleteBorrow(UUID id) {
        borrowRepository.delete(findBorrowById(id));
    }

    @Transactional
    @Override
    public void deleteBorrowByCode(String code) {
        borrowRepository.delete(findBorrowByCode(code));
    }
}
