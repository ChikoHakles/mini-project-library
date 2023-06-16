package id.co.indivara.perpustakaan.implementations;

import id.co.indivara.perpustakaan.entities.Book;
import id.co.indivara.perpustakaan.repositories.BookRepository;
import id.co.indivara.perpustakaan.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class BookImplementation implements BookService {
    @Autowired
    BookRepository bookRepository;
    @Override
    public ArrayList<Book> findAllBook() {
        return new ArrayList<Book>((Collection<Book>) bookRepository.findAll());
    }

    @Override
    public Book findBookById(Integer id) {
        return bookRepository.findById(id).isPresent() ? bookRepository.findById(id).get() : null;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Integer id, Book updateBook) {
        if(!bookRepository.findById(id).isPresent()) {
            return null;
        }
        Book oldBook = bookRepository.findById(id).get();
        if(updateBook.getBookTitle().isEmpty()) {
            oldBook.setBookTitle(updateBook.getBookTitle());
        }
        return null;
    }

    @Override
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
