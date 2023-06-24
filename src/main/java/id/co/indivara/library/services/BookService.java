package id.co.indivara.library.services;

import id.co.indivara.library.entities.Book;

import java.util.ArrayList;

public interface BookService {
    ArrayList<Book> findAllBook();
    Book findBookById(Integer id);
    Book saveBook(Book book);
    Book updateBook(Integer id, Book bookUpdate);
    void deleteBook(Integer id);
}
