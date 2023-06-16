package id.co.indivara.perpustakaan.services;

import id.co.indivara.perpustakaan.entities.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface BookService {
    ArrayList<Book> findAllBook();
    Book findBookById(Integer id);
    Book saveBook(Book book);
    Book updateBook(Integer id, Book bookUpdate);
    void deleteBook(Integer id);
}
