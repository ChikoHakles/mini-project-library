package id.co.indivara.library.implementations;

import id.co.indivara.library.entities.*;
import id.co.indivara.library.exceptions.DataRelatedException;
import id.co.indivara.library.repositories.BorrowRepository;
import id.co.indivara.library.services.BookService;
import id.co.indivara.library.services.BorrowService;
import id.co.indivara.library.services.ReaderService;
import id.co.indivara.library.services.WishlistService;
import id.co.indivara.library.utils.Utility;
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
        readerService.findReaderById(reader.getReaderId());
        return new ArrayList<>(borrowRepository.findAllByReader(reader));
    }

    @Override
    public ArrayList<Borrow> findAllBorrowByBook(Book book) {
        bookService.findBookById(book.getBookId());
        return new ArrayList<>(borrowRepository.findAllByBook(book));
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

    @Transactional
    @Override
    public Borrow saveBorrow(BorrowDTO borrowDTO) {
        if (borrowDTO == null) {
            throw new DataRelatedException("Must have a borrow inputted");
        }
        Book book = bookService.findBookById(borrowDTO.getBookId());
        Reader reader = readerService.findReaderById(borrowDTO.getReaderId());
        if (book.getBookReady() > 0) {
            book.setBookReady(book.getBookReady() - 1);
            book.setBookUnreturned(book.getBookUnreturned() + 1);
            book.setBookNumberOfReading(book.getBookNumberOfReading() + 1);
            Borrow createdBorrow = new Borrow(book, reader);
            return borrowRepository.save(createdBorrow);
        }
        wishlistService.saveWishlist(new WishlistDTO(borrowDTO.getBookId(), borrowDTO.getReaderId()));
        return null;
    }

    @Transactional
    @Override
    public void deleteBorrow(UUID id) {
        findBorrowById(id);
        borrowRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteBorrowByCode(String code) {
        borrowRepository.delete(findBorrowByCode(code));
    }
}
