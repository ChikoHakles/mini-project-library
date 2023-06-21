package id.co.indivara.library.services;

import id.co.indivara.library.entities.Book;
import id.co.indivara.library.entities.Borrow;
import id.co.indivara.library.entities.Reader;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BorrowRepository extends CrudRepository<Borrow, UUID> {
    List<Borrow> findAllByBook(Book book);
    List<Borrow> findAllByReader(Reader reader);
    Borrow findByBorrowCode(String code);
}
