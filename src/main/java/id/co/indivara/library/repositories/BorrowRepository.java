package id.co.indivara.library.repositories;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRepository extends CrudRepository<Borrow, UUID> {
    List<Borrow> findAllByBook(Book book);
    List<Borrow> findAllByReader(Reader reader);
    Borrow findFirstByReaderAndBookOrderByBorrowDateDesc(Reader reader, Book book);
    Borrow findByBorrowCode(String code);
}
