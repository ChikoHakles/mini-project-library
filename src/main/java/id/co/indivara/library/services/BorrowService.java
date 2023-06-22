package id.co.indivara.library.services;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.BorrowDTO;
import id.co.indivara.library.entities.Reader;

import java.util.ArrayList;
import java.util.UUID;

public interface BorrowService {
    ArrayList<Borrow> findAllBorrow();
    ArrayList<Borrow> findAllBorrowByReader(Reader reader);
    ArrayList<Borrow> findAllBorrowByBook(Book book);
    ArrayList<Borrow> findUnreturnedBorrow();
    ArrayList<Borrow> findUnreturnedBorrowByReader(Reader reader);
    ArrayList<Borrow> findUnreturnedBorrowByBook(Book book);
    Borrow findBorrowById(UUID id);
    Borrow findBorrowByCode(String code);
    Borrow saveBorrow(BorrowDTO borrowDTO);
    void deleteBorrow(UUID id);
    void deleteBorrowByCode(String code);
}
